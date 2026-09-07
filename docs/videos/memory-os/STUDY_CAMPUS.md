# Foundry City — memorize every key Embabel concept

Memory OS study campus for this repo. **Purpose:** put the useful
Embabel rules into long-term memory with method-of-loci hero walks.

Not a filmed cookbook. Not a slide deck. Each **category** is a
building. Each **subcategory** is a floor. Each **concept** is one
organ on one evolving hero.

Conventions: [`CONVENTIONS.md`](CONVENTIONS.md).  
Filmed already: [`embabel-cheatsheet.md`](embabel-cheatsheet.md)
(Type-Foundry).  
Authoring (not yet compiled): [`heroes/`](heroes/).  
Blogs that add to training: [`../../READING.md`](../../READING.md).

## How to study

1. Walk **Type-Foundry** (films or Markdown) until the 12 cheat-sheet
   rooms are instant.
2. Walk the other palaces in order A → F. Say `Hero from` → `Hero now`
   out loud before you read the rule.
3. Close the file. Recite the **recall route** for that floor.
4. Drill [`../../QUIZ_FLASHCARDS.md`](../../QUIZ_FLASHCARDS.md) and the
   review Q/A on each locus.
5. Only then read the mapped blogs.

One palace per sitting. Do not binge the campus.

## Campus map

```
                    [Frontier Greenhouse]     teal / future lessons
                              |
 [Prompt Atelier] ---- [Type-Foundry] ---- [Planning Citadel]
         |                    |                     |
 [DICE Workshop] ---- [Looping Observatory] ---- [Safety Vault]
                              |
                    [Annotation Armory]
```

Shared material: bronze + glass type-pipes; coal-orange on 1.0 floors;
teal on 1.5 / frontier. Heroes are siblings of the unlabeled type-ingot.

| # | Palace | Category | Hero | Loci | Status |
|---|--------|----------|------|------|--------|
| 0 | [Type-Foundry](embabel-cheatsheet.md) | Cheat-sheet core | Type-ingot → pipe-creature → mold-mouth | 12 | Filmed |
| A | [Planning Citadel](heroes/planning-citadel.md) | Planning core | Compass-Serpent | 10 | Authoring |
| B | [Annotation Armory](heroes/annotation-armory.md) | Agent definition | Badge-Beetle | 8 | Authoring |
| C | [DICE Workshop](heroes/dice-workshop.md) | Domain & tools | Customer-Golem | 7 | Authoring |
| D | [Prompt Atelier](heroes/prompt-atelier.md) | LLM extras beyond foundry floor 2 | Quill-Wisp | 6 | Authoring |
| E | [Looping Observatory](heroes/looping-observatory.md) | Conditions, HITL, loops, subagents | Hourglass-Sentinel | 10 | Authoring |
| F | [Safety Vault](heroes/safety-vault.md) | Safety, stuck, invoke, test | Gate-Warden | 10 | Authoring |
| G | [Frontier Greenhouse](heroes/frontier-greenhouse.md) | Next modules from blogs | Glass-Librarian | 8 | Authoring |

## Category / subcategory index

Every memorization-worthy rule in this curriculum (plus blog-frontier
APIs) lives in exactly one locus. Cheat-sheet rooms are marked ★
(already filmed).

### A. Planning core

#### A1. World model
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| Types are the wiring ★ | Foundry F1.1 Pipe Hall | 02–03, 16 |
| Four core objects (Action, Goal, Condition, Domain) | Citadel 1 | cheat sheet mental model |
| You do not write an FSM | Citadel 2 | cheat sheet |
| Return type posts an effect / type chaining | Citadel 3 | 03, 16 |
| Mix code + LLM ★ | Foundry F1.3 Split Workbench | 03 |

#### A2. The loop
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| Replan after every action / OODA ★ | Foundry F1.2 + Citadel 4 | 03 |
| `null` → replan ★ | Foundry F2.2 Null Pit | 18 |
| Stuck is a status, not a crash | Citadel 6 | 13 |
| `cost` heuristic when same I/O | Citadel 5 | 17 |

#### A3. Planner choice
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| Four hats snapshot ★ | Foundry F1.6 Hat Rack | 10 |
| GOAP = cheap typed path | Citadel 7 | 10 |
| Utility = greedy value; needs termination | Citadel 8 | 10 |
| Hybrid = gather then stop | Citadel 9 | 10 |
| Supervisor = LLM picks typed tools; needs `description` | Citadel 10 | 10 |

### B. Agent definition

#### B1. What an agent is
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| `@Agent(description=…)` used for selection | Armory 1 | 02 |
| `@EmbabelComponent` = action library, not an agent | Armory 2 | 10 |
| `@Action` params/return drive GOAP | Armory 3 | 02 |
| `@AchievesGoal` marks done, not “last method” | Armory 4 | 02 |

#### B2. Knobs and edges
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| `pre`/`post`, `canRerun`, `cost`/`value`, `description`, `clearBlackboard` | Armory 5 | 06, 09–11 |
| `@Export(remote=true)` shell / MCP / A2A | Armory 6 | 14 |
| Kotlin DSL `agent { }` as `@Bean` | Armory 7 | 15 |
| Java `Ai` vs Kotlin `OperationContext` | Armory 8 | 02, `JAVA_VS_KOTLIN` |

### C. Domain & DICE tools

#### C1. Domain-integrated context
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| Domain objects are the flow (DICE) | Workshop 1 | 04 + DICE blog |
| `@Tool` on the domain instance | Workshop 2 | 04 |
| Unannotated methods stay private | Workshop 3 | 04 |

