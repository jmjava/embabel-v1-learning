#!/usr/bin/env bash
# Copy Memory OS render outputs into the GitHub Pages recordings folder.
# Fail-closed destination checks: dest must be $ROOT/docs/videos/recordings
# (not a symlink, not outside the repo), the Pages player must embed those
# names, and each copy must land as a non-empty regular file whose size
# matches the source. This does not rebuild films. Pin stays 8822fcb.
set -euo pipefail

ROOT="${1:-$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)}"
ROOT="$(cd "$ROOT" && pwd)"

SRC="$ROOT/docs/videos/memory-os/build/video"
EXPECTED_DEST="$ROOT/docs/videos/recordings"
DEST="${MEMORYOS_PUBLISH_DEST:-$EXPECTED_DEST}"

FILMS=(
  embabel-cheatsheet.mp4
  embabel-cheatsheet-floor-1.mp4
  embabel-cheatsheet-floor-2.mp4
)

die() {
  echo "publish failed: $*" >&2
  exit 1
}

canon_dir() {
  local path="$1"
  (cd "$path" && pwd)
}

under_root() {
  local path="$1"
  case "$path" in
    "$ROOT"|"$ROOT"/*) return 0 ;;
    *) return 1 ;;
  esac
}

[ -d "$ROOT/docs" ] || die "missing $ROOT/docs"
[ -f "$ROOT/docs/index.html" ] || die "missing $ROOT/docs/index.html"

[ "$DEST" = "$EXPECTED_DEST" ] || die "destination must be $EXPECTED_DEST (got $DEST)"

if [ -L "$EXPECTED_DEST" ]; then
  die "destination must not be a symlink: $EXPECTED_DEST"
fi
if [ -e "$EXPECTED_DEST" ] && [ ! -d "$EXPECTED_DEST" ]; then
  die "destination is not a directory: $EXPECTED_DEST"
fi

mkdir -p "$EXPECTED_DEST"

if [ -L "$EXPECTED_DEST" ]; then
  die "destination became a symlink: $EXPECTED_DEST"
fi
[ -d "$EXPECTED_DEST" ] || die "destination is not a directory: $EXPECTED_DEST"

DEST_CANON="$(canon_dir "$EXPECTED_DEST")"
under_root "$DEST_CANON" || die "destination escaped repo: $DEST_CANON"
[ "$DEST_CANON" = "$EXPECTED_DEST" ] || die "destination resolved away from recordings: $DEST_CANON"

[ -d "$SRC" ] || die "missing source dir $SRC (run memoryos build first)"
if [ -L "$SRC" ]; then
  SRC_CANON="$(canon_dir "$SRC")"
  under_root "$SRC_CANON" || die "source dir escaped repo: $SRC_CANON"
fi

for name in "${FILMS[@]}"; do
  grep -Fq "videos/recordings/${name}" "$ROOT/docs/index.html" \
    || die "index.html does not embed videos/recordings/${name}"
done

publish_one() {
  local name="$1"
  case "$name" in
    */*|*..*|.*) die "illegal film name: $name" ;;
  esac
  [[ "$name" == *.mp4 ]] || die "not an mp4: $name"

  local src="$SRC/$name"
  local dest="$DEST_CANON/$name"

  [ -e "$src" ] || die "missing source $src"
  [ -f "$src" ] || die "source is not a regular file: $src"
  [ -r "$src" ] || die "unreadable source $src"
  [ -s "$src" ] || die "empty source $src"

  local dest_parent
  dest_parent="$(cd "$(dirname "$dest")" && pwd)"
  [ "$dest_parent" = "$DEST_CANON" ] || die "dest escaped recordings: $dest"

  local src_size dest_size
  src_size="$(wc -c < "$src")"
  cp -f "$src" "$dest"

  [ -f "$dest" ] || die "copy did not create $dest"
  [ ! -L "$dest" ] || die "destination became a symlink: $dest"
  [ -s "$dest" ] || die "destination empty after copy: $dest"
  dest_size="$(wc -c < "$dest")"
  [ "$src_size" -eq "$dest_size" ] || die "size mismatch $name: src=$src_size dest=$dest_size"

  echo "published $name (${dest_size} bytes -> $dest)"
}

for name in "${FILMS[@]}"; do
  publish_one "$name"
done
