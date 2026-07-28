# Embabel VS Code / Cursor snippets

Project snippets live in this folder and load automatically when you open the repo.

| File | Language |
|------|----------|
| `embabel-java.code-snippets` | Java |
| `embabel-kotlin.code-snippets` | Kotlin |

## How to use

1. Open a `.java` or `.kt` file.
2. Type a prefix starting with `emb-` (see table below).
3. Accept the suggestion (Tab / Enter) and fill tab-stops.

If snippets don’t appear: **Command Palette → “Snippets: Configure User Snippets”** is unrelated — these are **workspace** snippets. Reload the window once after pull (`Developer: Reload Window`).

Cursor uses the same `.vscode/*.code-snippets` format as VS Code.

## Prefix cheat sheet

All prefixes work in **both** Java and Kotlin unless noted.

| Prefix | Inserts |
|--------|---------|
| `emb-agent` | Full `@Agent` class with step + goal |
| `emb-action` | Single `@Action` + typed LLM create |
| `emb-goal` | `@AchievesGoal` + `@Action` |
| `emb-create` | `createObject` call |
| `emb-create-if` | `createObjectIfPossible` |
| `emb-creating` | `creating(...).withExample(...).fromPrompt` |
| `emb-llm` | `LlmOptions` + temperature |
| `emb-tool-object` | `withToolObject` (DICE) |
| `emb-tool-web` | `CoreToolGroups.WEB` |
| `emb-llm-tool` | Custom `@LlmTool` class |
| `emb-domain-tool` | Domain record/`@Tool` (**Java**) |
| `emb-hitl` | HITL form (`WaitFor` / `fromForm`) |
| `emb-condition` | `@Condition` ok/bad pair |
| `emb-bind` | `outputBinding` + `@RequireNameMatch` |
| `emb-subagent` | `Subagent.ofClass(...).consuming(...)` |
| `emb-repeat` | `RepeatUntilAcceptableBuilder` loop |
| `emb-supervisor` | Supervisor planner agent |
| `emb-utility` | Utility planner agent |
| `emb-state` | `@State` loop types |
| `emb-guardrail` | `UserInputGuardRail` |
| `emb-stuck` | `StuckHandler` + REPLAN |
| `emb-invoke` | `AgentInvocation` + `ProcessOptions` |
| `emb-test` | `FakeOperationContext` unit test |
| `emb-component` | `@EmbabelComponent` (**Java**) |
| `emb-dsl` | Kotlin DSL `agent { }` + `@Bean` (**Kotlin**) |
| `emb-validated` | data class + `@field:Pattern` (**Kotlin**) |

## Full-file starter templates

For complete class skeletons (not just snippets), copy from [`templates/`](../templates/) — map in [`templates/README.md`](../templates/README.md).

## Tips

- After expanding `emb-agent`, rename types at the tab-stops before filling prompts.
- Pair with the study PDF: `docs/print/embabel-cheatsheet.pdf`.
- Lesson counterparts in `java-demo` / `kotlin-demo` show the same patterns fully wired.
