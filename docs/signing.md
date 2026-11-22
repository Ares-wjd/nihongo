# 서명 키·릴리스 절차

릴리스 APK는 GitHub Actions(`.github/workflows/release.yml`)가 Secrets의 키로 서명한다.
키는 저장소에 절대 커밋하지 않는다(`*.jks`, `*.keystore`는 `.gitignore`에 있음).

## 1. 키 생성 (최초 1회)
JDK의 `keytool` 사용(Android Studio 설치 시 `<Android Studio>/jbr/bin/keytool`).

```bash
keytool -genkeypair -v -keystore nihongo-release.jks -alias nihongo -keyalg RSA -keysize 4096 -validity 36500
```

- 키스토어 비밀번호, 이름 등 입력. 키 비밀번호는 키스토어 비밀번호와 같게 두면 편함(PKCS12 형식은 같아야 함).
- alias(`nihongo`)와 비밀번호 2개를 기록.
- 키스토어 파일은 저장소 폴더 밖에 저장.

## 2. 원본 키 백업 (필수)
- **키를 잃으면 같은 앱으로 업데이트 설치 불가.** 다른 키로 서명한 APK는 기존 앱 위에 설치되지 않아, 앱 삭제(학습 기록 소실) 후 재설치해야 함.
- `nihongo-release.jks` 파일 + alias + 비밀번호를 2곳 이상(예: 비밀번호 관리자 첨부, 개인 클라우드, USB)에 보관.
- GitHub Secrets는 값을 다시 볼 수 없으므로 백업 역할을 못 함.

## 3. base64 변환
Secrets에는 텍스트만 넣을 수 있어 키 파일을 base64 문자열로 변환한다.

Windows (PowerShell):
```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("nihongo-release.jks")) | Set-Content -NoNewline nihongo-release.b64.txt
```

macOS:
```bash
base64 -i nihongo-release.jks -o nihongo-release.b64.txt
```

Linux:
```bash
base64 -w 0 nihongo-release.jks > nihongo-release.b64.txt
```

- 등록 후 `nihongo-release.b64.txt`는 삭제.

## 4. Secrets 등록
경로: 저장소 → Settings → Secrets and variables → Actions → New repository secret

| 이름 | 값 |
|---|---|
| `KEYSTORE_BASE64` | `nihongo-release.b64.txt` 내용 전체 |
| `KEYSTORE_PASSWORD` | 키스토어 비밀번호 |
| `KEY_ALIAS` | alias (예: `nihongo`) |
| `KEY_PASSWORD` | 키 비밀번호 |

4개 중 하나라도 없으면 release 워크플로가 실패한다.

## 5. 릴리스 방법
방법 A(권장): Actions → Release → **Run workflow** → branch `main`, 버전 `0.1.0` 입력. 워크플로가 현재 커밋에 태그 `v0.1.0`을 만들고 Release 생성.

방법 B: `main`에서 태그를 만들어 푸시한다. 형식은 `vX.Y.Z`.

```bash
git checkout main && git pull
git tag v0.1.0
git push origin v0.1.0
```

- Actions가 서명 APK를 빌드해 태그 이름의 Release를 만들고 `nihongo-0.1.0.apk`를 첨부.
- versionName = 태그에서 `v` 제거(`0.1.0`), versionCode = 워크플로 실행 번호(`github.run_number`, 매 실행 증가).
- 같은 태그를 다시 쓰지 않는다. 실패 시 Release·태그 삭제 후 원인 수정, 새 태그로 재시도.

## 로컬 빌드
- 환경변수(`KEYSTORE_PATH`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`)가 모두 있으면 `./gradlew assembleRelease`가 서명 APK 생성.
- 없으면 무서명 `app-release-unsigned.apk` 생성(빌드 실패 아님). 버전은 `-PversionName=1.0.0 -PversionCode=1`로 지정, 미지정 시 `0.0.0-dev`/1.
