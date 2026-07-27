# Breakpoint cheat sheet

Prefer debugging the `*GuidedTest` classes — each method is annotated with `@DebugGuide` listing exact stops.

## Universal Embabel stops

| When studying… | Break here | Watch |
|----------------|------------|-------|
| Prompt shape | first line of `createObject` / `fromPrompt` / `generateText` | prompt string, llm options |
| Model choice | `withLlm` / `withAutoLlm` / `withDefaultLlm` | temperature, model criteria |
| Planning | action method entry after previous return | which blackboard types exist |
| Tools | `withToolObject` / `withTool` / `@LlmTool` method body | tool schema, ToolCallContext |
| HITL | `WaitFor.formSubmission` / `fromForm` | process waiting transition |
| Conditions | `@Condition` methods | boolean results; call frequency |
| Bindings | producer `outputBinding`, consumer `@RequireNameMatch` | which instance is selected |
| Subagents | `Subagent.ofClass(...).consuming(...)` | child agent first action |
| Workflows | `RepeatUntilAcceptableBuilder.repeating` + `withEvaluator` | score/feedback |
| States | action returning `@State` type; `clearBlackboard=true` | hidden previous states |
| Guardrails | `validate(...)` | `isValid`, severity |
| Stuck | `handleStuck` | REPLAN vs NO_RESOLUTION |
| Invocation | `AgentInvocation.invoke` | ProcessOptions verbosity |

## IDE tips

- **IntelliJ**: Debug the JUnit method (Ctrl/Cmd+Shift+D). Enable "Break at first line" only if lost.
- Put a breakpoint on **return** of each `@Action` to see blackboard posts.
- For integration tests (`EmbabelMockitoIntegrationTest`), break on `whenCreateObject` stubs and `invocation.invoke`.
- Compare Java/Kotlin tests for Lesson 02 in two debugger sessions — same flow, different call shape.

## Auto-suggested breakpoints

`@DebugGuide` is retained at runtime. You can write a small IntelliJ scratch or test listener that reads the annotation and prints suggested stops before a suite runs (future enhancement noted in SPEC_COMPLETENESS).
