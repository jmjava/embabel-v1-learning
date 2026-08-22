# Embabel Guided Walkthrough

This repository is a study curriculum for [Embabel Agent Framework](https://docs.embabel.com/embabel-agent/guide/1.0.0-SNAPSHOT/) **1.5.0**, with **Java** and **Kotlin** side-by-side demos, an official [Cookbook 1.5](https://github.com/embabel/embabel-cookbook) map, linkable Javadoc/KDoc, and unit tests designed for step-debugging.

## Starter class templates

Full-file skeletons (Java + Kotlin) for each common pattern: [`../templates/README.md`](../templates/README.md).

## VS Code / Cursor snippets

Type `emb-` in a `.java` / `.kt` file. Full list: [`.vscode/SNIPPETS.md`](../.vscode/SNIPPETS.md).

## Study aids (start here if short on time)

| Doc | Use it for |
|-----|------------|
| [`TOP_10.md`](TOP_10.md) | The ten ideas to memorize |
| [`CHEATSHEET.md`](CHEATSHEET.md) | API / annotation reference (Markdown) |
| [`print/embabel-cheatsheet.pdf`](print/embabel-cheatsheet.pdf) | **Printable PDF** cheat sheet |
| [`CHAPTER_SUMMARIES.md`](CHAPTER_SUMMARIES.md) | Per-lesson “what / why / top points” |
| [`QUIZ_FLASHCARDS.md`](QUIZ_FLASHCARDS.md) | Flashcard Q&A per lesson |
| [`REVIEW_CIRCUIT.md`](REVIEW_CIRCUIT.md) | **60–90 min checkbox checklist** |
| [`BREAKPOINTS.md`](BREAKPOINTS.md) | Debugger stop map |
| [`JAVA_VS_KOTLIN.md`](JAVA_VS_KOTLIN.md) | Language deltas |
| [`ADVANCED_NUANCES.md`](ADVANCED_NUANCES.md) | Sharp edges after the basics |

## How to study

1. Prefer the timed path: [`REVIEW_CIRCUIT.md`](REVIEW_CIRCUIT.md) (checkboxes).
2. Skim [`TOP_10.md`](TOP_10.md) and keep the [cheat sheet PDF](print/embabel-cheatsheet.pdf) open.
3. Read [`LessonOrder`](../learning-common/src/main/java/com/embabel/learning/common/curriculum/LessonOrder.java) for the sequence.
4. Before each lesson, read its section in [`CHAPTER_SUMMARIES.md`](CHAPTER_SUMMARIES.md).
5. Open the lesson package (Java `package-info.java` and/or the Kotlin class KDoc).
6. Read the agent/service class — follow `{@link}` / KDoc cross-links to counterparts.
7. Debug the matching `*GuidedTest` (breakpoints are declared with `@DebugGuide`).
8. Drill with [`QUIZ_FLASHCARDS.md`](QUIZ_FLASHCARDS.md); compare languages in [`JAVA_VS_KOTLIN.md`](JAVA_VS_KOTLIN.md).

Generate API docs:

```bash
./mvnw -pl learning-common,java-demo javadoc:javadoc
# Open java-demo/target/site/apidocs/index.html
```

## Curriculum map

| # | Concept | Java | Kotlin | Guided test |
|---|---------|------|--------|-------------|
| 01 | Injected `Ai` | `lesson01.InjectedAiDemo` | `lesson01.InjectedAiDemo` | both |
| 02 | First `@Agent` Write/Review | `lesson02.WriteAndReviewAgent` | `lesson02.WriteAndReviewAgent` | both |
| 03 | Multi-action GOAP | `lesson03.PersonalizedBriefAgent` | `lesson03.PersonalizedBriefAgent` | Java |
| 04 | Domain tools (DICE) | `lesson04.BankSupportAgent` | `lesson04.BankSupportAgent` | Java |
| 05 | HITL `WaitFor` | `lesson05.HitlProfileAgent` | `lesson05.HitlProfileAgent` | — |
| 06 | Conditions & bindings | `lesson06.ConditionalResearchAgent` | `lesson06.ConditionalResearchAgent` | both |
| 07 | Tool groups / `@LlmTool` | `lesson07.ToolingAgent` | `lesson07.ToolingAgent` | — |
| 08 | Subagents | `lesson08.SubagentHandoffAgent` | `lesson08.SubagentHandoffAgent` | — |
| 09 | RepeatUntil workflow | `lesson09.RepeatUntilStoryAgent` | `lesson09.RepeatUntilStoryAgent` | — |
| 10 | Planner types | `lesson10.*` | `lesson10.*` | Java |
| 11 | `@State` loops | `lesson11.StatefulDraftAgent` | `lesson11.StatefulDraftAgent` | — |
| 12 | Guardrails | `lesson12.GuardedJokeAgent` | `lesson12.GuardedJokeAgent` | both |
| 13 | StuckHandler | `lesson13.SelfUnstickingAgent` | `lesson13.SelfUnstickingAgent` | Java |
| 14 | AgentInvocation | `lesson14.InvocationDemoService` | `lesson14.InvocationDemoService` | — |
| 15 | Kotlin DSL | — | `lesson15.FactCheckerDsl` | Kotlin |
| 16 | Cookbook type chaining | `cookbook15.TypeChainingTravelAgent` | same | both |
| 17 | Cookbook action cost | `cookbook15.HeuristicTravelAgent` | same | both |
| 18 | `createObjectIfPossible` | `cookbook15.PossibleTripPlanner` | same | both |
| 19 | Thinking traces | `cookbook15.ThinkingTripPlanner` | same | Java |
| 20 | Streaming objects | `cookbook15.StreamingTripPlanner` | same | Java |
| 21 | Messages + tool inspectors | `cookbook15.MessageAndToolTripPlanner` | same | Java |

1.5 cookbook chapter table and video scripts: [`COOKBOOK_15.md`](COOKBOOK_15.md).

## Official docs cross-reference

Primary guide: https://docs.embabel.com/embabel-agent/guide/1.0.0-SNAPSHOT/

Especially: annotation model (§4.6), planners (§4.20), tools (§4.9), states (§4.19), testing (§4.38), RAG (§4.12), MCP (§4.34).

## Run

```bash
# Unit tests (no API keys required)
./mvnw test

# Interactive shell (needs OPENAI_API_KEY and/or ANTHROPIC_API_KEY)
./mvnw -pl java-demo spring-boot:run
./mvnw -pl kotlin-demo spring-boot:run
```

## Deep dive

After lessons 06–11, read [`ADVANCED_NUANCES.md`](ADVANCED_NUANCES.md).

## Spec completeness Q&A

See [`SPEC_COMPLETENESS.md`](SPEC_COMPLETENESS.md) for questions that make this learning spec even more complete — and answers already applied in this repo.
