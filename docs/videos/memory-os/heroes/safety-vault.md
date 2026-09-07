# Safety Vault Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Memorize production envelope rules: guardrails, stuck
recovery, `hide()`, invocation, ProcessOptions, export, tests, and
debugger stops.

**Audience:** Lessons 12–14, TOP_10 #9, BREAKPOINTS, nuances.

**Learning method:** Method of loci. One locus = one concept.

**Video style:** First-person vault. Every locus is the next organ of
the walking palace-gate. No lettering in stills.

**Narration style:** Mnemonic first, then the rule.

**Metaphor:** The city gate. The warden *is* the safety and the
doorbell.

**Hero:** An iron palace-gate that learned to walk. Weld-bars → two
rail-grates → seed pouch → replan compass vs sealed tomb → hide-cloak
→ gift-shaped doorbell → options cloak → export key → skeletal
mirror → five-notch spyglass.

**Chassis:** an iron palace-gate that learned to walk

**Hero origin:** An open iron gate with no lock.

**Camera:** first-person, eye-level, wide-angle, standing just inside the threshold, looking at the next organ of the same hero

**Hero scale:** the hero is architectural — it fills at least 40% of the frame and is the first thing the eye hits

**Metaphor material:** Cold bronze bars, warning-orange welds, a little
teal on the invoke floor.

**Core rule:** One locus = one concept. Last organ grew this one.

------------------------------------------------------------------------

# 1. Building Overview

1. **Floor 1 — Safety** (five loci)
2. **Floor 2 — Invoke, export, prove** (five loci)

------------------------------------------------------------------------

# 2. Floor 1 --- Safety

## Learning Objective

Block dangerous calls. Recover stuck worlds by seeding facts.
`hide()` is not delete. Do not use recovery to paper over a bad
domain.

**Metaphor region:** The Weld Gate

**Hero stage:** Open gate grows bars and a seed pouch

**Metaphor entry:** Unlocked gate

**Metaphor exit:** Cloaked object — climb to the doorbell

## Scene 1 --- Weld Bars: CRITICAL

**Visual:** Bars weld across the gate when a forbidden topic
approaches. Lower sparks only glow in a log-bowl. The mouth behind
the gate never speaks.

**Overlay:** `CRITICAL`

**Narration:**

> Guardrail `CRITICAL` failures block LLM execution. Lower severities
> log. Attach with `withGuardRails` on the PromptRunner. Unit-test
> `validate` first.

**Recall cue:** Welded bars → CRITICAL blocks.

**Review question:** What does CRITICAL severity do?

**Review answer:** Blocks the LLM call

**Metaphor part:** head-slot

**Hero from:** An open iron gate with no lock.

**Hero now:** The same gate-warden with weld-bars fused shut across the mouth.

**Hero silhouette:** Gate-figure with glowing weld-bars across the mouth.

**Hero tell:** Bars fully welded; a log-bowl catching lesser sparks.

**Hero shot:** Weld-bars filling the mouth of the gate.

**Hero becomes:** The same gate-warden with an inbound grate and an outbound grate.


**Key distinction:** Severity is the difference between log and halt.

------------------------------------------------------------------------

## Scene 2 --- Twin Rails: Input vs Assistant

**Visual:** An inbound grate checks visitors. An outbound grate
checks what the mouth says. Same warden, two inspections.

**Overlay:** `UserInputGuardRail`

**Narration:**

> `UserInputGuardRail` validates before the call.
> `AssistantMessageGuardRail` analyses the response. Keep policy out
> of action bodies. Rails can inspect the blackboard.

**Recall cue:** Two grates → input vs assistant rails.

**Review question:** When does a UserInputGuardRail run?

**Review answer:** Before the LLM call

**Metaphor part:** seeing-slot

**Hero from:** The same gate-warden with weld-bars fused shut across the mouth.

**Hero now:** The same gate-warden with an inbound grate and an outbound grate.

**Hero silhouette:** Barred warden with two separate grates.

**Hero tell:** Visitors checked on the way in; speech checked on the way out.

**Hero shot:** Two grates, inbound and outbound.

**Hero becomes:** The same gate-warden pouring a fact-seed from a pouch.


**Key distinction:** Two directions, two interfaces.

------------------------------------------------------------------------

## Scene 3 --- Seed Pouch: StuckHandler

**Visual:** When the brick-tail from the citadel appears, the warden
pours a missing fact-seed (a small bronze dog) onto the floor. The
domain heart does not get rewritten.

**Overlay:** `StuckHandler`

**Narration:**

> Implement `StuckHandler` on the agent (or multicast). Seed missing
> blackboard facts carefully. Prefer `REPLAN` when you fixed the
> world. Do not use handlers to hide incomplete modeling.

**Recall cue:** Seed pouch → StuckHandler seeds facts.

**Review question:** What should a StuckHandler do?

**Review answer:** Seed missing facts, then usually REPLAN

