#!/usr/bin/env python3
"""SpotBugs clean-as-you-code and frozen-baseline checks.

Check mode fails when a fingerprint is not in the frozen baseline and never
writes that file. Update mode rewrites the baseline locally and refuses to
run when CI or GITHUB_ACTIONS is set. Clean-as-you-code fails only on a new
fingerprint in a Java file changed versus the base ref. Findings outside
that diff are printed and do not fail that mode.
"""

from __future__ import annotations

import argparse
import os
import subprocess
import sys
import tempfile
import xml.etree.ElementTree as ET
from pathlib import Path


HEADER = (
    "# Frozen SpotBugs findings for learning-common and java-demo.\n"
    "# CI fails when a fingerprint appears that is not listed here.\n"
    "# Do not refresh this file in CI. Update locally with:\n"
    "#   python3 scripts/spotbugs_ratchet.py --mode update\n"
)

REPORTS = (
    "learning-common/target/spotbugsXml.xml",
    "java-demo/target/spotbugsXml.xml",
)


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


def changed_main_java(repo: Path, base: str) -> set[str]:
    chunks = [
        git_ok(
            repo,
            "diff",
            "--name-only",
            "--diff-filter=ACMR",
            f"{base}...HEAD",
            "--",
            "*.java",
        ),
        git_ok(repo, "diff", "--name-only", "--diff-filter=ACMR", "HEAD", "--", "*.java"),
        git_ok(repo, "ls-files", "--others", "--exclude-standard", "--", "*.java"),
    ]
    if not chunks[0] and base != "HEAD":
        chunks[0] = git_ok(
            repo,
            "diff",
            "--name-only",
            "--diff-filter=ACMR",
            base,
            "HEAD",
            "--",
            "*.java",
        )
    names: set[str] = set()
    for raw in chunks:
        for line in raw.splitlines():
            path = line.strip()
            if "/src/main/java/" in path:
                names.add(path)
    return names


def parse_report(module: str, xml_text: str) -> list[str]:
    root = ET.fromstring(xml_text)
    findings: list[str] = []
    for bug in root.iter("BugInstance"):
        bug_type = bug.attrib.get("type", "")
        priority = bug.attrib.get("priority", "")
        method = ""
        method_el = bug.find("Method")
        if method_el is not None:
            method = method_el.attrib.get("name", "")
        classname = ""
        class_el = bug.find("Class")
        if class_el is not None:
            classname = class_el.attrib.get("classname", "")
        source = bug.find("SourceLine")
        sourcepath = ""
        start = ""
        if source is not None:
            sourcepath = source.attrib.get("sourcepath", "")
            start = source.attrib.get("start", "")
        findings.append(
            "|".join((module, bug_type, priority, classname, method, sourcepath, start))
        )
    return findings


def load_findings(repo: Path) -> list[str]:
    findings: list[str] = []
    missing: list[str] = []
    for rel in REPORTS:
        path = repo / rel
        if not path.is_file():
            missing.append(rel)
            continue
        module = rel.split("/", 1)[0]
        findings.extend(parse_report(module, path.read_text(encoding="utf-8")))
    if missing:
        raise SystemExit(
            "missing SpotBugs report(s): "
            + ", ".join(missing)
            + " (run mvn -pl learning-common,java-demo spotbugs:spotbugs)"
        )
    return sorted(set(findings))


def read_baseline(text: str) -> set[str]:
    return {
        line.strip()
        for line in text.splitlines()
        if line.strip() and not line.startswith("#")
    }


def format_baseline(findings: set[str] | list[str]) -> str:
    body = "".join(f"{item}\n" for item in sorted(set(findings)))
    return HEADER + body


def ci_refuses_update() -> bool:
    return os.environ.get("CI") == "true" or os.environ.get("GITHUB_ACTIONS") == "true"


def repo_root() -> Path:
    raw = git(Path("."), "rev-parse", "--show-toplevel")
    return Path(raw.strip())


def cmd_update(repo: Path, baseline: Path) -> int:
    if ci_refuses_update():
        print("refusing to rewrite the SpotBugs baseline in CI", file=sys.stderr)
        return 2
    findings = load_findings(repo)
    baseline.parent.mkdir(parents=True, exist_ok=True)
    baseline.write_text(format_baseline(findings), encoding="utf-8")
    print(f"updated {baseline} ({len(findings)} findings)")
    return 0


def cmd_check(repo: Path, baseline: Path) -> int:
    if not baseline.is_file():
        print(f"missing SpotBugs baseline: {baseline}", file=sys.stderr)
        return 2
    before = baseline.read_bytes()
    frozen = read_baseline(before.decode("utf-8"))
    findings = load_findings(repo)
    current = set(findings)
    new = sorted(current - frozen)
    gone = sorted(frozen - current)
    for item in sorted(frozen & current):
        print(f"spotbugs-baseline: known {item}")
    for item in gone:
        print(f"spotbugs-baseline: gone {item}")
    after = baseline.read_bytes()
    if before != after:
        print("baseline check rewrote the file", file=sys.stderr)
        return 2
    if new:
        print("spotbugs-baseline: FAIL", file=sys.stderr)
        for item in new:
            print(f"  new {item}", file=sys.stderr)
        return 1
    print(f"spotbugs-baseline: PASS ({len(current)} findings, {len(gone)} gone)")
    return 0


