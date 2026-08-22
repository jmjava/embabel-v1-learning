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

"${VENV}/bin/pip" install --upgrade \
  "memoryos @ git+https://x-access-token:${BROAD_REPO_TOKEN}@github.com/jmjava/memory-os.git"

# Wrapper: if OPENAI_API_KEY is unset or a Cursor crsr_ value, use CURSOR_API_KEY
# when that looks like an OpenAI key. No secret values are written to disk.
# Remove any existing file or symlink first so tee does not overwrite the
# venv entrypoint through a leftover /usr/local/bin/memoryos -> venv link.
WRAPPER="/usr/local/bin/memoryos"
sudo rm -f "${WRAPPER}"
sudo tee "${WRAPPER}" >/dev/null <<'EOF'
#!/usr/bin/env bash
set -euo pipefail
REAL="${HOME}/.venvs/memoryos/bin/memoryos"
if [ -n "${CURSOR_API_KEY:-}" ]; then
  case "${OPENAI_API_KEY:-}" in
    ""|crsr_*)
      case "${CURSOR_API_KEY}" in
        sk-*) export OPENAI_API_KEY="${CURSOR_API_KEY}" ;;
      esac
      ;;
  esac
fi
exec "${REAL}" "$@"
EOF
sudo chmod 755 "${WRAPPER}"

if [ -x ./mvnw ]; then
  ./mvnw -q -DskipTests test-compile
fi

command -v memoryos >/dev/null
command -v ffmpeg >/dev/null
memoryos --help >/dev/null
echo "memoryos-cloud-install: ok ($(memoryos --version 2>/dev/null || echo memoryos))"
