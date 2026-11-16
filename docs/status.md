# 현재 상태 (인수인계)
> 메인 채팅이 종료 전에 갱신. 새 메인 채팅은 이 파일부터 읽음.

## 완료
- 큰 틀 계획 확정 → `docs/plan.md`
- 채팅 체계·CLAUDE.md·Skills(chat-start, chat-close, task-spec, task-verify)·Hooks(CLAUDE.md 글자 수, 비밀 파일 커밋 차단) 구성
- 운영 흐름 확정: 작업 채팅은 완료 보고만 → 메인이 `task-verify`로 확인 → 통과 시 작업 채팅 보관·다음 진행. 새 채팅 안내는 메인만, 첫 프롬프트는 코드블록으로
- 브랜치 방식 확정: 채팅 종료 시 PR → 채팅이 `main`에 직접 병합

## 다음 할 일
- 로드맵 0단계 세부 계획: 빈 Android 프로젝트 구조, Actions 빌드·서명(키 생성·Secrets 등록 절차 포함) → 작업 명세 작성 후 작업 채팅 배정

## 미결정
- 없음
