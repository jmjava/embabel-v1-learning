# Looping Observatory Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Memorize eligibility, human pauses, and iteration:
pure conditions, `pre`/`post`, HITL, RepeatUntil, `@State`,
`clearBlackboard`, and typed subagent handoff.

**Audience:** Lessons 05, 06, 08, 09, 11.

**Learning method:** Method of loci. One locus = one concept.

**Video style:** First-person observatory of hourglasses. Every locus
is the next organ of the Hourglass-Sentinel. No lettering in stills.

**Narration style:** Mnemonic first, then the rule.

**Metaphor:** A night observatory where time is the planner’s loop.
The sentinel *is* waiting, scoring, and handing off.

**Hero:** A bronze hourglass-sentinel. Lantern-heart (pure condition)
→ gate rings → paused sand (HITL) → heavy glass (high cost) → side
mold then human crank → score-bell cycles → max-flip pegs → chambered
states → looping broom → child hourglass with a typed ticket.

**Hero origin:** A frozen hourglass on the observatory roof — sand
stuck, no organs yet.

**Metaphor material:** Night bronze, sand-glass, coal-orange starlight,
teal score-light on floor 2.

**Core rule:** One locus = one concept. Last organ grew this one.

------------------------------------------------------------------------

# 1. Building Overview

1. **Floor 1 — Eligibility and HITL** (five loci)
2. **Floor 2 — Iteration and composition** (five loci)

Named bindings stay in Foundry F1.5. `canRerun` lives on the Armory
knob panel.

------------------------------------------------------------------------

# 2. Floor 1 --- Eligibility And HITL

## Learning Objective

Conditions are cheap pure facts. HITL is an expensive pause that
returns a type, then the planner resumes. Try the model first.

**Metaphor region:** The Lantern Deck

**Hero stage:** Frozen glass grows gates and a human crank

**Metaphor entry:** Frozen hourglass

**Metaphor exit:** Soft clay plus human crank — climb to loops

## Scene 1 --- Lantern Heart: Pure @Condition

**Visual:** A lantern grows as the heart. It flickers true/false
without moving sand. Many moths bump it — it may be asked often.

**Overlay:** `@Condition`

**Narration:**

> `@Condition` methods are boolean world-state facts. Keep them
> side-effect free. The planner may evaluate them more than once.
> Purity is the contract.

**Recall cue:** Moth-bumped lantern → pure condition.

**Review question:** May condition methods have side effects?

**Review answer:** No — keep them pure; they may be called often

**Metaphor part:** head-slot

**Hero from:** A frozen hourglass on the roof.

**Hero now:** The same hourglass with a lantern-heart moths keep bumping.

**Hero silhouette:** Hourglass with a flickering lantern heart

**Hero tell:** Moths bump the lantern; sand still frozen

**Hero becomes:** Two gate-rings grow around the waist, only one open.

**Key distinction:** Conditions are facts, not actions.

------------------------------------------------------------------------

## Scene 2 --- Twin Gates: pre / post

**Visual:** Two rings around the waist. One ring must already be open
(`pre`). The other slams shut after passage (`post`). Sibling windows:
only one lights.

**Overlay:** `pre / post`

**Narration:**

> Named conditions in `pre`/`post` gate mutually exclusive siblings
> (direct vs one-stop). Reference condition *names*, not casual Java
> method names. Type presence alone is not a quality gate.

**Recall cue:** Twin waist-rings → pre/post.

**Review question:** What do `pre` and `post` reference?

**Review answer:** Condition names

**Metaphor part:** seeing-slot

**Hero from:** Hourglass with a lantern-heart.

**Hero now:** The same hourglass with two gate-rings at the waist.

**Hero silhouette:** Lantern-hourglass with two waist gate-rings

**Hero tell:** Sibling windows; only one ring open at a time

**Hero becomes:** Sand freezes until a human turns the crank.

**Key distinction:** `pre`/`post` are names, not method pointers by luck.

------------------------------------------------------------------------

## Scene 3 --- Paused Sand: WaitFor / fromForm

**Visual:** Sand freezes mid-air. A human-sized crank appears. Turning
it dumps a form-shaped ingot into the glass, then sand falls again.

**Overlay:** `WaitFor / fromForm`

**Narration:**

> HITL actions return form schema types. Java: `WaitFor.formSubmission`.
> Kotlin: `fromForm`. The process waits. After submission, Embabel
> replans with the new type. No graph edge to draw.

**Recall cue:** Frozen sand + crank → HITL wait.

