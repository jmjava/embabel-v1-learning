#!/usr/bin/env bash
# Clean as You Code: run detekt:check on Kotlin changed versus a base ref
# (default origin/main). Old findings stay in kotlin-demo/detekt-baseline.xml.
# Quiet files are not scanned. Does not run detekt:create-baseline and does
# not rewrite the baseline.
set -euo pipefail
export GIT_LFS_SKIP_SMUDGE=1
root="$(git -c filter.lfs.process= -c filter.lfs.required=false rev-parse --show-toplevel)"
cd "$root"
base="${1:-origin/main}"
gitc=(git -c filter.lfs.process= -c filter.lfs.required=false -c filter.lfs.smudge= -c filter.lfs.clean=)

if ! "${gitc[@]}" rev-parse --verify --quiet "$base" >/dev/null; then
  echo "detekt-changed: base ref ${base} is missing" >&2
  exit 1
fi

collect() {
  "${gitc[@]}" diff --name-only --diff-filter=ACMR "${base}...HEAD" -- '*.kt' '*.kts' 2>/dev/null || \
    "${gitc[@]}" diff --name-only --diff-filter=ACMR "$base" HEAD -- '*.kt' '*.kts'
  "${gitc[@]}" diff --name-only --diff-filter=ACMR HEAD -- '*.kt' '*.kts'
  "${gitc[@]}" ls-files --others --exclude-standard -- '*.kt' '*.kts'
}

mapfile -t files < <(collect | sed '/^$/d' | sort -u)
baseline="$root/kotlin-demo/detekt-baseline.xml"
if [[ -f "$baseline" ]]; then
  frozen="$(grep -c '<ID>' "$baseline" || true)"
  echo "detekt-changed: ${frozen} old findings remain visible in kotlin-demo/detekt-baseline.xml"
else
  echo "detekt-changed: baseline file is missing; full detekt:check must create it outside CI" >&2
  exit 1
fi

if [[ ${#files[@]} -eq 0 ]]; then
  echo "detekt-changed: no Kotlin changes versus ${base}"
  exit 0
fi

module_files=()
for f in "${files[@]}"; do
  if [[ "$f" != kotlin-demo/* ]]; then
    echo "detekt-changed: Kotlin path is outside kotlin-demo: $f" >&2
    exit 1
  fi
  module_files+=("$root/$f")
done

input="$(IFS=,; echo "${module_files[*]}")"
echo "detekt-changed: kotlin-demo (${#module_files[@]} files) versus ${base}"
./mvnw -B -pl kotlin-demo detekt:check \
  -Ddetekt.input="$input" \
  -Ddetekt.failBuildOnMaxIssuesReached=true \
  -Ddetekt.autoCorrect=false
echo "detekt-changed: PASS"
