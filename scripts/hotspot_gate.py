#!/usr/bin/env python3
"""Fail only when a changed Java or Kotlin file is both complex and a hotspot.

A one-line edit in a quiet file passes, even if that file is already complex.
Complexity is file-level cyclomatic complexity (decision points). The hotspot
set is the top 10% of Java and Kotlin files by git commit touches, including
ties at the cutoff. Files that share only the minimum frequency are quiet
whenever a strictly hotter file exists.
"""

from __future__ import annotations

import argparse
import math
import os
import re
import subprocess
import sys
from pathlib import Path


DECISION = re.compile(r"\b(?:if|when|while|for|catch)\b|&&|\|\||\?:")
SOURCE_SUFFIXES = (".java", ".kt", ".kts")


def git(repo: Path, *args: str) -> str:
    env = os.environ.copy()
    env["GIT_LFS_SKIP_SMUDGE"] = "1"
    return subprocess.check_output(
        [
            "git",
            "-c",
            "filter.lfs.process=",
            "-c",
            "filter.lfs.required=false",
            "-c",
            "filter.lfs.smudge=",
            "-c",
            "filter.lfs.clean=",
            "-C",
            str(repo),
            *args,
        ],
        text=True,
        stderr=subprocess.DEVNULL,
        env=env,
    )


def git_ok(repo: Path, *args: str) -> str:
    try:
        return git(repo, *args)
    except subprocess.CalledProcessError:
        return ""


def strip_noise(text: str) -> str:
    out: list[str] = []
    i = 0
    n = len(text)
    while i < n:
        if text.startswith("//", i):
            i = text.find("\n", i)
            if i < 0:
                break
            continue
        if text.startswith("/*", i):
            end = text.find("*/", i + 2)
            i = n if end < 0 else end + 2
            continue
        if text[i] in "\"'":
            quote = text[i]
            i += 1
            while i < n:
                if text[i] == "\\":
                    i += 2
                    continue
                if text[i] == quote:
                    i += 1
                    break
                i += 1
            continue
        out.append(text[i])
        i += 1
    return "".join(out)


def file_ccn(path: Path) -> int:
    try:
        text = path.read_text(encoding="utf-8", errors="replace")
    except OSError:
        return 1
    return 1 + len(DECISION.findall(strip_noise(text)))


def name_only(raw: str) -> set[str]:
    return {line.strip() for line in raw.splitlines() if line.strip()}


def is_source(path: str) -> bool:
    return path.endswith(SOURCE_SUFFIXES)


def changed_sources(repo: Path, base: str) -> list[str]:
    committed = git_ok(
        repo,
        "diff",
        "--name-only",
        "--diff-filter=ACMR",
        f"{base}...HEAD",
        "--",
        "*.java",
        "*.kt",
        "*.kts",
    )
    if not committed and base != "HEAD":
        committed = git_ok(
            repo,
            "diff",
            "--name-only",
            "--diff-filter=ACMR",
            base,
            "HEAD",
            "--",
            "*.java",
            "*.kt",
            "*.kts",
        )
    worktree = git_ok(
        repo,
        "diff",
        "--name-only",
        "--diff-filter=ACMR",
        "HEAD",
        "--",
        "*.java",
        "*.kt",
        "*.kts",
    )
    untracked = git_ok(
        repo,
        "ls-files",
        "--others",
        "--exclude-standard",
        "--",
        "*.java",
        "*.kt",
        "*.kts",
    )
    names = name_only(committed) | name_only(worktree) | name_only(untracked)
    return sorted(path for path in names if is_source(path))


def frequencies(repo: Path) -> dict[str, int]:
    raw = git_ok(repo, "log", "--name-only", "--pretty=format:", "--", "*.java", "*.kt", "*.kts")
    counts: dict[str, int] = {}
    for line in raw.splitlines():
        path = line.strip()
        if is_source(path):
            counts[path] = counts.get(path, 0) + 1
    return counts


def hotspot_set(counts: dict[str, int], fraction: float) -> set[str]:
    if not counts:
        return set()
    ranked = sorted(counts.items(), key=lambda item: (-item[1], item[0]))
    n = max(1, math.ceil(len(ranked) * fraction))
    cutoff = ranked[n - 1][1]
    minimum = ranked[-1][1]
    chosen = {path for path, count in ranked if count >= cutoff and count > 0}
    if cutoff == minimum and any(count > minimum for _, count in ranked):
        chosen = {path for path, count in ranked if count > minimum}
    return chosen


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--repo", default=".")
    parser.add_argument("--base", default="origin/main")
    parser.add_argument("--top-fraction", type=float, default=0.10)
    parser.add_argument("--complex-ccn", type=int, default=10)
    args = parser.parse_args()
    repo = Path(args.repo).resolve()
    changed = changed_sources(repo, args.base)
    if not changed:
        print(f"hotspot-gate: no Java or Kotlin changes versus {args.base}")
        return 0
    counts = frequencies(repo)
    hot = hotspot_set(counts, args.top_fraction)
    failures: list[str] = []
    for rel in changed:
        path = repo / rel
        if not path.is_file():
            continue
        ccn = file_ccn(path)
        touches = counts.get(rel, 0)
        is_hot = rel in hot
        is_complex = ccn >= args.complex_ccn
        if is_hot and is_complex:
            failures.append(f"{rel} (ccn={ccn}, commits={touches}) is a complex hotspot")
        else:
            print(f"hotspot-gate: pass {rel} ccn={ccn} commits={touches} hot={is_hot}")
    if failures:
        print("hotspot-gate: FAIL", file=sys.stderr)
        for line in failures:
            print(f"  {line}", file=sys.stderr)
        return 1
    print("hotspot-gate: PASS")
    return 0


if __name__ == "__main__":
    sys.exit(main())
