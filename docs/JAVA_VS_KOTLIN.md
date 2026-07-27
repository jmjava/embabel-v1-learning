# Java vs Kotlin in Embabel

Embabel is written in Kotlin but designed for natural Java usage. This project mirrors lessons in both languages so you can feel the deltas.

## Side-by-side differences

| Topic | Java | Kotlin |
|-------|------|--------|
| Domain DTOs | `record` | `data class` |
| LLM helper param | Often `Ai ai` | Often `OperationContext context` then `context.ai()` |
| Typed create | `createObject(prompt, Story.class)` / `creating(Story.class).fromPrompt(...)` | reified `create<Story>(...)` / `createObject<Story>(...)` |
| Nullable extraction | `createObjectIfPossible` may return `null` | return type `PersonProfile?` |
| Constructor `@Value` | `@Value` on ctor param | `@param:Value("\${...}")` |
| Bean Validation | `@Pattern` on record component | `@field:Pattern` on data-class property |
| HITL | `WaitFor.formSubmission(msg, Type.class)` | `fromForm<Type>(msg)` |
| Annotation arrays | `pre = {"cond"}` | `pre = ["cond"]` |
| Class literals | `Foo.class` | `Foo::class.java` / `Foo::class` for some DSL APIs |
| Agent definition | Annotation model (recommended) | Annotation **or** DSL `agent { ... }` `@Bean` |
| State classes | Nested `record` (implicitly static) | Prefer **top-level** / non-`inner` classes |
| Export starting types | `startingInputTypes = { UserInput.class }` | `startingInputTypes = [UserInput::class]` |

## What stays the same

- GOAP type-driven planning semantics
- Blackboard / conditions / `@AchievesGoal`
- `ProcessOptions`, `AgentInvocation`, planner enums
- Tool groups, `Subagent`, workflow builders
- Testing with `FakeOperationContext` / `FakePromptRunner`

## Recommended study pairing

1. Read Java `WriteAndReviewAgent` then Kotlin counterpart — note `Ai` vs `OperationContext`.
2. Compare HITL lessons for `WaitFor` vs `fromForm`.
3. Read Kotlin-only Lesson 15 DSL after you understand annotation agents.
4. Keep Lesson 11 state nuances in mind before modeling loops.

## Nuances that surprise people

1. **Action source order ≠ execution order.** Types (and conditions) drive GOAP.
2. **`@Tool` methods are not global.** They must be passed with `withToolObject`.
3. **Tools are per PromptRunner.** Attaching a tool in one action does not attach it everywhere.
4. **`createObjectIfPossible` + high-cost HITL** is a common fallback pattern.
5. **Supervisor is type-informed but nondeterministic**; prefer GOAP for business workflows.
6. **Utility needs clear termination** (`@AchievesGoal` / policies) or it keeps exploring.
7. **StuckHandler is recovery, not a modeling substitute.**
