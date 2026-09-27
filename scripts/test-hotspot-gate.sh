#!/usr/bin/env bash
# Proves the hotspot gate for Java and Kotlin: a one-line change in a quiet
# (even complex) file passes; the same kind of edit in a complex high-frequency
# file fails; a one-line edit in a simple high-frequency file passes.
set -euo pipefail
export GIT_LFS_SKIP_SMUDGE=1
root="$(git -c filter.lfs.process= -c filter.lfs.required=false rev-parse --show-toplevel)"
gate="$root/scripts/hotspot_gate.py"

scenario() {
  local ext="$1"
  local tmp
  tmp="$(mktemp -d)"
  git -C "$tmp" init -q
  commit() {
    git -C "$tmp" -c user.email="ratchet@example.test" -c user.name="Ratchet" add -A
    git -C "$tmp" -c user.email="ratchet@example.test" -c user.name="Ratchet" commit -qm "$1"
  }
  if [[ "$ext" == "java" ]]; then
    cat > "$tmp/quiet.java" <<'EOF'
class Quiet {
    int complex(int x) {
        if (x == 1) return 1;
        if (x == 2) return 2;
        if (x == 3) return 3;
        if (x == 4) return 4;
        if (x == 5) return 5;
        if (x == 6) return 6;
        if (x == 7) return 7;
        if (x == 8) return 8;
        if (x == 9) return 9;
        return 0;
    }
}
EOF
    cp "$tmp/quiet.java" "$tmp/hot.java"
    printf 'class Simple { int simple(int x) { return x; } }\n' > "$tmp/simple.java"
  else
    cat > "$tmp/quiet.kt" <<'EOF'
fun complex(x: Int): Int {
    if (x == 1) return 1
    if (x == 2) return 2
    if (x == 3) return 3
    if (x == 4) return 4
    if (x == 5) return 5
    if (x == 6) return 6
    if (x == 7) return 7
    if (x == 8) return 8
    if (x == 9) return 9
    return 0
}
EOF
    cp "$tmp/quiet.kt" "$tmp/hot.kt"
    printf 'fun simple(x: Int) = x\n' > "$tmp/simple.kt"
  fi
  commit base
  local n
  for n in 1 2 3 4 5 6 7; do
    printf '\n' >> "$tmp/hot.$ext"
    printf '\n' >> "$tmp/simple.$ext"
    commit "churn $n"
  done
  if ! python3 "$gate" --repo "$tmp" --base HEAD; then
    echo "expected PASS with a clean $ext tree" >&2
    rm -rf "$tmp"
    exit 1
  fi
  printf '// one line\n' >> "$tmp/quiet.$ext"
  if ! python3 "$gate" --repo "$tmp" --base HEAD; then
    echo "expected PASS for a one-line change in a quiet complex $ext file" >&2
    rm -rf "$tmp"
    exit 1
  fi
  git -C "$tmp" checkout -q -- "quiet.$ext"
  printf '// one line\n' >> "$tmp/simple.$ext"
  if ! python3 "$gate" --repo "$tmp" --base HEAD; then
    echo "expected PASS for a one-line change in a simple $ext hotspot" >&2
    rm -rf "$tmp"
    exit 1
  fi
  git -C "$tmp" checkout -q -- "simple.$ext"
  printf '// one line\n' >> "$tmp/hot.$ext"
  if python3 "$gate" --repo "$tmp" --base HEAD; then
    echo "expected FAIL for a change to a complex $ext hotspot" >&2
    rm -rf "$tmp"
    exit 1
  fi
  rm -rf "$tmp"
}

scenario java
scenario kt
echo "test-hotspot-gate: PASS"
