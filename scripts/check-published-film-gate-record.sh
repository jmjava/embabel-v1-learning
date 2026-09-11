#!/usr/bin/env bash
# Record-only: published cheat-sheet films fail current memoryos evaluate/gate
# (8822fcb think pause 2.5s vs current 12.5s; no reverse walk). This does not
# install memory-os, recut films, or call the image API. Pin stays 8822fcb.
set -euo pipefail
ROOT="${1:-$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)}"
exec python3 - "$ROOT" <<'PY'
from __future__ import annotations

import json
import re
import sys
from pathlib import Path

ROOT = Path(sys.argv[1]).resolve()
MANIFEST = ROOT / "docs" / "videos" / "memory-os" / "ingest-manifest.json"
SPEC = ROOT / "docs" / "videos" / "memory-os" / "embabel-cheatsheet.palace.yaml"
PIN_FULL = "8822fcb5ae813f491daeff7d5f37fce7ff10d213"
PINNED_THINK_SEC = 2.5
CURRENT_THINK_SEC = 12.5
PUBLISHED_FILMS = (
    "embabel-cheatsheet.mp4",
    "embabel-cheatsheet-floor-1.mp4",
    "embabel-cheatsheet-floor-2.mp4",
)
THINK_KEYS = ("review_pause_sec", "animal_review_pause_sec", "reveal_delay_sec")
REVERSE_MARKERS = (
    "recall-reverse-prompt",
    "recall-reverse-answer",
    "reverse_walk",
    "reverse-walk",
    "reverse walk",
    "walks the animal backwards",
)


def yaml_floats(text: str, key: str) -> list[float]:
    pattern = re.compile(
        rf"(?m)^[ \t]*{re.escape(key)}:[ \t]*([0-9]+(?:\.[0-9]+)?)\s*$"
    )
    return [float(match.group(1)) for match in pattern.finditer(text)]


def derive_think_pause_sec(spec_text: str) -> float:
    values: list[float] = []
    for key in THINK_KEYS:
        values.extend(yaml_floats(spec_text, key))
    return min(values) if values else PINNED_THINK_SEC


def derive_reverse_walk(spec_text: str) -> bool:
    lowered = spec_text.lower()
    return any(marker in lowered for marker in REVERSE_MARKERS)


def main() -> int:
    errors: list[str] = []
    if not MANIFEST.is_file():
        print(f"published-film-gate-record: missing {MANIFEST}", file=sys.stderr)
        return 1
    if not SPEC.is_file():
        print(f"published-film-gate-record: missing {SPEC}", file=sys.stderr)
        return 1

    try:
        manifest = json.loads(MANIFEST.read_text(encoding="utf-8"))
    except json.JSONDecodeError as exc:
        print(f"published-film-gate-record: ingest-manifest.json is not JSON: {exc}", file=sys.stderr)
        return 1

    sha = manifest.get("memory_os_sha")
    if sha != PIN_FULL:
        errors.append(f"memory_os_sha is {sha!r}; pin must stay {PIN_FULL}")

    record = manifest.get("published_film_gate")
    if not isinstance(record, dict):
        errors.append("ingest-manifest.json must contain published_film_gate")
        print("published-film-gate-record failed:", file=sys.stderr)
        for error in errors:
            print(f"  {error}", file=sys.stderr)
        return 1

    spec_text = SPEC.read_text(encoding="utf-8")
    published_think = derive_think_pause_sec(spec_text)
    published_reverse = derive_reverse_walk(spec_text)
    think_fails = published_think + 1e-6 < CURRENT_THINK_SEC
    reverse_fails = not published_reverse
    current_checks_fail = think_fails or reverse_fails
    expected_verdict = "fail" if current_checks_fail else "pass"

    recordings = ROOT / "docs" / "videos" / "recordings"
    for name in PUBLISHED_FILMS:
        path = recordings / name
        if not path.is_file():
            errors.append(f"missing published film {path.relative_to(ROOT)}")

    if record.get("verdict") != expected_verdict:
        errors.append(
            f"published_film_gate.verdict is {record.get('verdict')!r}; "
            f"derived evaluate/gate result is {expected_verdict}"
        )
    if record.get("think_pause_sec") != published_think:
        errors.append(
            f"published_film_gate.think_pause_sec is {record.get('think_pause_sec')!r}; "
            f"palace spec derives {published_think}"
        )
    if record.get("required_think_pause_sec") != CURRENT_THINK_SEC:
        errors.append(
            f"published_film_gate.required_think_pause_sec is "
            f"{record.get('required_think_pause_sec')!r}; current evaluate requires "
            f"{CURRENT_THINK_SEC}"
        )
    if record.get("reverse_walk") is not published_reverse:
        errors.append(
            f"published_film_gate.reverse_walk is {record.get('reverse_walk')!r}; "
            f"palace spec derives {published_reverse}"
        )
    if record.get("required_reverse_walk") is not True:
        errors.append("published_film_gate.required_reverse_walk must be true")
    commands = record.get("memoryos_commands")
    if not isinstance(commands, list) or set(commands) != {"evaluate", "gate"}:
        errors.append("published_film_gate.memoryos_commands must be [evaluate, gate]")
    if current_checks_fail and record.get("films_rebuilt") is not False:
        errors.append(
            "published_film_gate.films_rebuilt must stay false while films fail "
            "current evaluate/gate"
        )

    if errors:
        print("published-film-gate-record failed:", file=sys.stderr)
        for error in errors:
            print(f"  {error}", file=sys.stderr)
        return 1

    print(
        "published-film-gate-record: "
        f"verdict={expected_verdict} think_pause_sec={published_think} "
        f"(required {CURRENT_THINK_SEC}) reverse_walk={published_reverse} "
        f"pin={PIN_FULL} films_rebuilt=false"
    )
    return 0


if __name__ == "__main__":
    sys.exit(main())
PY