**Metaphor part:** hearing-slot

**Hero from:** The same gate-warden with an inbound grate and an outbound grate.

**Hero now:** The same gate-warden pouring a fact-seed from a pouch.

**Hero silhouette:** Twin-grate warden with a seed pouch.

**Hero tell:** A small bronze dog-seed on the floor; the domain heart untouched.

**Hero shot:** Seed pouch pouring a small bronze dog.

**Hero becomes:** The same gate-warden holding a replan-compass in one hand and a sealed tomb in the other.


**Key distinction:** Recovery ≠ remodeling.

------------------------------------------------------------------------

## Scene 4 --- Compass Or Tomb: REPLAN vs NO_RESOLUTION

**Visual:** Two hands: a spinning replan-compass, and a sealed tomb
when nothing can be done. The warden chooses.

**Overlay:** `REPLAN`

**Narration:**

> After seeding, prefer `REPLAN`. `NO_RESOLUTION` is the honest tomb
> when the world cannot be fixed. Break on `handleStuck` and the
> subsequent goal action.

**Recall cue:** Compass vs tomb → REPLAN vs NO_RESOLUTION.

**Review question:** What do you return after fixing world state?

**Review answer:** REPLAN

**Metaphor part:** scent-slot

**Hero from:** The same gate-warden pouring a fact-seed from a pouch.

**Hero now:** The same gate-warden holding a replan-compass in one hand and a sealed tomb in the other.

**Hero silhouette:** Seed-warden with a compass in one hand and a tomb in the other.

**Hero tell:** Two distinct objects, one per hand.

**Hero shot:** Compass in one hand, tomb in the other.

**Hero becomes:** The same gate-warden cloaking an ingot while a history-chest stays full.


**Key distinction:** Two outcomes; do not collapse them.

------------------------------------------------------------------------

## Scene 5 --- Hide Cloak: hide()

**Visual:** A cloak covers an ingot. The ingot is still in the
history-chest behind the gate. It is only gone from consideration.

**Overlay:** `hide()`

**Narration:**

> `hide(object)` removes something from planner consideration
> without deleting history you may need for logs. Not the same as
> wiping the blackboard.

**Recall cue:** Cloak, not fire → hide().

**Review question:** Does hide() delete history?

**Review answer:** No — it hides from consideration

**Metaphor part:** passage-slot

**Hero from:** The same gate-warden holding a replan-compass in one hand and a sealed tomb in the other.

**Hero now:** The same gate-warden cloaking an ingot while a history-chest stays full.

**Hero silhouette:** Compass-warden draping a cloak over an ingot.

**Hero tell:** History-chest visible behind the cloak.

**Hero shot:** Cloak over an ingot; chest still full.

**Hero becomes:** The same gate-warden whose doorbell is cut in the shape of the goal gift.


### Floor 1 Recall Route

`CRITICAL → Rail kinds → Seed facts → REPLAN → hide()`

------------------------------------------------------------------------

# 3. Floor 2 --- Invoke, Export, Prove

## Learning Objective

Call agents on purpose. Carry options. Export deliberately. Test
structure with fakes. Know the first debugger stops.

**Metaphor region:** The Doorbell Court

**Hero stage:** Warden becomes the app edge

**Metaphor entry:** From the hide-cloak

**Metaphor exit:** Spyglass for the debugger

## Scene 1 --- Gift Doorbell: AgentInvocation

**Visual:** The doorbell is cut in the shape of the goal object. Only
a matching gift rings the warden. Selection by return type.

**Overlay:** `AgentInvocation`

**Narration:**

> `AgentInvocation` selects by goal return type. Build from the
> platform, pass input, get the typed goal. Keep invocation at the
> edges. Agents stay domain-focused.

**Recall cue:** Shaped doorbell → invoke by goal type.

**Review question:** How does AgentInvocation usually select an agent?

**Review answer:** By goal return type

**Metaphor part:** vault-slot

**Hero from:** The same gate-warden cloaking an ingot while a history-chest stays full.

**Hero now:** The same gate-warden whose doorbell is cut in the shape of the goal gift.

**Hero silhouette:** Cloaked warden with a goal-shaped doorbell.

**Hero tell:** Only a matching-shaped gift rings it.

**Hero shot:** Doorbell cut as a gift-shape.

**Hero becomes:** The same gate-warden wearing an options cloak with three clasps.


**Key distinction:** You call the goal type, not a string name by habit.

------------------------------------------------------------------------

## Scene 2 --- Options Cloak: ProcessOptions

**Visual:** A cloak with three clasps: an eye (verbosity/show prompts),
a coin-meter (budget / early stop), a hidden envelope already seen in
the workshop (tool-call context). Child wardens wear the same cloak.

**Overlay:** `ProcessOptions`

**Narration:**

> `ProcessOptions` carries verbosity, budgets, planner override, and
> tool-call context. Subagents inherit verbosity. Budgets matter in
> production. Early termination is a feature.

