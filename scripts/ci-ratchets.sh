#!/usr/bin/env bash
# Pull-request ratchets. Does not rewrite detekt or SpotBugs baselines and
# does not smudge Git LFS films.
set -euo pipefail
export GIT_LFS_SKIP_SMUDGE=1
export RATCHET_ALL_MODULES=1
root="$(git -c filter.lfs.process= -c filter.lfs.required=false rev-parse --show-toplevel)"
cd "$root"
base="${1:-origin/main}"
gitc=(git -c filter.lfs.process= -c filter.lfs.required=false -c filter.lfs.smudge= -c filter.lfs.clean=)

if ! "${gitc[@]}" rev-parse --verify --quiet "$base" >/dev/null; then
  "${gitc[@]}" fetch --no-tags origin main
fi

if grep -E 'detekt:create-baseline|spotbugs_ratchet.py --mode update|--update' .github/workflows/ci.yml; then
  echo "ci workflow rewrites a baseline" >&2
  exit 1
fi

bash scripts/test-hotspot-gate.sh
python3 scripts/spotbugs_ratchet.py --mode self-test
bash scripts/hotspot-gate.sh "$base"
./mvnw -B -pl learning-common -am install -DskipTests
bash scripts/detekt-changed.sh "$base"
./mvnw -B -pl kotlin-demo detekt:check
./mvnw -B -DskipTests test-compile
./mvnw -B -pl learning-common,java-demo spotbugs:spotbugs
python3 scripts/spotbugs_ratchet.py --mode cayc --base "$base"
python3 scripts/spotbugs_ratchet.py --mode check
./mvnw -B -pl learning-common test -Dtest=LearningTestsDoNotOpenSocketsTest
./mvnw -B -pl learning-common org.pitest:pitest-maven:mutationCoverage
