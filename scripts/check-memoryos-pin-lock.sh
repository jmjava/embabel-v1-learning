#!/usr/bin/env bash
# Leftover #10 lock: consumer Memory OS pin stays 8822fcb unless an explicit
# leftover authorizes a bump. This does not install memory-os, recut films,
# or call the image API. Pin stays 8822fcb. Films stay unrebuilt.
set -euo pipefail
ROOT="${1:-$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)}"
exec python3 - "$ROOT" <<'PY'
from __future__ import annotations

import json
import sys
from pathlib import Path

ROOT = Path(sys.argv[1]).resolve()
MANIFEST = ROOT / "docs" / "videos" / "memory-os" / "ingest-manifest.json"
LEFTOVERS = ROOT / "leftovers" / "memory-os"
LOCK_LEFTOVER = LEFTOVERS / "10-lock-pin-8822fcb.md"
PIN_FULL = "8822fcb5ae813f491daeff7d5f37fce7ff10d213"
PIN_PREFIX = "8822fcb"
LOCK_LEFTOVER_ID = 10

PIN_SITES = (
    Path("docs/videos/memory-os/ingest-manifest.json"),
    Path("docs/videos/memory-os/README.md"),
    Path("docs/videos/memory-os/PLAN.md"),
    Path("learning-common/src/main/java/com/embabel/learning/common/curriculum/CookbookChapterCatalog.java"),
    Path("scripts/memoryos-cloud-install.sh"),
    Path("scripts/memoryos-assert-pin.sh"),
    Path("leftovers/memory-os/10-lock-pin-8822fcb.md"),
)


def parse_front_matter(text: str) -> dict[str, str]:
    if not text.startswith("---"):
        return {}
    end = text.find("\n---", 3)
    if end < 0:
        return {}
    data: dict[str, str] = {}
    for line in text[4:end].splitlines():
        stripped = line.strip()
        if not stripped or ":" not in stripped:
            continue
        key, value = stripped.split(":", 1)
        data[key.strip()] = value.strip().strip('"').strip("'")
    return data


def leftover_id(raw: object) -> int | None:
    try:
        return int(str(raw).strip())
    except (TypeError, ValueError):
        return None


def load_leftovers() -> list[tuple[Path, dict[str, str]]]:
    if not LEFTOVERS.is_dir():
        return []
    found: list[tuple[Path, dict[str, str]]] = []
    for path in sorted(LEFTOVERS.glob("*.md")):
        found.append((path, parse_front_matter(path.read_text(encoding="utf-8"))))
    return found


