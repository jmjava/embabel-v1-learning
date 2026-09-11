#!/usr/bin/env bash
# Fail-closed GitHub Pages gate: palace Markdown front matter must be present.
# Front matter is everything before the first level-1 Floor heading (memory-os
# palace-markdown spec). This does not install memory-os, validate films, or
# rebuild recordings. Pin stays 8822fcb.
set -euo pipefail
ROOT="${1:-$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)}"
exec python3 - "$ROOT" <<'PY'
from __future__ import annotations

import re
import sys
from pathlib import Path

ROOT = Path(sys.argv[1]).resolve()
PALACE_ROOT = ROOT / "docs" / "videos" / "memory-os"
FLOOR_HEADING = re.compile(r"^#\s+(?:\d+\.\s+)?Floor\s+\d+", re.M)
REQUIRED = (
    "Purpose",
    "Audience",
    "Learning method",
    "Video style",
    "Narration style",
)


def label_value(front_matter: str, label: str) -> str | None:
    pattern = re.compile(
        rf"\*\*{re.escape(label)}:\*\*\s*(.*?)(?=\n\s*\*\*[^*]+?:\*\*|\n\s*#|\n\s*-{{3,}}\s*$|\Z)",
        re.S | re.M,
    )
    match = pattern.search(front_matter)
    if match is None:
        return None
    return match.group(1).strip()


def main() -> int:
    if not PALACE_ROOT.is_dir():
        print(f"pages validate failed: missing {PALACE_ROOT}", file=sys.stderr)
        return 1

    errors: list[str] = []
    checked = 0
    for path in sorted(PALACE_ROOT.rglob("*.md")):
        text = path.read_text(encoding="utf-8")
        floor = FLOOR_HEADING.search(text)
        if floor is None:
            continue
        checked += 1
        front_matter = text[: floor.start()]
        rel = path.relative_to(ROOT)
        for label in REQUIRED:
            value = label_value(front_matter, label)
            if not value:
                errors.append(f"{rel}: missing front-matter **{label}:**")

    if checked == 0:
        errors.append("no palace Markdown with a level-1 Floor heading under docs/videos/memory-os")

    if errors:
        print("pages validate failed:", file=sys.stderr)
        for error in errors:
            print(f"  {error}", file=sys.stderr)
        return 1

    print(f"pages validate: {checked} palace(s) have required front matter")
    return 0


if __name__ == "__main__":
    sys.exit(main())
PY