**Recall cue:** Three clasps → ProcessOptions.

**Review question:** What do subagents inherit?

**Review answer:** Parent verbosity / process options

**Metaphor part:** works-slot

**Hero from:** The same gate-warden whose doorbell is cut in the shape of the goal gift.

**Hero now:** The same gate-warden wearing an options cloak with three clasps.

**Hero silhouette:** Doorbell-warden in a three-clasp cloak.

**Hero tell:** A child warden in the same cloak standing behind.

**Hero shot:** Three clasps on the cloak, child warden behind.

**Hero becomes:** The same gate-warden with an export keyhole in the vault door.


**Key distinction:** Options are the production envelope.

------------------------------------------------------------------------

## Scene 3 --- Export Key: @Export again at the edge

**Visual:** The armory keyhole reappears in the vault door. Shell,
remote-protocol, and handshake keys. A lock-disc waits for later
security in the greenhouse.

**Overlay:** `@Export`

**Narration:**

> Remote export is how edges discover goals. Do not hide agents as
> undocumented side effects. Pair with `@SecureAgentTool` when the
> door is MCP. That lock-disc is the Greenhouse.

**Recall cue:** Vault keyhole → export at the edge.

**Review question:** Should app code hide agents as side effects?

**Review answer:** No — invoke and export explicitly

**Metaphor part:** grasp-slot

**Hero from:** The same gate-warden wearing an options cloak with three clasps.

**Hero now:** The same gate-warden with an export keyhole in the vault door.

**Hero silhouette:** Cloaked warden beside a keyhole door and a lock-disc.

**Hero tell:** Lock-disc present but unused — later palace.

**Hero shot:** Keyhole in the vault door with an unused lock-disc.

**Hero becomes:** The same gate-warden holding a skeletal prompt-mirror.


**Key distinction:** Discovery and invocation are both explicit.

------------------------------------------------------------------------

## Scene 4 --- Fake Mirror: FakeOperationContext

**Visual:** A mirror shows prompt bones, temperature, and which
wrenches were clamped — never the model’s poem. Tests point at the
mirror.

**Overlay:** `FakeOperationContext`

**Narration:**

> Unit-test with `FakeOperationContext`. Assert prompt contents,
> temperature, tool attachment, condition predicates. Save live LLM
> judgment for demos. Test structure, not poetry.

**Recall cue:** Structure mirror → fakes.

**Review question:** What should unit tests assert about LLM calls?

**Review answer:** Prompts, options, tools — not model prose

**Metaphor part:** stand-slot

**Hero from:** The same gate-warden with an export keyhole in the vault door.

**Hero now:** The same gate-warden holding a skeletal prompt-mirror.

**Hero silhouette:** Keyhole-warden with a skeletal prompt-mirror.

**Hero tell:** Mirror shows bones, not verses.

**Hero shot:** Skeletal prompt-mirror close-up.

**Hero becomes:** The same gate-warden aiming a five-notch spyglass.


**Key distinction:** Fakes make the curriculum offline-first.

------------------------------------------------------------------------

## Scene 5 --- Spyglass: Debug First Stops

**Visual:** A spyglass with five notches: action entry, mold-line,
return-post, condition-planner, goal bell. The guided test is the
path.

**Overlay:** `@DebugGuide`

**Narration:**

> First stops: `@Action` entry, `createObject`/`fromPrompt`, action
> `return`, `@Condition` / planner choice, `@AchievesGoal`. Debug
> `*GuidedTest`. `@DebugGuide` lists them.

**Recall cue:** Five-notch spyglass → debug stops.

**Review question:** Name the five first debugger stops.

**Review answer:** Action entry, create line, return, condition/planner, goal

**Metaphor part:** exit-slot

**Hero from:** The same gate-warden holding a skeletal prompt-mirror.

**Hero now:** The same gate-warden aiming a five-notch spyglass.

**Hero silhouette:** Mirror-warden with a five-notch spyglass.

**Hero tell:** Five distinct notches on the spyglass.

**Hero shot:** Five-notch spyglass aimed at the walk.

**Hero becomes:** The finished iron palace-gate that learned to walk, ready for recall.


**Key distinction:** Tests are the guided doors, not live shells.

### Floor 2 Recall Route

`Invocation → ProcessOptions → Export → Fakes → Breakpoints`

------------------------------------------------------------------------

# 4. Final Active-Recall Sequence

End the video with locations only. Ask the learner to name the
concept, then reveal:

### Floor 1

**Safety**

`CRITICAL → Rail kinds → Seed facts → REPLAN → hide()`

### Floor 2

**Invoke, export, prove**

`Invocation → ProcessOptions → Export → Fakes → Breakpoints`


# 5. Study Map

Lessons 12–14. `BREAKPOINTS.md`. TOP_10 #9–10.
Next palace: Frontier Greenhouse (blogs / not yet lessons).
