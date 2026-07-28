# Embabel V1 Learning Demo

Comprehensive study project for the [Embabel Agent Framework](https://github.com/embabel/embabel-agent) (**1.0.0**): Java + Kotlin demos, linkable Javadoc curriculum, and guided unit tests with breakpoint maps.

## Quick start

```bash
./mvnw test
./mvnw -pl learning-common,java-demo javadoc:javadoc
```

Interactive shells (optional LLM keys):

```bash
export OPENAI_API_KEY=...          # and/or ANTHROPIC_API_KEY
./mvnw -pl java-demo spring-boot:run
./mvnw -pl kotlin-demo spring-boot:run
```

## VS Code / Cursor snippets

Workspace snippets (prefix `emb-…`) load from [`.vscode/`](.vscode/). See [`.vscode/SNIPPETS.md`](.vscode/SNIPPETS.md).

Examples: `emb-agent`, `emb-action`, `emb-hitl`, `emb-subagent`, `emb-repeat`, `emb-dsl` (Kotlin).

## Study path

Start at **[`docs/WALKTHROUGH.md`](docs/WALKTHROUGH.md)**.

| Doc | Purpose |
|-----|---------|
| [docs/REVIEW_CIRCUIT.md](docs/REVIEW_CIRCUIT.md) | 60–90 min checkbox review circuit |
| [docs/TOP_10.md](docs/TOP_10.md) | Ten ideas to memorize |
| [docs/CHEATSHEET.md](docs/CHEATSHEET.md) | API / annotation cheat sheet (Markdown) |
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

- `learning-common` — shared domain types, `@Lesson` / `@DebugGuide`
- `java-demo` — Java agents + guided tests + shell app
- `kotlin-demo` — Kotlin agents (incl. DSL lesson) + guided tests + shell app

## What you will learn

GOAP type-driven planning, annotation agents, injected `Ai`, domain tools (DICE), HITL, conditions/bindings, tool groups, subagents, RepeatUntil workflows, Utility/Supervisor planners, `@State` loops, guardrails, stuck recovery, `AgentInvocation`, and Kotlin DSL agents — with nuances called out in Javadoc/KDoc.
