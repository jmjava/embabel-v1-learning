# Embabel one-page cheat sheet

**Printable PDF:** [`print/embabel-cheatsheet.pdf`](print/embabel-cheatsheet.pdf)  
Regenerate: `./scripts/generate-cheatsheet-pdf.sh`  
**VS Code snippets:** type `emb-` — see [`.vscode/SNIPPETS.md`](../.vscode/SNIPPETS.md)  
**Starter class templates:** [`../templates/README.md`](../templates/README.md)

Keep this (or the PDF) next to the debugger.

**Versions:** default Maven pin is Embabel **1.0**; `-Pembabel-15` compiles **1.5** extras.
See [`VERSIONS.md`](VERSIONS.md). Rules below are extracted from the
[User Guide](https://docs.embabel.com/embabel-agent/guide/1.5.0-SNAPSHOT/) and
[Cookbook 1.5](https://docs.embabel.com/embabel-cookbook/1.5.0/) — not a second
copy of the cookbook travel recipes.

## Mental model (30 seconds)

```
User/system input → Blackboard objects (typed)
                 → Planner picks next @Action
                 → Action may call LLM / code / tools / subagents
                 → Return value posts new types → REPLAN
                 → @AchievesGoal return type → DONE
```

OODA loop: **Observe** blackboard → **Orient** conditions → **Decide** plan → **Act**.

Guide core objects: **Actions** (steps) · **Goals** (what “done” is) · **Conditions**
(reassessed after every action) · **Domain model** (types *are* the flow).
Plans are inferred and rebuilt; you do not write an FSM.

## 1.0 vs 1.5 (what to add, not replace)

| | 1.0 (always) | 1.5 extras (`-Pembabel-15`) |
|---|--------------|-----------------------------|
| Plan | GOAP / Utility / Hybrid / Supervisor | same planners |
| Create typed object | `createObject` / `creating(T).fromPrompt` | + `thinking()`, `fromMessages` |
| Soft fail | `createObjectIfPossible` → `null` | same; thinking variant wraps `ThinkingResponse` |
| Stream | not in this curriculum | `StreamingPromptRunnerBuilder.createObjectStream` |
| Tools | `withToolObject` / groups / `@LlmTool` | + `withToolCallInspectors` |
| Models | OpenAI / Anthropic starters | + DashScope, BYOK, templated system prompts |

## Cookbook rules (extracted)

1. **Type chaining** — classifier return type picks the next action; no `if` in the planner.
2. **`@Condition`** — boolean gate for mutually exclusive siblings (direct vs one-stop).
3. **Stuck** — leftover type with no matching action → `STUCK` (seed facts or `REPLAN`).
4. **`cost`** — same input/output shape → cheaper action wins.
5. **RepeatUntil** — evaluator returns `TextFeedback(score, text)`; stop at threshold or `maxIterations`.
6. **`creating(T)`** — `Ai` → `PromptRunner` → example + optional validation → `fromPrompt`.
7. **`createObjectIfPossible`** — incomplete prompt returns `null` (warn, don’t throw).
8. **Messages** — `SystemMessage` + `UserMessage` via `fromMessages`, not one concatenated blob.
9. **Thinking (1.5)** — `thinking()` only if `supportsThinking()`; read `thinkingBlocks`.
10. **Streaming (1.5)** — newline JSON → typed flux; check `supportsStreaming()`.
11. **Tool call** — `@LlmTool` is invisible until `withToolObject`; inspectors log calls.

## Annotations

| Annotation | On | Meaning |
|------------|----|---------|
| `@Agent(description=...)` | class | Spring component + agent; description used for selection |
| `@EmbabelComponent` | class | Reusable actions/conditions, not a full agent |
| `@Action` | method | Planner-callable step; params/return types drive GOAP |
| `@AchievesGoal` | method | Completing this action finishes the process |
| `@Condition(name=...)` | method | Boolean world-state fact (keep side-effect free) |
| `@RequireNameMatch` | param | Bind by blackboard name, not just type |
| `@State` | type | Scopes which actions are live; enables loops |
| `@Export(remote=true)` | goal | Expose for shell / MCP / A2A |
| `@LlmTool` | method | Custom in-process tool method |
| `@Tool` (Spring AI) | domain method | Exposed only via `withToolObject(domain)` |

### `@Action` knobs

| Attribute | Use when |
|-----------|----------|
| `pre` / `post` | Named conditions must be true / become true |
| `cost` / `value` | GOAP cost vs Utility value |
| `canRerun` | Action may execute more than once |
| `outputBinding` | Name the return for later `@RequireNameMatch` |
| `clearBlackboard` | Looping `@State` actions (not goal actions) |
| `description` | Especially important for **Supervisor** |

## LLM call patterns

```java
// Java — typed create
ai.withDefaultLlm().createObject(prompt, Story.class);
ai.withLlm(LlmOptions.withAutoLlm().withTemperature(0.7))
  .withPromptContributor(persona)
  .creating(Story.class).fromPrompt(prompt);
ai.withDefaultLlm().createObjectIfPossible(prompt, Person.class); // may null → replan
```

```kotlin
// Kotlin — reified helpers + OperationContext
context.ai().withDefaultLlm().createObject<Story>(prompt)
context.ai().withDefaultLlm().createObjectIfPossible<Person>(prompt) // Person?
fromForm<PersonProfile>("Fill this in") // HITL
```

## Tools (remember the attachment scope)

| API | What it does |
|-----|----------------|
| `withToolObject(domain)` | Expose `@Tool` methods on that instance |
| `withToolGroup(CoreToolGroups.WEB)` | Capability bundle |
| `withTool(Tool.fromInstance(...).getFirst())` | Custom `@LlmTool` |
| `withTools(Subagent.ofClass(...).consuming(...))` | Agent-as-tool |
| `ToolCallContext` param | Out-of-band metadata; **not** in LLM schema |

Tools attach to a **PromptRunner**, not the whole agent forever.

## Planners

| Planner | Picks actions by… | Best for | Risk |
|---------|-------------------|----------|------|
| **GOAP** (default) | Cheap typed path to goal | Business workflows | Skips opportunistic side quests |
| **UTILITY** | Highest net value | Triage / exploration | May not terminate cleanly |
| **HYBRID** | Utility gather + goal stop | Gather-then-synthesize | Needs real goal |
| **SUPERVISOR** | LLM chooses typed tools | Flexible multi-step | Nondeterministic + extra LLM cost |

## Blackboard rules of thumb

1. Latest object of a type wins — unless named binding.
2. Returning a new type = posting an effect.
3. `null` / failed `createObjectIfPossible` → replan.
4. Condition methods may be called often → pure functions.
5. State transitions hide previous state objects.

## Invocation (from app code)

```java
AgentInvocation.builder(agentPlatform)
  .options(ProcessOptions.DEFAULT.withVerbosity(Verbosity.DEFAULT.withShowPrompts(true)))
  .build(ReviewedStory.class)
  .invoke(new UserInput("..."));
```

Selection is usually by **goal return type**.

## Java vs Kotlin (5 lines)

| | Java | Kotlin |
|---|------|--------|
| DTO | `record` | `data class` |
| Context | often `Ai` | often `OperationContext` |
| Create | `Class` arg | reified `create` / `createObject` |
| HITL | `WaitFor.formSubmission` | `fromForm` |
| Agents | annotations | annotations **or** DSL `@Bean` |

## Debug first stops

1. First `@Action` entry  
2. `createObject` / `fromPrompt` line  
3. Action `return` (blackboard post)  
4. `@Condition` / planner choice  
5. Goal `@AchievesGoal` method  

Full map: [`BREAKPOINTS.md`](BREAKPOINTS.md).

## Don't forget

- Source order of actions ≠ execution order  
- `@Tool` without `withToolObject` = invisible  
- High `cost` on HITL so it's a fallback  
- Supervisor needs good action `description`s  
- StuckHandler recovers — it doesn't fix a bad domain model
- Official cookbook is recipes; this page is the recall list
- Memory OS palace: cheat-sheet films — [`videos/memory-os/embabel-cheatsheet.md`](videos/memory-os/embabel-cheatsheet.md)
- Memory OS study campus (all concepts, hero evolutions) — [`videos/memory-os/STUDY_CAMPUS.md`](videos/memory-os/STUDY_CAMPUS.md)
- External blogs — [`READING.md`](READING.md)