#### C2. Attachment scope
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| Tools attach per PromptRunner ★ | Foundry F1.4 + Workshop 4 | 04, 07 |
| `withToolGroup` capability bundles | Workshop 5 | 07 |
| `@LlmTool` + `Tool.fromInstance` | Workshop 6 | 07 |
| `ToolCallContext` is out-of-band | Workshop 7 | 07, 14 |
| Inspectors log calls ★ | Foundry F2.6 | 21 |

### D. LLM boundary

#### D1. Typed create (filmed)
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| Injected `Ai` without an agent | Atelier 1 | 01 |
| `creating(T).fromPrompt` ★ | Foundry F2.1 Mold Mouth | 01 |
| `createObjectIfPossible` → `null` ★ | Foundry F2.2 | 18 |
| `fromMessages` / contributors ★ | Foundry F2.3 | 21 |
| `thinking()` ★ | Foundry F2.4 | 19 |
| `createObjectStream` ★ | Foundry F2.5 | 20 |

#### D2. Call-shape extras
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| `withId` names the interaction | Atelier 2 | nuances |
| Personas / PromptContributors | Atelier 3 | 02, 21 |
| Mix models by role | Atelier 4 | 02, VERSIONS |
| Bean Validation on result types | Atelier 5 | 01 |
| Conservative temperature; local models | Atelier 6 | 09, blogs |

### E. Conditions, humans, loops

#### E1. Eligibility and HITL
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| `@Condition` methods are pure | Observatory 1 | 06 |
| `pre`/`post` named gates | Observatory 2 | 06 |
| Named bindings ★ | Foundry F1.5 Named Crates | 06 |
| HITL `WaitFor` / `fromForm` | Observatory 3 | 05 |
| High `cost` so HITL is fallback | Observatory 4 | 05 |
| Try AI, else ask (ifPossible + HITL) | Observatory 5 | 05, 18 |

#### E2. Iteration and composition
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| RepeatUntil `TextFeedback` + threshold | Observatory 6 | 09 |
| `maxIterations` safety cap | Observatory 7 | 09 |
| `@State` scopes actions; hides prior state | Observatory 8 | 11 |
| `clearBlackboard` on loop, not goal | Observatory 9 | 11 |
| `Subagent.consuming(T)` | Observatory 10 | 08 |

`canRerun` lives on the Armory knob panel (B2), not here. Named bindings
stay in Foundry F1.5. Observatory is ten loci: five eligibility/HITL,
then five iteration/composition.

### F. Production envelope

#### F1. Safety
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| Guardrail `CRITICAL` blocks | Vault 1 | 12 |
| Input vs assistant rails | Vault 2 | 12 |
| StuckHandler seeds facts | Vault 3 | 13 |
| `REPLAN` vs `NO_RESOLUTION` | Vault 4 | 13 |
| `hide()` vs delete | Vault 5 | nuances |

#### F2. Invoke, export, prove
| Concept | Palace / locus | Lesson |
|---------|----------------|--------|
| `AgentInvocation` by goal type | Vault 6 | 14 |
| `ProcessOptions` (verbosity, budget, tool context) | Vault 7 | 14 |
| `@Export` + secure MCP (pair later) | Vault 8 | 14 |
| `FakeOperationContext` — test prompts, not poetry | Vault 9 | TOP_10 #9 |
| Debug first stops | Vault 10 | BREAKPOINTS |

### G. Frontier (blogs / SPEC_COMPLETENESS)

| Concept | Palace / locus | Source |
|---------|----------------|--------|
| ToolishRag — model decides retrieval | Greenhouse 1 | RAG blogs |
| Skills (`SKILL.md`) lazy load | Greenhouse 2 | 1.0 features blog |
| BYOK | Greenhouse 3 | 1.0 features |
| Chat-store persistence | Greenhouse 4 | 1.0 features |
| `UnfoldingTool` | Greenhouse 5 | 1.0 features |
| Parallel tool loop | Greenhouse 6 | 1.0 features |
| `@SecureAgentTool` | Greenhouse 7 | 1.0 features |
| ONNX embeddings / token budget rails | Greenhouse 8 | 1.0 features |

## Recall routes (recite without notes)

**Foundry F1** `Types → Replan → Code+LLM → Per-call tools → Named bindings → Planner hats`  
**Foundry F2** `creating → ifPossible → fromMessages → thinking → stream → inspectors`  
**Citadel F1** `Four objects → No FSM → Type posts effect → OODA → cost → Stuck status`  
**Citadel F2** `GOAP → Utility → Hybrid → Supervisor`  
**Armory** `Agent → Component → Action → Goal → Knobs → Export → DSL → Ai vs Context`  
**Workshop** `DICE → @Tool → Private → Per-call → Groups → @LlmTool → ToolCallContext`  
**Atelier** `Injected Ai → withId → Personas → Mix models → Validation → Local/temp`  
**Observatory F1** `Pure condition → pre/post → HITL wait → High cost → ifPossible+HITL`  
**Observatory F2** `RepeatUntil score → maxIterations → @State hide → clearBlackboard → Subagent schema`  
**Vault F1** `CRITICAL → Rail kinds → Seed facts → REPLAN → hide()`  
**Vault F2** `Invocation → ProcessOptions → Export → Fakes → Breakpoints`  
**Greenhouse** `ToolishRag → Skills → BYOK → Chat store → Unfold → Parallel → Secure MCP → ONNX`

## Memory OS later

Each `heroes/*.md` is compile-shaped Markdown (same headers as
`embabel-cheatsheet.md`). Do not vendor memory-os. When ready:

```bash
memoryos compile docs/videos/memory-os/heroes/planning-citadel.md \
  --id embabel-planning-citadel \
  -o docs/videos/memory-os/embabel-planning-citadel.palace.yaml
```

Ingest stubs live in [`ingest-manifest.json`](ingest-manifest.json)
under `campus_palaces`.
