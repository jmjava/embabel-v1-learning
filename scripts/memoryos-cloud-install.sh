#!/usr/bin/env bash
# Idempotent Cloud Agent install: Memory OS CLI + Embabel Maven cache.
# Requires BROAD_REPO_TOKEN (private jmjava/memory-os). OPENAI_API_KEY is
# only needed later for images/TTS, not for this bootstrap.
set -euo pipefail

sudo apt-get update -qq
sudo DEBIAN_FRONTEND=noninteractive apt-get install -y -qq \
  python3 python3-venv python3-pip ffmpeg fonts-dejavu-core

VENV="${HOME}/.venvs/memoryos"
python3 -m venv "${VENV}"
"${VENV}/bin/pip" install -U pip >/dev/null

if [ -z "${BROAD_REPO_TOKEN:-}" ]; then
  echo "BROAD_REPO_TOKEN is required to install private jmjava/memory-os" >&2
  exit 1
fi

# Consumer pin is 8822fcb (ingest-manifest memory_os_sha). Do not float to main HEAD.
"${VENV}/bin/pip" install --upgrade \
  "memoryos @ git+https://x-access-token:${BROAD_REPO_TOKEN}@github.com/jmjava/memory-os.git@8822fcb"
sudo ln -sfn "${VENV}/bin/memoryos" /usr/local/bin/memoryos

if [ -x ./mvnw ]; then
  ./mvnw -q -DskipTests test-compile
fi

command -v memoryos >/dev/null
command -v ffmpeg >/dev/null
memoryos --help >/dev/null
echo "memoryos-cloud-install: ok ($(memoryos --version 2>/dev/null || echo memoryos))"