**Review question:** What does a HITL action return?

**Review answer:** A form schema type, then the process resumes

**Metaphor part:** hearing-slot

**Hero from:** Hourglass with twin gate-rings.

**Hero now:** The same hourglass with sand frozen and a human crank.

**Hero silhouette:** Gated hourglass with a huge human crank

**Hero tell:** Sand mid-air until the crank turns; a form-ingot drops in

**Hero becomes:** The glass itself turns heavy and expensive.

**Key distinction:** Pause is a type post, not a special FSM node.

------------------------------------------------------------------------

## Scene 4 --- Heavy Glass: High Cost Fallback

**Visual:** The hourglass becomes lead-heavy. A cheap mold-path
glows beside it. The planner prefers the mold unless the mold fails.

**Overlay:** `cost` (HITL)

**Narration:**

> HITL `cost` should be high so it is a fallback, not the default
> plan. Cheap extraction first. Humans are the expensive organ.

**Recall cue:** Lead glass → high HITL cost.

**Review question:** Why is HITL cost high?

**Review answer:** So it is a fallback, not the happy path

**Metaphor part:** scent-slot

**Hero from:** Hourglass with a human crank.

**Hero now:** The same hourglass, now lead-heavy, beside a cheap mold-path.

**Hero silhouette:** Crank-hourglass of lead beside a cheap mold

**Hero tell:** Planner walks the mold-path; lead glass waits

**Hero becomes:** Soft clay tries the mold, then slumps toward the crank.

**Key distinction:** Cost is how you keep humans off the happy path.

------------------------------------------------------------------------

## Scene 5 --- Slump Then Crank: ifPossible + HITL

**Visual:** Soft clay tries a small mold, slumps through a null hole
(warning lantern, no explosion), and only then the heavy crank turns.

**Overlay:** `createObjectIfPossible + HITL`

**Narration:**

> Common pattern: try typed extraction with
> `createObjectIfPossible`. `null` warns and replans — often toward
> the high-cost HITL action. Incomplete prompts should not throw.

**Recall cue:** Slump then crank → try AI, else ask.

**Review question:** What should incomplete extraction do?

**Review answer:** Return null, replan, often to HITL

**Metaphor part:** passage-slot

**Hero from:** Lead hourglass beside a cheap mold.

**Hero now:** The same hourglass catching slumped clay, then offering
the crank.

**Hero silhouette:** Lead hourglass under a null-hole, crank ready

**Hero tell:** Warning lantern, no explosion; crank only after the slump

**Hero becomes:** Sand begins cycling past a score-bell.

### Floor 1 Recall Route

`Pure condition → pre/post → HITL wait → High cost → ifPossible+HITL`

------------------------------------------------------------------------

# 3. Floor 2 --- Iteration And Composition

## Learning Objective

Loops are honest: score thresholds, max iterations, `@State` chambers,
clear on the loop, typed child agents.

**Metaphor region:** The Cycle Dome — teal score-light

**Hero stage:** Hourglass learns to repeat and to hold a child

**Metaphor entry:** Climb from the slump-crank

**Metaphor exit:** Child hourglass with a typed ticket

## Scene 1 --- Score Bell: RepeatUntil

**Visual:** Sand cycles. A critic-lantern rings a score-bell. When
the tone is high enough, the dome opens. Feedback is tone + spoken
color, not a number painted on glass.

**Overlay:** `TextFeedback`

**Narration:**

> RepeatUntil needs `ActionContext`. The evaluator returns
> `TextFeedback(score, text)`. Stop at threshold. This is the
> standard “good enough” generation loop.

**Recall cue:** Score-bell → RepeatUntil feedback.

**Review question:** What does the RepeatUntil evaluator return?

**Review answer:** TextFeedback — score plus text

**Metaphor part:** vault-slot

**Hero from:** Hourglass catching slumped clay.

**Hero now:** The same hourglass cycling sand past a score-bell.

**Hero silhouette:** Slump-hourglass with a critic score-bell

**Hero tell:** Bell rings a tone; dome opens only when the tone is high

**Hero becomes:** Pegs cap how many flips are allowed.

**Key distinction:** Needs ActionContext, not bare `Ai`.

------------------------------------------------------------------------

## Scene 2 --- Flip Pegs: maxIterations

**Visual:** Wooden pegs count flips. When pegs run out, the best
tone-so-far is kept and the dome still opens — a safety cap.

**Overlay:** `maxIterations`

**Narration:**

