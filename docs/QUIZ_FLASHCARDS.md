# Flashcard-style quiz (per lesson)

How to use:
1. Cover the **A:** line (or fold the page).
2. Say the answer out loud.
3. Check, then mark ✅ / ❌ on [`REVIEW_CIRCUIT.md`](REVIEW_CIRCUIT.md).

Tip: study front→back first, then reverse (read the answer, recall the question topic).

---

## Lesson 01 — Injected `Ai`

**Q:** Do you need `@Agent` to call an LLM with Embabel?  
**A:** No. Inject `Ai` into any Spring component.

**Q:** Which API builds a typed object with few-shot examples?  
**A:** `creating(Class).withExample(...).fromPrompt(...)`.

**Q:** In Kotlin, how do you put Bean Validation on a data-class property?  
**A:** Use `@field:Pattern` (use-site target), not bare `@Pattern`.

---

## Lesson 02 — First `@Agent`

**Q:** What decides action order in a GOAP agent?  
**A:** Parameter/return types (and conditions)—not source order.

**Q:** What annotation marks process completion?  
**A:** `@AchievesGoal` on an `@Action`.

**Q:** Java often takes `Ai`; what does Kotlin often take instead?  
**A:** `OperationContext`, then `context.ai()`.

---

## Lesson 03 — Multi-action GOAP

**Q:** What does returning a new type from an action do?  
**A:** Posts that object onto the blackboard (an effect for planning).

**Q:** Why use `createObjectIfPossible` instead of `createObject`?  
**A:** Failure can return null and trigger replanning (e.g. toward HITL).

**Q:** Must every `@Action` call an LLM?  
**A:** No—deterministic Spring/code actions are first-class.

---

## Lesson 04 — Domain tools (DICE)

**Q:** Are `@Tool` methods automatically available to every LLM call?  
**A:** No. Pass the instance with `withToolObject(...)`.

**Q:** What happens to domain methods without `@Tool`?  
**A:** They stay application-private (not in the tool schema).

**Q:** What does DICE emphasize?  
**A:** Typed domain objects grounding LLM inputs/outputs and tools.

---

## Lesson 05 — HITL

**Q:** Java API to pause for a typed form?  
**A:** `WaitFor.formSubmission(message, Type.class)`.

**Q:** Kotlin equivalent?  
**A:** `fromForm<Type>(message)` (or `fromForm(message, Type::class.java)`).

**Q:** Why set HITL `cost = 100`?  
**A:** So GOAP prefers cheaper extraction paths; HITL is the fallback.

---

## Lesson 06 — Conditions & bindings

**Q:** Should `@Condition` methods have side effects?  
**A:** No—they may be evaluated often; keep them pure.

**Q:** How do you select among multiple blackboard objects of the same type?  
**A:** Producer `outputBinding` + consumer `@RequireNameMatch`.

**Q:** What does `canRerun = true` allow?  
**A:** The action may execute more than once in a process.

---

## Lesson 07 — Tools

**Q:** Where are tools attached—agent-wide forever, or elsewhere?  
**A:** On a specific `PromptRunner` call chain.

**Q:** Is `ToolCallContext` visible in the LLM tool JSON schema?  
**A:** No—it is out-of-band metadata (auth/tenant/etc.).

**Q:** How do you turn a `@LlmTool` class into `Tool` instances?  
**A:** `Tool.fromInstance(obj)`.

---

## Lesson 08 — Subagents

**Q:** What does `Subagent.ofClass(X).consuming(T)` define?  
**A:** The typed JSON payload the parent LLM must produce to invoke agent X.

**Q:** Do subagents inherit parent verbosity / process options?  
**A:** Yes (typically).

**Q:** Why use a subagent instead of calling methods directly?  
**A:** Let the LLM compose specialists as tools while staying typed.

---

## Lesson 09 — RepeatUntil

**Q:** Why does RepeatUntil need `ActionContext`, not just `Ai`?  
**A:** It runs a typed subprocess / workflow under the platform.

**Q:** Which two knobs bound nondeterminism in RepeatUntilAcceptable?  
**A:** `withMaxIterations` and `withScoreThreshold`.

**Q:** What does the evaluator typically return?  
**A:** `TextFeedback` (score + feedback text).

---

## Lesson 10 — Planners

**Q:** Default planner?  
**A:** GOAP.

**Q:** Which planner lets an LLM choose typed actions as tools?  
**A:** Supervisor (`PlannerType.SUPERVISOR`).

**Q:** Main risk of Utility planning?  
**A:** It may keep exploring without clear termination unless you design goals/policies.

---

## Lesson 11 — `@State`

**Q:** What triggers a state transition?  
**A:** Returning a different `@State` type from an action.

**Q:** When should you use `clearBlackboard = true`?  
**A:** On looping state actions—not casually on the final goal action.

**Q:** Kotlin pitfall for state classes?  
**A:** Avoid `inner` classes that capture the outer agent instance.

---

## Lesson 12 — Guardrails

**Q:** How do you attach a guardrail to an LLM call?  
**A:** `withGuardRails(...)` on the PromptRunner.

**Q:** What severity can block execution?  
**A:** `CRITICAL`.

**Q:** Best first unit test for a guardrail?  
**A:** Call `validate(...)` directly with an in-memory blackboard—no LLM needed.

---

## Lesson 13 — StuckHandler

**Q:** When does a stuck handler run?  
**A:** When the planner cannot find a legal next action / path.

**Q:** What should `handleStuck` often return after seeding data?  
**A:** `StuckHandlingResultCode.REPLAN`.

**Q:** Anti-pattern?  
**A:** Using stuck handlers to paper over an incomplete domain model.

---

## Lesson 14 — Invocation

**Q:** How does `AgentInvocation` usually select an agent?  
**A:** By goal return type (e.g. `ReviewedStory.class`).

**Q:** Where do verbosity, budgets, and tool-call context live?  
**A:** `ProcessOptions`.

**Q:** Are ProcessOptions inherited by subagents?  
**A:** Verbosity/options are typically inherited—design with that in mind.

---

## Lesson 15 — Kotlin DSL

**Q:** How do DSL agents get into the Spring context?  
**A:** Explicit `@Bean` registration (not stereotype scanning alone).

**Q:** Are DSL agents a different runtime from annotation agents?  
**A:** No—same platform; different authoring style.

**Q:** When is DSL especially nice?  
**A:** Compact aggregate/transform pipelines in Kotlin.

---

## Mixed review (hard mode)

**Q:** Source order of `@Action` methods equals execution order — true or false?  
**A:** False.

**Q:** Name the four planner types.  
**A:** GOAP, UTILITY, HYBRID, SUPERVISOR.

**Q:** What’s the one-line OODA mapping for Embabel?  
**A:** Observe blackboard → Orient conditions → Decide plan → Act (replan).

**Q:** `@Tool` without `withToolObject` is…  
**A:** Invisible to the model.

**Q:** Latest blackboard object of a type wins unless…  
**A:** You use named bindings (`outputBinding` + `@RequireNameMatch`).
