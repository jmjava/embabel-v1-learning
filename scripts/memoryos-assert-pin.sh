#!/usr/bin/env bash
# Fail unless the installed memoryos package resolves to consumer pin 8822fcb.
# __version__ / `memoryos --version` are 0.1.0 at both 8822fcb and 2e94f8d, so
# they cannot detect drift. Use PEP 610 direct_url.json or pip freeze SHA.
set -euo pipefail

PIN_PREFIX="8822fcb"
PIN_FULL="8822fcb5ae813f491daeff7d5f37fce7ff10d213"

VENV="${MEMORYOS_VENV:-${HOME}/.venvs/memoryos}"
PYTHON="${MEMORYOS_PYTHON:-${VENV}/bin/python}"

resolve_commit() {
  if [ -n "${MEMORYOS_DIRECT_URL_JSON:-}" ]; then
    python3 -c '
import json, os, sys
from pathlib import Path
data = json.loads(Path(os.environ["MEMORYOS_DIRECT_URL_JSON"]).read_text())
commit = ((data.get("vcs_info") or {}).get("commit_id") or "").strip().lower()
if not commit:
    sys.exit(2)
print(commit)
'
    return
  fi

  if [ -x "${PYTHON}" ]; then
    if commit="$("${PYTHON}" -c '
import json, sys
from importlib.metadata import PackageNotFoundError, distribution
try:
    raw = distribution("memoryos").read_text("direct_url.json")
except PackageNotFoundError:
    sys.exit(2)
if not raw:
    sys.exit(2)
data = json.loads(raw)
commit = ((data.get("vcs_info") or {}).get("commit_id") or "").strip().lower()
if not commit:
    sys.exit(2)
print(commit)
' 2>/dev/null)"; then
      printf '%s\n' "${commit}"
      return
    fi

    PIP="$(dirname "${PYTHON}")/pip"
    if [ -x "${PIP}" ]; then
      # Last @ is the VCS revision; never print the freeze line (may contain a token).
      freeze="$("${PIP}" freeze 2>/dev/null | grep -E '^memoryos([[:space:]]|@|=)' || true)"
      if [ -n "${freeze}" ]; then
        commit="${freeze##*@}"
        commit="${commit%%[[:space:]]*}"
        commit="$(printf '%s' "${commit}" | tr '[:upper:]' '[:lower:]')"
        if [[ "${commit}" =~ ^[0-9a-f]{7,40}$ ]]; then
          printf '%s\n' "${commit}"
          return
        fi
      fi
    fi
  fi

  echo "memoryos-assert-pin: could not resolve installed git SHA (do not trust __version__/--version)" >&2
  return 2
}

matches_pin() {
  local got="$1"
  [[ ${#got} -ge 7 ]] || return 1
  [[ "${got}" == "${PIN_FULL}" ]] && return 0
  [[ "${got}" == "${PIN_PREFIX}" ]] && return 0
  [[ "${PIN_FULL}" == "${got}"* ]] && return 0
  [[ "${got}" == "${PIN_PREFIX}"* ]] && return 0
  return 1
}

commit="$(resolve_commit)" || {
  echo "memoryos-assert-pin: installed memoryos is not pinned to ${PIN_PREFIX} (no recorded SHA)" >&2
  exit 1
}

if ! matches_pin "${commit}"; then
  echo "memoryos-assert-pin: installed ${commit}, expected ${PIN_FULL} (version 0.1.0 is not a pin)" >&2
  exit 1
fi

echo "memoryos-assert-pin: ok (${commit})"
