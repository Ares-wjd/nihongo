# 현재 상태 (인수인계)
> 메인 채팅이 종료 전에 갱신. 새 메인 채팅은 이 파일부터 읽음.

## 완료
- 큰 틀 계획 확정 → `docs/plan.md`
- 채팅 체계·CLAUDE.md·Skills(chat-start, chat-close, task-spec, task-verify)·Hooks(CLAUDE.md 글자 수, 비밀 파일 커밋 차단) 구성
- 운영 흐름 확정: 작업 채팅은 완료 보고만 → 메인이 `task-verify`로 확인 → 통과 시 작업 채팅 보관·다음 진행. 새 채팅 안내는 메인만, 첫 프롬프트는 코드블록으로
- 브랜치 방식 확정: 채팅 종료 시 PR → 채팅이 `main`에 직접 병합
- 0단계 결정(패키지·SDK·버전·CI·서명) → `docs/plan.md` 스택 절
- 코딩1 확인 완료: 빈 Compose 앱, `ci.yml`(main CI 녹색), `release.yml`, `docs/signing.md`. 세부 `docs/tasks/코딩-1.md`
- 응답 규칙 추가: 작업 끝난 응답은 짧게, 마지막 줄 `완료.`/`미완료: <이유>`

## 다음 할 일
- 사용자: `docs/signing.md` 따라 키 생성·백업·Secrets 4개 등록
- 등록 후: `v0.1.0` 태그 푸시 → release.yml 실제 실행 확인 → 실기기 설치 확인(태그 푸시는 사용자 승인 후 메인 채팅이 진행)
- 로드맵 1단계 세부 계획(단어 데이터 형식, N5 단어장, TTS 스크립트)

## 미결정
- 없음
