#!/usr/bin/env bash
# CLAUDE.md 수정 후 글자 수 검사. 초과 시 Claude에게 피드백(exit 2).
LIMIT=2000
f=$(jq -r '.tool_input.file_path // empty')
case "$f" in *CLAUDE.md) ;; *) exit 0 ;; esac
[ -f "$f" ] || exit 0
n=$(python3 -c 'import sys;print(len(open(sys.argv[1],encoding="utf-8").read()))' "$f")
if [ "$n" -gt "$LIMIT" ]; then
  echo "CLAUDE.md ${n}자 > 제한 ${LIMIT}자. 상세 내용은 docs/로 옮겨 줄일 것." >&2
  exit 2
fi
