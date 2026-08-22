# Embabel V1 Learning Demo

Study project for the [Embabel Agent Framework](https://github.com/embabel/embabel-agent) on **one `main`**: Embabel **1.0** by default, **1.5** extras behind `-Pembabel-15`. Java + Kotlin demos, guided unit tests, and a cheat sheet extracted from the official [User Guide](https://docs.embabel.com/embabel-agent/guide/1.5.0-SNAPSHOT/) and [Cookbook](https://docs.embabel.com/embabel-cookbook/1.5.0/).

This is not a second copy of the cookbook travel recipes. Memorize the [cheat sheet](docs/CHEATSHEET.md).

## Quick start

```bash
./mvnw test                 # Embabel 1.0 — lessons 01–15
./mvnw test -Pembabel-15    # Embabel 1.5 — plus thinking / streaming extras
./mvnw -pl learning-common,java-demo javadoc:javadoc
```

Version switch: **[`docs/VERSIONS.md`](docs/VERSIONS.md)**.

Interactive shells (optional LLM keys):

```bash
export OPENAI_API_KEY=...          # and/or ANTHROPIC_API_KEY
./mvnw -pl java-demo spring-boot:run
./mvnw -pl kotlin-demo spring-boot:run
```

## Starter class templates

Copy-paste skeletons for common agent patterns (not compiled by Maven):

**[`templates/`](templates/)** — Java + Kotlin side-by-side · map in [`templates/README.md`](templates/README.md)

## VS Code / Cursor snippets

Workspace snippets (prefix `emb-…`) load from [`.vscode/`](.vscode/). See [`.vscode/SNIPPETS.md`](.vscode/SNIPPETS.md).

Examples: `emb-agent`, `emb-action`, `emb-hitl`, `emb-subagent`, `emb-repeat`, `emb-dsl` (Kotlin).

## Study path

Start at **[`docs/CHEATSHEET.md`](docs/CHEATSHEET.md)**, then **[`docs/WALKTHROUGH.md`](docs/WALKTHROUGH.md)**.  
Official-source map: **[`docs/COOKBOOK_15.md`](docs/COOKBOOK_15.md)**.  
Memory OS: **plan only** — [`docs/videos/memory-os/PLAN.md`](docs/videos/memory-os/PLAN.md) (renderer is upgrading; do not generate videos yet).

| Doc | Purpose |
|-----|---------|
| [docs/CHEATSHEET.md](docs/CHEATSHEET.md) | Extracted API / planner rules (primary) |
| [docs/VERSIONS.md](docs/VERSIONS.md) | 1.0 vs 1.5 on the same branch |
| [docs/COOKBOOK_15.md](docs/COOKBOOK_15.md) | What we pulled from Guide + Cookbook |
| [docs/REVIEW_CIRCUIT.md](docs/REVIEW_CIRCUIT.md) | 60–90 min checkbox review circuit |
| [docs/TOP_10.md](docs/TOP_10.md) | Ten ideas to memorize |
| [docs/print/embabel-cheatsheet.pdf](docs/print/embabel-cheatsheet.pdf) | Printable PDF cheat sheet |
| [docs/CHAPTER_SUMMARIES.md](docs/CHAPTER_SUMMARIES.md) | Per-lesson summaries + top points |
| [docs/QUIZ_FLASHCARDS.md](docs/QUIZ_FLASHCARDS.md) | Flashcard Q&A per lesson |
| [docs/WALKTHROUGH.md](docs/WALKTHROUGH.md) | Full lesson map + how to study |
| [docs/JAVA_VS_KOTLIN.md](docs/JAVA_VS_KOTLIN.md) | Language differences |
| [docs/BREAKPOINTS.md](docs/BREAKPOINTS.md) | Debugger stop map |
| [docs/ADVANCED_NUANCES.md](docs/ADVANCED_NUANCES.md) | Sharp edges |
| [docs/SPEC_COMPLETENESS.md](docs/SPEC_COMPLETENESS.md) | Spec Q&A + next extensions |

Curriculum order is defined in
`learning-common/.../curriculum/LessonOrder.java`.

## Modules

- `learning-common` — shared domain types, `@Lesson` / `@DebugGuide`, source catalog
- `java-demo` — Java agents + guided tests + shell app (`cookbook15` only with `-Pembabel-15`)
- `kotlin-demo` — Kotlin agents (incl. DSL) + guided tests + shell app
- `docs/videos/memory-os` — palace **plan** (generation paused)

## What you will learn

GOAP type-driven planning, annotation agents, injected `Ai`, domain tools (DICE), HITL, conditions/bindings, tool groups, subagents, RepeatUntil workflows, Utility/Supervisor planners, `@State` loops, guardrails, stuck recovery, `AgentInvocation`, Kotlin DSL agents. On 1.5: action cost, `createObjectIfPossible`, thinking traces, streaming objects, `fromMessages`, tool-call inspectors.