def main() -> int:
    errors: list[str] = []
    if not MANIFEST.is_file():
        print(f"memoryos-pin-lock: missing {MANIFEST}", file=sys.stderr)
        return 1
    if not LOCK_LEFTOVER.is_file():
        print(f"memoryos-pin-lock: missing lock leftover {LOCK_LEFTOVER}", file=sys.stderr)
        return 1

    try:
        manifest = json.loads(MANIFEST.read_text(encoding="utf-8"))
    except json.JSONDecodeError as exc:
        print(f"memoryos-pin-lock: ingest-manifest.json is not JSON: {exc}", file=sys.stderr)
        return 1

    leftovers = load_leftovers()
    lock_records = [
        (path, meta)
        for path, meta in leftovers
        if leftover_id(meta.get("leftover")) == LOCK_LEFTOVER_ID
    ]
    if not lock_records:
        errors.append("leftover #10 lock file must declare leftover: 10")
    else:
        _, lock_meta = lock_records[0]
        if lock_meta.get("action") != "lock":
            errors.append("leftover #10 must stay action: lock")
        if lock_meta.get("sha") != PIN_FULL:
            errors.append(
                f"leftover #10 sha is {lock_meta.get('sha')!r}; lock leftover must stay {PIN_FULL}"
            )
        if lock_meta.get("films_rebuilt") not in {"false", "False"}:
            errors.append("leftover #10 films_rebuilt must stay false")

    bump_records = [
        (path, meta) for path, meta in leftovers if meta.get("action") == "bump"
    ]
    lock = manifest.get("pin_lock")
    if not isinstance(lock, dict):
        errors.append("ingest-manifest.json must contain pin_lock")
        print("memoryos-pin-lock failed:", file=sys.stderr)
        for error in errors:
            print(f"  {error}", file=sys.stderr)
        return 1

    authorizing = lock.get("authorizing_leftover")
    if leftover_id(lock.get("leftover")) != LOCK_LEFTOVER_ID:
        errors.append("pin_lock.leftover must be 10")
    if lock.get("action") != "lock":
        errors.append("pin_lock.action must be lock")
    if lock.get("locked") is not True:
        errors.append("pin_lock.locked must be true")
    if lock.get("films_rebuilt") is not False:
        errors.append("pin_lock.films_rebuilt must stay false")
    if lock.get("prefix") != PIN_PREFIX:
        errors.append(f"pin_lock.prefix must stay {PIN_PREFIX}")

    authorized_full = PIN_FULL
    authorized_prefix = PIN_PREFIX
    if authorizing in (None, "", "null"):
        if bump_records:
            names = ", ".join(path.name for path, _ in bump_records)
            errors.append(
                "pin_lock.authorizing_leftover is empty but bump leftover(s) exist: "
                + names
            )
        if lock.get("sha") != PIN_FULL:
            errors.append(
                f"pin_lock.sha is {lock.get('sha')!r}; without an authorizing leftover "
                f"the pin must stay {PIN_FULL}"
            )
    else:
        leftover_no = leftover_id(authorizing)
        if leftover_no is None:
            errors.append(
                f"pin_lock.authorizing_leftover is {authorizing!r}; expected a leftover number"
            )
        matches = [
            (path, meta)
            for path, meta in bump_records
            if leftover_id(meta.get("leftover")) == leftover_no
        ]
        if not matches:
            errors.append(
                f"pin_lock.authorizing_leftover is {authorizing!r} but no leftovers/memory-os "
                f"file with action: bump leftover: {authorizing} exists"
            )
        else:
            _, bump = matches[0]
            target = (bump.get("to") or bump.get("sha") or "").strip().lower()
            if len(target) < 7:
                errors.append(
                    f"authorizing leftover {leftover_no} must name the new SHA in to: or sha:"
                )
            else:
                authorized_full = target
                authorized_prefix = target[:7]
                if lock.get("sha") != authorized_full:
                    errors.append(
                        f"pin_lock.sha is {lock.get('sha')!r}; authorizing leftover "
                        f"{leftover_no} names {authorized_full}"
                    )

    sha = manifest.get("memory_os_sha")
    if sha != authorized_full:
        errors.append(
            f"memory_os_sha is {sha!r}; consumer pin must stay {authorized_full} "
            "(changing the pin without an explicit leftover must go red)"
        )

    catalog = ROOT / PIN_SITES[3]
    if catalog.is_file():
        catalog_text = catalog.read_text(encoding="utf-8")
        if f'MEMORY_OS_SHA = "{authorized_full}"' not in catalog_text:
            errors.append(
                "CookbookChapterCatalog.MEMORY_OS_SHA must stay "
                f"{authorized_full}"
            )
    install = ROOT / PIN_SITES[4]
    if install.is_file() and f"@{authorized_prefix}" not in install.read_text(encoding="utf-8"):
        errors.append(
            f"memoryos-cloud-install.sh must pin memory-os @{authorized_prefix}"
        )

    for rel in PIN_SITES:
        path = ROOT / rel
        if not path.is_file():
            errors.append(f"missing pin site {rel}")
            continue
        text = path.read_text(encoding="utf-8")
        if authorized_prefix not in text and authorized_full not in text:
            errors.append(
                f"{rel} does not pin {authorized_prefix}; "
                "changing the pin without an explicit leftover must go red"
            )

    if errors:
        print("memoryos-pin-lock failed:", file=sys.stderr)
        for error in errors:
            print(f"  {error}", file=sys.stderr)
        return 1

    print(
        "memoryos-pin-lock: "
        f"leftover=10 action=lock sha={authorized_full} "
        f"authorizing_leftover={authorizing} films_rebuilt=false"
    )
    return 0


if __name__ == "__main__":
    sys.exit(main())
PY
