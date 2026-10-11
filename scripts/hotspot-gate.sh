#!/usr/bin/env bash
# Fail only when a changed Java or Kotlin file is both complex and in
# the top git change-frequency set. See scripts/hotspot_gate.py.
set -euo pipefail
export GIT_LFS_SKIP_SMUDGE=1
root="$(git -c filter.lfs.process= -c filter.lfs.required=false rev-parse --show-toplevel)"
base="${1:-origin/main}"
exec python3 "$root/scripts/hotspot_gate.py" --repo "$root" --base "$base"