def cmd_cayc(repo: Path, baseline: Path, base: str) -> int:
    if not baseline.is_file():
        print(f"missing SpotBugs baseline: {baseline}", file=sys.stderr)
        return 2
    frozen = read_baseline(baseline.read_text(encoding="utf-8"))
    findings = load_findings(repo)
    changed = changed_main_java(repo, base)
    new_in_diff: list[str] = []
    for item in findings:
        sourcepath = item.split("|")[5]
        touched = any(path.endswith(sourcepath) for path in changed) if sourcepath else False
        known = item in frozen
        if touched and not known:
            new_in_diff.append(item)
            print(f"spotbugs-cayc: NEW {item}")
        elif touched:
            print(f"spotbugs-cayc: known changed {item}")
        else:
            print(f"spotbugs-cayc: known unchanged {item}")
    if not findings:
        print("spotbugs-cayc: no findings; old set is empty and remains visible in the baseline file")
    if new_in_diff:
        print("spotbugs-cayc: FAIL", file=sys.stderr)
        return 1
    print(f"spotbugs-cayc: PASS ({len(changed)} changed main Java files)")
    return 0


def self_test() -> int:
    previous_ci = os.environ.pop("CI", None)
    try:
        return _self_test()
    finally:
        if previous_ci is None:
            os.environ.pop("CI", None)
        else:
            os.environ["CI"] = previous_ci


def _self_test() -> int:
    with tempfile.TemporaryDirectory() as tmp:
        root = Path(tmp)
        report_dir = root / "learning-common" / "target"
        report_dir.mkdir(parents=True)
        java_dir = root / "java-demo" / "target"
        java_dir.mkdir(parents=True)
        xml = """<?xml version="1.0" encoding="UTF-8"?>
<BugCollection>
  <BugInstance type="NP_NULL" priority="2">
    <Class classname="com.example.Foo"/>
    <Method classname="com.example.Foo" name="bar" signature="()V"/>
    <SourceLine classname="com.example.Foo" start="10" sourcepath="com/example/Foo.java"/>
  </BugInstance>
</BugCollection>
"""
        (report_dir / "spotbugsXml.xml").write_text(xml, encoding="utf-8")
        (java_dir / "spotbugsXml.xml").write_text(
            '<?xml version="1.0" encoding="UTF-8"?><BugCollection></BugCollection>',
            encoding="utf-8",
        )
        baseline = root / "config" / "spotbugs-baseline.txt"
        if cmd_update(root, baseline) != 0:
            print("self-test: update failed", file=sys.stderr)
            return 1
        frozen = baseline.read_bytes()
        if cmd_check(root, baseline) != 0:
            print("self-test: check should pass", file=sys.stderr)
            return 1
        if baseline.read_bytes() != frozen:
            print("self-test: check rewrote the baseline", file=sys.stderr)
            return 1
        extra = xml.replace(
            "</BugCollection>",
            """  <BugInstance type="DLS_DEAD_LOCAL_STORE" priority="2">
    <Class classname="com.example.Foo"/>
    <Method classname="com.example.Foo" name="baz" signature="()V"/>
    <SourceLine classname="com.example.Foo" start="20" sourcepath="com/example/Foo.java"/>
  </BugInstance>
</BugCollection>
""",
        )
        (report_dir / "spotbugsXml.xml").write_text(extra, encoding="utf-8")
        if cmd_check(root, baseline) == 0:
            print("self-test: new finding should fail", file=sys.stderr)
            return 1
        if baseline.read_bytes() != frozen:
            print("self-test: failed check rewrote the baseline", file=sys.stderr)
            return 1
        os.environ["CI"] = "true"
        if cmd_update(root, baseline) != 2:
            print("self-test: CI update should be refused", file=sys.stderr)
            return 1
        if baseline.read_bytes() != frozen:
            print("self-test: refused update rewrote the baseline", file=sys.stderr)
            return 1
    print("spotbugs-ratchet self-test: PASS")
    return 0


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--mode", choices=("check", "cayc", "update", "self-test"), required=True)
    parser.add_argument("--repo", type=Path, default=None)
    parser.add_argument("--baseline", type=Path, default=Path("config/spotbugs-baseline.txt"))
    parser.add_argument("--base", default="origin/main")
    args = parser.parse_args(argv)
    if args.mode == "self-test":
        return self_test()
    repo = (args.repo or repo_root()).resolve()
    baseline = args.baseline if args.baseline.is_absolute() else repo / args.baseline
    if args.mode == "update":
        return cmd_update(repo, baseline)
    if args.mode == "check":
        return cmd_check(repo, baseline)
    return cmd_cayc(repo, baseline, args.base)


if __name__ == "__main__":
    sys.exit(main())
