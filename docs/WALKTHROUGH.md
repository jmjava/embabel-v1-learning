# Embabel Guided Walkthrough

This repository is a study curriculum for [Embabel Agent Framework](https://docs.embabel.com/embabel-agent/guide/1.0.0-SNAPSHOT/) **1.0.0**, with **Java** and **Kotlin** side-by-side demos, linkable Javadoc/KDoc, and unit tests designed for step-debugging.

## How to study

1. Read [`LessonOrder`](../learning-common/src/main/java/com/embabel/learning/common/curriculum/LessonOrder.java) for the sequence.
2. Open the lesson package (Java `package-info.java` and/or the Kotlin class KDoc).
3. Read the agent/service class — follow `{@link}` / KDoc cross-links to counterparts.
4. Debug the matching `*GuidedTest` (breakpoints are declared with `@DebugGuide`).
5. Compare languages in [`JAVA_VS_KOTLIN.md`](JAVA_VS_KOTLIN.md).
6. Keep [`BREAKPOINTS.md`](BREAKPOINTS.md) open as a cheat sheet.

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
