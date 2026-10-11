#!/usr/bin/env bash
# Fail-closed GitHub Pages cache-bust for docs/index.html.
# Appends ?v=<12-char sha> to videos/recordings/*.mp4 source URLs.
# A missing index exits non-zero. This does not deploy Pages, rebuild films,
# or change the memory-os pin (stays 8822fcb).
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SCRIPT_PATH="${SCRIPT_DIR}/$(basename "${BASH_SOURCE[0]}")"

self_check() {
  local tmp missing present code=0
  tmp="$(mktemp -d)"
  trap 'rm -rf "$tmp"' RETURN
  missing="${tmp}/missing"
  present="${tmp}/present"
  mkdir -p "${missing}/docs" "${present}/docs"

  set +e
  GITHUB_SHA="abc123def4567890abcdef" "${SCRIPT_PATH}" "${missing}"
  code=$?
  set -e
  if [[ "${code}" -eq 0 ]]; then
    echo "cache-bust self-check failed: missing docs/index.html exited 0" >&2
    return 1
  fi

  cat > "${present}/docs/index.html" <<'HTML'
<source src="videos/recordings/embabel-cheatsheet.mp4" type="video/mp4">
<source src="videos/recordings/already.mp4?v=old" type="video/mp4">
HTML
  GITHUB_SHA="abc123def4567890abcdef" "${SCRIPT_PATH}" "${present}"
  local rewritten
  rewritten="$(cat "${present}/docs/index.html")"
  local expected="src=\"videos/recordings/embabel-cheatsheet.mp4?v=abc123def456\""
  local replaced="src=\"videos/recordings/already.mp4?v=abc123def456\""
  if [[ "${rewritten}" != *"${expected}"* || "${rewritten}" != *"${replaced}"* ]]; then
    echo "cache-bust self-check failed: present index was not cache-busted" >&2
    echo "${rewritten}" >&2
    return 1
  fi
  if [[ "${rewritten}" == *"?v=old"* ]]; then
    echo "cache-bust self-check failed: previous query string was left in place" >&2
    return 1
  fi
  echo "cache-bust self-check: missing index exits non-zero; present index cache-busts"
}

if [[ "${1:-}" == "--self-check" ]]; then
  self_check
  exit 0
fi

ROOT="${1:-$(cd "${SCRIPT_DIR}/.." && pwd)}"
exec python3 - "${ROOT}" <<'PY'
from __future__ import annotations

import os
import re
import sys
from pathlib import Path

ROOT = Path(sys.argv[1]).resolve()
SRC = re.compile(r'src="(videos/recordings/[^"?]+\.mp4)(?:\?[^"]*)?"')


def main() -> int:
    short = os.environ.get("GITHUB_SHA", "")[:12]
    if not short:
        print("cache-bust failed: GITHUB_SHA is required", file=sys.stderr)
        return 1
    path = ROOT / "docs" / "index.html"
    if not path.is_file():
        print(f"cache-bust failed: missing {path}", file=sys.stderr)
        return 1
    text = path.read_text(encoding="utf-8")
    new = SRC.sub(lambda m: f'src="{m.group(1)}?v={short}"', text)
    path.write_text(new, encoding="utf-8")
    print(f"cache-bust: wrote ?v={short} into {path}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
PY
