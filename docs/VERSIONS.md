# Embabel 1.0 and 1.5 on the same branch

This repository stays on **one `main`**. Default Maven coordinates compile against
**Embabel Agent 1.0.0**. Embabel **1.5.0** extras (thinking, streaming, message
lists, tool inspectors) compile only when you opt in.

Study from the cheat sheet, not from a second copy of the official cookbook
travel recipes:

- [`CHEATSHEET.md`](CHEATSHEET.md) — extracted concepts
- [`COOKBOOK_15.md`](COOKBOOK_15.md) — official-source map (what we pulled, not what we cloned)

## Switch versions

```bash
# 1.0 — lessons 01–15 (default)
./mvnw test

# 1.5 — lessons 01–15 plus cookbook15 extras
./mvnw test -Pembabel-15
```

| Profile | `embabel-agent.version` | Compiles |
|---------|-------------------------|----------|
| *(default)* | `1.0.0` | `lesson01`–`lesson15` |
| `-Pembabel-15` | `1.5.0` | those lessons + `**/cookbook15/**` |

The parent property `embabel.extra.excludes` is `**/cookbook15/**` unless the
`embabel-15` profile replaces it with a never-match pattern.

## Why not pin the whole tree to 1.5?

1.5 is the current Cookbook publication line, but 1.0 is still the baseline
mental model (GOAP types, conditions, HITL, planners). Pinning `main` to 1.5
made the default classpath a 1.5-only fork. Profiles keep both lines reviewable
on the same commit.

## Docs by version

| Source | 1.0 | 1.5 |
|--------|-----|-----|
| User Guide | [guide/1.0.0-SNAPSHOT](https://docs.embabel.com/embabel-agent/guide/1.0.0-SNAPSHOT/) | [guide/1.5.0-SNAPSHOT](https://docs.embabel.com/embabel-agent/guide/1.5.0-SNAPSHOT/) |
| Cookbook | same planner/API ideas; recipes published under 1.5 | [cookbook/1.5.0](https://docs.embabel.com/embabel-cookbook/1.5.0/) |
| Framework release | [v1.0.0](https://github.com/embabel/embabel-agent/releases/tag/v1.0.0) | [v1.5.0](https://github.com/embabel/embabel-agent/releases/tag/v1.5.0) |

1.5 highlights that matter for this cheat sheet: `PromptRunner.thinking()`,
`StreamingPromptRunnerBuilder`, vendor-neutral streaming tool loops,
`TemplatedPromptRunnerBuilder`, DashScope / BYOK starters. Most 1.0 annotations
and GOAP rules still apply.
