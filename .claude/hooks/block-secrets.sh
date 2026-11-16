#!/usr/bin/env bash
# git commit 전에 스테이징된 비밀 파일 차단.
cmd=$(jq -r '.tool_input.command // empty')
echo "$cmd" | grep -q 'git commit' || exit 0
cd "${CLAUDE_PROJECT_DIR:-.}" || exit 0
bad=$(git diff --cached --name-only | grep -Ei '\.(jks|keystore|p12|pem)$|key\.properties$|service[-_]?account.*\.json$|credentials.*\.json$|\.env$')
if [ -n "$bad" ]; then
  echo "비밀 파일 커밋 차단: $bad (git reset HEAD <파일> 후 .gitignore에 추가)" >&2
  exit 2
fi
