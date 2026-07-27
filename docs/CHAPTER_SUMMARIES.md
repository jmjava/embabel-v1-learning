# Chapter / section summaries

One page per lesson: **what**, **why**, **top points**, **code to open**, **test to debug**.

---

## Lesson 01 — Injected `Ai` (no agent)

**What:** Call Embabel’s `Ai` helper from any Spring bean.  
**Why:** You can add typed LLM features without inventing a full agent process.

**Top points**
1. `creating(Class).withExample(...).fromPrompt(...)` is the structured-output path.
2. Bean Validation on the result type constrains generation.
3. `withId(...)` names the interaction for logs.
4. No planner/blackboard involvement yet.
5. Kotlin needs `@field:Pattern` on data-class properties.

**Open:** `lesson01.InjectedAiDemo` (Java + Kotlin)  
**Debug:** `InjectedAiDemoGuidedTest`

---

## Lesson 02 — First `@Agent` (Write & Review)

**What:** Two actions; GOAP infers `UserInput → Story → ReviewedStory`.  
**Why:** This is the canonical Embabel “hello world” agent.

**Top points**
1. `@AchievesGoal` marks completion — not “last method in the file.”
2. Source order of actions does not dictate runtime order.
3. Personas / prompt contributors specialize writer vs reviewer.
4. Temperature is a per-call choice (creative write vs sober review).
5. Java often takes `Ai`; Kotlin often takes `OperationContext`.

**Open:** `lesson02.WriteAndReviewAgent`  
**Debug:** `WriteAndReviewAgentGuidedTest`

---

## Lesson 03 — Multi-action GOAP (code + LLM)

**What:** Extract person → summarize interest in Java/Kotlin code → write brief with LLM.  
**Why:** Shows type-driven chaining and non-LLM actions.

**Top points**
1. Returning a new type posts an effect onto the blackboard.
2. `createObjectIfPossible` can return null and force replanning.
3. Injected Spring services are first-class inside actions.
4. Longer chains stay readable because types document the plan.
5. Keep action methods focused: one transformation each.

**Open:** `lesson03.PersonalizedBriefAgent`  
**Debug:** `PersonalizedBriefAgentGuidedTest` (Java)

---

## Lesson 04 — Domain objects as tools (DICE)

**What:** Pass a `BankCustomer` into the LLM via `withToolObject`.  
**Why:** Domain Integrated Context Engineering — models call safe domain behavior.

**Top points**
1. Only `@Tool`-annotated methods are exposed.
2. Unannotated methods stay application-private (see `internalRiskNote`).
3. Lookup/repository code stays outside the LLM.
4. Starting input can be a typed record, not only `UserInput`.
5. Expose the minimum tool surface you can justify.

**Open:** `lesson04.BankSupportAgent`, `BankCustomer`  
**Debug:** `BankSupportAgentGuidedTest`

---

## Lesson 05 — Human-in-the-loop

**What:** Cheap LLM extract, expensive `WaitFor` / `fromForm` fallback.  
**Why:** Production agents must pause for structured human input.

**Top points**
1. HITL actions return form schema types.
2. High `cost` keeps HITL off the happy path.
3. Process enters a waiting state until submission.
4. Java: `WaitFor.formSubmission`; Kotlin: `fromForm`.
5. Combine with `createObjectIfPossible` for “try AI, else ask.”

**Open:** `lesson05.HitlProfileAgent`  
**Debug:** `HitlProfileAgentGuidedTest` (cost annotation)

---

## Lesson 06 — Conditions & named bindings

**What:** Critique-driven rewrite loop with `pre`/`post` and bindings.  
**Why:** Type presence alone is not enough for quality gates or multi-instance types.

**Top points**
1. `@Condition` methods should be pure.
2. `pre`/`post` reference condition names, not Java method names casually.
3. `outputBinding` names produced values.
4. `@RequireNameMatch` selects by name when types collide.
5. `canRerun=true` allows rewrite actions to fire again.

**Open:** `lesson06.ConditionalResearchAgent`  
**Debug:** `ConditionalResearchAgentGuidedTest`

---

## Lesson 07 — Tool groups & custom tools

**What:** `CoreToolGroups.WEB` + custom `@LlmTool` clock diagnostic.  
**Why:** Real agents need capabilities and safe custom functions.

**Top points**
1. Tool groups are capability bundles.
2. `Tool.fromInstance` discovers `@LlmTool` methods.
3. `ToolCallContext` is out-of-band (auth/tenant), not model-visible.
4. Prompt text should tell the model *when* to call tools.
5. Attachment is per PromptRunner call chain.

**Open:** `lesson07.ToolingAgent`  
**Debug:** `ToolingAgentGuidedTest`

---

## Lesson 08 — Subagent handoffs

**What:** Parent LLM invokes `WriteAndReviewAgent` as a typed tool.  
**Why:** Compose agents without hard-coding a sequential Java call.

**Top points**
1. `Subagent.ofClass(...).consuming(T)` defines the JSON the parent must fill.
2. Subagents inherit parent verbosity/options.
3. Useful for “specialist” decomposition.
4. Still strongly typed end-to-end.
5. Break in parent prompt *and* child first action.

