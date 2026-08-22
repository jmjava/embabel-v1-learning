#!/usr/bin/env bash
# Copy Memory OS render outputs into the GitHub Pages recordings folder.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
SRC="$ROOT/docs/videos/memory-os/build/video"
DEST="$ROOT/docs/videos/recordings"
mkdir -p "$DEST"

copy_if_present() {
  local name="$1"
  if [ -f "$SRC/$name" ]; then
    cp -f "$SRC/$name" "$DEST/$name"
    echo "published $name ($(du -h "$DEST/$name" | cut -f1))"
  else
    echo "missing $SRC/$name" >&2
    return 1
  fi
}

status=0
copy_if_present embabel-cheatsheet.mp4 || status=1
copy_if_present embabel-cheatsheet-floor-1.mp4 || status=1
copy_if_present embabel-cheatsheet-floor-2.mp4 || status=1
exit "$status"
