# Embabel starter class templates

Copy-paste skeletons for common Embabel patterns.  
**Java:** `templates/java/` · **Kotlin:** `templates/kotlin/`

## How to use

1. Copy a file into `java-demo` or `kotlin-demo` (or your app).
2. Change `package com.example.embabel…` to your package.
3. Rename types / fill `TODO` prompts.
4. Or use VS Code snippets (`emb-…`) for inline inserts — [`.vscode/SNIPPETS.md`](../.vscode/SNIPPETS.md).

These are **not** compiled by the Maven build (outside module source roots).

## Template map

| Pattern | Java | Kotlin | Lesson |
|---------|------|--------|--------|
| Injected `Ai` component | `01-InjectedAiComponent.java` | `01-InjectedAiComponent.kt` | 01 |
| Basic GOAP `@Agent` | `02-GoapWriteReviewAgent.java` | `02-GoapWriteReviewAgent.kt` | 02 |
| Multi-step GOAP + code action | `03-GoapMixedCodeAgent.java` | `03-GoapMixedCodeAgent.kt` | 03 |
| Domain `@Tool` + support agent | `04-DomainToolSupportAgent.java` | `04-DomainToolSupportAgent.kt` | 04 |
| HITL fallback agent | `05-HitlFallbackAgent.java` | `05-HitlFallbackAgent.kt` | 05 |
| Conditions + named bindings | `06-ConditionalResearchAgent.java` | `06-ConditionalResearchAgent.kt` | 06 |
| Tool group + `@LlmTool` | `07-ToolingAgent.java` | `07-ToolingAgent.kt` | 07 |
| Subagent orchestrator | `08-SubagentOrchestrator.java` | `08-SubagentOrchestrator.kt` | 08 |
| RepeatUntil loop agent | `09-RepeatUntilAgent.java` | `09-RepeatUntilAgent.kt` | 09 |
| Supervisor planner agent | `10-SupervisorAgent.java` | `10-SupervisorAgent.kt` | 10 |
| Utility planner agent | `10-UtilityAgent.java` | `10-UtilityAgent.kt` | 10 |
| `@State` loop agent | `11-StatefulLoopAgent.java` | `11-StatefulLoopAgent.kt` | 11 |
| Guardrailed agent | `12-GuardedAgent.java` | `12-GuardedAgent.kt` | 12 |
| StuckHandler agent | `13-StuckHandlerAgent.java` | `13-StuckHandlerAgent.kt` | 13 |
| Invocation service | `14-InvocationService.java` | `14-InvocationService.kt` | 14 |
| `@EmbabelComponent` library | `15-EmbabelComponentLibrary.java` | `15-EmbabelComponentLibrary.kt` | 10 |
| Kotlin DSL agent + `@Bean` | — | `16-DslAgent.kt` | 15 |
| Guided unit test | `99-GuidedUnitTest.java` | `99-GuidedUnitTest.kt` | — |

## Suggested first copies

1. `02-GoapWriteReviewAgent` — default starting point  
2. `04-DomainToolSupportAgent` — DICE / tools  
3. `10-SupervisorAgent` or `10-UtilityAgent` — non-GOAP planners  
4. `16-DslAgent.kt` — Kotlin-only compact flows  