**Open:** `lesson08.SubagentHandoffAgent`  
**Debug:** `SubagentHandoffAgentGuidedTest`

---

## Lesson 09 — RepeatUntil workflow builder

**What:** Writer/reviewer loop with score threshold via builder API.  
**Why:** Standard subprocess pattern for “good enough” generation.

**Top points**
1. Needs `ActionContext` (subprocess), not bare `Ai`.
2. `withMaxIterations` + `withScoreThreshold` bound nondeterminism.
3. Evaluator returns `TextFeedback` (score + text).
4. Builders compile down to agents/actions under the hood.
5. Some models reject custom temperature — keep options boring.

**Open:** `lesson09.RepeatUntilStoryAgent`  
**Debug:** step `repeating` + `withEvaluator` lambdas under a Fake/integration setup

---

## Lesson 10 — Planner types

**What:** Same style of actions; Supervisor kitchen + Utility triage.  
**Why:** Planner choice changes selection semantics dramatically.

**Top points**
1. GOAP = cheapest typed path to goal (default).
2. Utility = greedy highest net value; design termination.
3. Hybrid = gather opportunistically, stop on real goal.
4. Supervisor = LLM picks typed actions; needs good `description`s.
5. `@EmbabelComponent` shares action libraries without being an agent.

**Open:** `lesson10.SupervisorKitchenAgent`, `UtilityTriage*`  
**Debug:** `PlannerTypeLessonTest`

---

## Lesson 11 — `@State` machines & loops

**What:** `Drafting` → refine → `Done` → finish report.  
**Why:** Explicit stages beat fighting GOAP for iterative workflows.

**Top points**
1. Returning a state type transitions the machine.
2. Prior state objects get hidden; non-state data can remain.
3. `clearBlackboard=true` belongs on loop actions.
4. Java nested records work well; Kotlin avoid `inner` classes.
5. Goal action should be on the terminal state.

**Open:** `lesson11.StatefulDraftAgent`  
**Debug:** `StatefulDraftAgentGuidedTest`

---

## Lesson 12 — Guardrails

**What:** Block password topics before joke generation.  
**Why:** Enterprise agents need injectable validation at LLM boundaries.

**Top points**
1. Implement `UserInputGuardRail` / assistant variants.
2. `CRITICAL` severity can block execution.
3. Attach with `withGuardRails(...)` on the PromptRunner.
4. Guardrails can inspect the blackboard.
5. Unit-test the pure `validate` method first.

**Open:** `lesson12.GuardedJokeAgent`  
**Debug:** `GuardedJokeAgentGuidedTest`

---

## Lesson 13 — Stuck recovery

**What:** Goal needs a `Dog`; handler seeds one and asks to `REPLAN`.  
**Why:** Processes can reach “no legal action” — recovery should be intentional.

**Top points**
1. Implement `StuckHandler` on the agent (or multicast handlers).
2. Seed missing blackboard facts carefully.
3. Prefer `REPLAN` when you fixed the world state.
4. Don’t use stuck handlers to hide incomplete modeling.
5. Break on `handleStuck` and the subsequent goal action.

**Open:** `lesson13.SelfUnstickingAgent`  
**Debug:** `SelfUnstickingAgentGuidedTest`

---

## Lesson 14 — Invocation & ProcessOptions

**What:** Call agents from services with verbosity options.  
**Why:** Real apps invoke agents from HTTP, jobs, and shell — not only demos.

**Top points**
1. `AgentInvocation` selects by goal return type.
2. `ProcessOptions` carries verbosity, budget, planner override, tool context.
3. Verbosity is inherited by subagents.
4. Use `withToolCallContext` for tenant/auth metadata.
5. Keep invocation at the edges; keep agents focused on domain.

**Open:** `lesson14.InvocationDemoService`  
**Debug:** breakpoint on `invocation.invoke` (needs platform / integration test for full path)

---

## Lesson 15 — Kotlin DSL agents

**What:** `agent { transformation...; goal(...) }` registered as a `@Bean`.  
**Why:** Compact Kotlin flows; still the same platform underneath.

**Top points**
1. DSL agents need explicit Spring registration.
2. Great for aggregate/transform pipelines.
3. Annotation model remains the Java default (and is fine in Kotlin).
4. Goals still declare satisfaction types.
5. Compare with Lesson 02 to see two styles solving similar problems.

**Open:** `lesson15.FactCheckerDsl`  
**Debug:** `FactCheckerDslGuidedTest`

---

## Suggested review circuit (60–90 minutes)

1. Skim [`TOP_10.md`](TOP_10.md)  
2. Skim [`CHEATSHEET.md`](CHEATSHEET.md)  
3. Debug Lesson 02 (both languages)  
4. Debug Lessons 04, 06, 12  
5. Read Lesson 10 summaries + planner annotations  
6. Skim [`ADVANCED_NUANCES.md`](ADVANCED_NUANCES.md)  
