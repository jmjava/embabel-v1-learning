# Top 10 things to internalize

If you only memorize ten ideas from this curriculum, make them these.

## 1. Types are the wiring

GOAP does not run your methods top-to-bottom. It builds a path from **blackboard types** (and conditions) to a goal type. Design domain objects carefully — they *are* the control flow.

## 2. Replanning is the loop

After every action, Embabel reassesses the world (OODA). Plans are not fixed scripts. Returning new objects, `null`, or flipping conditions changes the next step.

## 3. Mix code agency with LLM agency

Not every `@Action` needs an LLM. Deterministic Spring services inside actions are a feature — cheaper, testable, safer.

## 4. Tools are opt-in per call

`@Tool` / `@LlmTool` methods exist, but the model only sees what that PromptRunner attaches (`withToolObject`, `withTool`, `withToolGroup`). Scope tools narrowly.

## 5. Prefer typed LLM outputs

`createObject` / `creating(...).fromPrompt` beat free-text parsing. Use `createObjectIfPossible` when failure should trigger an alternate plan (often HITL).

## 6. Name collisions need bindings

Multiple values of the same type → `outputBinding` + `@RequireNameMatch`. Otherwise “latest wins” will surprise you.

## 7. Planner choice is a product decision

- **GOAP** — deterministic business paths  
- **Utility / Hybrid** — opportunistic gathering  
- **Supervisor** — flexible, LLM-orchestrated, less deterministic  

Same `@Action` style; very different runtime semantics.

## 8. States unlock honest loops

Pure GOAP is awkward for “keep refining until good.” `@State` + `clearBlackboard` (and RepeatUntil builders) are the intentional looping tools.

## 9. Test prompts and structure, not model poetry

Unit-test with `FakeOperationContext`: assert prompt contents, temperature, tool attachment, condition predicates. Save live LLM judgment for demos/integration.

## 10. Export and invoke explicitly

Shell/MCP/A2A discover goals via `@Export`. Application code should call `AgentInvocation` + `ProcessOptions` (verbosity, budgets, tool-call context) — don’t hide agents as undocumented side effects.

---

### Honorary #11–15 (almost made the cut)

11. HITL `cost` should be high so it’s a fallback  
12. Condition methods must be side-effect free  
13. Subagent `consuming(T)` is the schema the parent LLM fills  
14. Guardrail `CRITICAL` blocks execution  
15. Kotlin DSL is great for compact flows; annotations still win for most Java teams  

Study path: [`WALKTHROUGH.md`](WALKTHROUGH.md) → lesson code → [`CHEATSHEET.md`](CHEATSHEET.md).  
Memorize with palaces: [`videos/memory-os/STUDY_CAMPUS.md`](videos/memory-os/STUDY_CAMPUS.md).  
Blogs: [`READING.md`](READING.md).