> `withMaxIterations` bounds nondeterminism. If the threshold is
> never met, you still stop. Safety cap, not an invitation to
> infinite critic loops.

**Recall cue:** Pegs run out → maxIterations.

**Review question:** What if the score never hits the threshold?

**Review answer:** Stop at maxIterations (keep best so far)

**Metaphor part:** works-slot

**Hero from:** Hourglass with a score-bell.

**Hero now:** The same hourglass with flip-pegs counting cycles.

**Hero silhouette:** Score-bell hourglass with a row of flip-pegs

**Hero tell:** Last peg falls; dome opens anyway with the best tone

**Hero becomes:** The glass splits into chambers; only one set of
gears spins.

**Key distinction:** Caps are production, not pessimism.

------------------------------------------------------------------------

## Scene 3 --- Chambered Glass: @State

**Visual:** The hourglass splits into stacked chambers. Only the
current chamber’s gears spin. Prior chamber objects hide under
frosted glass. Non-state sand can still show.

**Overlay:** `@State`

**Narration:**

> `@State` types scope which actions are live. Returning a state
> type transitions the machine. Prior state objects hide. Non-state
> data can remain. Kotlin: avoid `inner` state classes.

**Recall cue:** One spinning chamber → @State scope.

**Review question:** What happens to the previous state object?

**Review answer:** It is hidden; non-state data may remain

**Metaphor part:** grasp-slot

**Hero from:** Hourglass with flip-pegs.

**Hero now:** The same hourglass split into stacked state-chambers.

**Hero silhouette:** Pegged hourglass of stacked chambers, one spinning

**Hero tell:** Frosted prior chamber; one gear-set spinning

**Hero becomes:** A broom appears only in the looping chamber.

**Key distinction:** States are honest loops; do not fight GOAP.

------------------------------------------------------------------------

## Scene 4 --- Loop Broom: clearBlackboard

**Visual:** A broom lives only in the looping chamber. The goal
chamber is unmarked by the broom. Sweeping the goal chamber would
erase the prize.

**Overlay:** `clearBlackboard`

**Narration:**

> `clearBlackboard = true` belongs on looping `@State` actions so
> the next iteration is clean. Do not clear on the final goal
> action. That is how prizes vanish.

**Recall cue:** Broom in the loop chamber only → clearBlackboard.

**Review question:** Should the goal action clear the blackboard?

**Review answer:** No — clear on loop actions, not the goal

**Metaphor part:** stand-slot

**Hero from:** Chambered hourglass.

**Hero now:** The same hourglass with a broom only in the looping
chamber.

**Hero silhouette:** Chambered hourglass holding a broom in one room

**Hero tell:** Goal chamber has no broom

**Hero becomes:** A child hourglass sits in the palm with a ticket
mold.

**Key distinction:** Clear is for iteration hygiene, not for done.

------------------------------------------------------------------------

## Scene 5 --- Child Glass: Subagent.consuming

**Visual:** A smaller hourglass in the palm. The parent must fill a
ticket-mold (`consuming(T)`) before the child will spin. The child
wears the parent’s cloak (verbosity/options).

**Overlay:** `Subagent.consuming(T)`

**Narration:**

> `Subagent.ofClass(...).consuming(T)` is the JSON the parent LLM
> must produce to invoke the child. Still typed end-to-end.
> Subagents inherit parent verbosity and process options. Compose
> specialists; do not hard-code a sequential Java call.

**Recall cue:** Palm child + ticket mold → consuming(T).

**Review question:** What does `consuming(T)` define?

**Review answer:** The typed payload the parent must fill to call the child

**Metaphor part:** exit-slot

**Hero from:** Chambered hourglass with a loop broom.

**Hero now:** The same hourglass holding a child hourglass and a
ticket-mold.

**Hero silhouette:** Broom-hourglass with a child glass in its palm

**Hero tell:** Ticket-mold in the parent’s other hand; child wears the parent cloak

**Hero becomes:** The finished observatory sentinel, ready for recall.

**Key distinction:** Handoff is typed tool-call, not a hidden method.

### Floor 2 Recall Route

`RepeatUntil score → maxIterations → @State hide → clearBlackboard → Subagent schema`

------------------------------------------------------------------------

# 4. Final Active-Recall Sequence

Lantern, gates, crank, lead, slump, bell, pegs, chambers, broom, child.

------------------------------------------------------------------------

# 5. Study Map

Lessons 05, 06, 08, 09, 11. Craig Walls HITL recipe.
Next palace: Safety Vault.
