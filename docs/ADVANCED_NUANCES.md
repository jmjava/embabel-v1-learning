# Advanced Embabel nuances (deep dive)

Companion to the lesson Javadocs. These are the sharp edges worth memorizing.

## Planning & blackboard

1. **Replanning is the default loop.** After every action, conditions and type availability are reassessed (OODA).
2. **Latest object of a type wins** unless you use `outputBinding` + `@RequireNameMatch`.
3. **Returning `null`** from an action (or `createObjectIfPossible`) can force an alternate plan path.
4. **`hide(object)`** removes something from consideration without deleting history semantics you may still need for logging.
5. **GOAP cost vs Utility value:** GOAP seeks a cheap path to a goal; Utility greedily picks high net value and may wander without a clear goal/termination.

## LLM calls

1. Prefer **typed** `createObject` / `creating(...).fromPrompt` over free text when structure matters.
2. **`withId`** names interactions for logs/observability — use it in teaching demos.
3. **Personas / PromptContributors** change system-style context without baking it into every prompt string.
4. Some models **reject custom temperature** — keep options conservative in reusable builders.
5. Tools are attached to a **PromptRunner instance**, not magically to the whole agent.

## Tools & subagents

1. Domain `@Tool` methods require `withToolObject`.
2. Framework `CoreToolGroups.WEB` etc. are capability bundles that may need runtime providers.
3. `ToolCallContext` is out-of-band (auth/tenant) — not part of the tool JSON schema.
4. `Subagent.consuming(T)` defines what the parent LLM must produce to invoke the child.
5. Subagents inherit parent verbosity/process options.

## States & HITL

1. `@State` scopes available actions; transitions hide prior state objects.
2. `clearBlackboard = true` is for loops — do not clear on the final goal action casually.
3. Kotlin: avoid `inner` state classes.
4. HITL `cost` should be high so it is a fallback, not the default plan.

## Safety & operations

1. Guardrail `CRITICAL` failures block LLM execution.
2. StuckHandlers should seed missing facts, not paper over incomplete domains.
3. `ProcessOptions` budgets/early termination matter for production.
4. Remote export (`@Export(remote=true)`) is how shell/MCP/A2A discover goals — pair with security annotations for MCP.

## Still thinner in this repo (see SPEC_COMPLETENESS)

RAG ToolishRag, MCP security, A2A, streaming/thinking, observability exporters, Agent Skills, ConcurrentAgentProcess.

Memorization hooks for those APIs (not lessons yet): Frontier Greenhouse in [`videos/memory-os/STUDY_CAMPUS.md`](videos/memory-os/STUDY_CAMPUS.md). Blogs: [`READING.md`](READING.md).
