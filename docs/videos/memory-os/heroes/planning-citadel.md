# Planning Citadel Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Memorize Embabel planning rules that the Type-Foundry only
sketches: core objects, inferred plans, type effects, OODA, cost, stuck
status, and the four planners in depth.

**Audience:** JVM engineers who already walked the cheat-sheet foundry.

**Learning method:** Method of loci. One locus = one concept. One hero
metamorphosis per room.

**Video style:** First-person walk through one bronze citadel. Every
locus is the next organ of the Compass-Serpent. No lettering in stills.

**Narration style:** Calm technical instructor. Mnemonic first, then
the rule. Pause for recall.

**Metaphor:** A citadel grown from the compass that crawled off the
type-ingot. The serpent *is* the plan.

**Hero:** A bronze compass-serpent. Four organs become its body, then
pulsing scale-bands, a type-pipe belly, a cost rattle, a brick-wall
tail, then four planner hoods that fuse one at a time.

**Chassis:** a bronze compass-serpent the size of a citadel aisle

**Hero origin:** A spinning bronze compass that crawled off the type-ingot.

**Camera:** first-person, eye-level, wide-angle, standing just inside the threshold, looking at the next organ of the same hero

**Hero scale:** the hero is architectural — it fills at least 40% of the frame and is the first thing the eye hits

**Metaphor material:** Warm bronze coils, glass type-pipes, coal-orange
arrow-light.

**Core rule:** One locus = one concept. The last organ grew this one.

------------------------------------------------------------------------

# 1. Building Overview

1. **Floor 1 — How a process lives:** objects, no FSM, type effects,
   OODA, cost, stuck status.
2. **Floor 2 — Four planners:** GOAP, Utility, Hybrid, Supervisor.

Teaching source: `docs/CHEATSHEET.md` planners + mental model;
lessons 03, 10, 13, 16, 17.

------------------------------------------------------------------------

# 2. Floor 1 --- How A Process Lives

## Learning Objective

Remember that Embabel infers a plan from typed world state. You do not
draw the graph. Cost and stuck are planner facts, not crashes.

**Metaphor region:** The Coil — bronze rings around a compass heart

**Hero stage:** Compass becomes a four-organ serpent

**Metaphor entry:** The foundry compass slithers in

**Metaphor exit:** Four hoods begin to bud for the climb

## Scene 1 --- Four Organs: Core Objects

**Visual:** The compass sprouts four body parts: action-legs, a
goal-bell tail-ring, lantern-ribs (conditions), a domain-heart. No
labels.

**Overlay:** `Actions Goals Conditions Domain`

**Narration:**

> Guide core objects: Actions are steps, Goals are what done is,
> Conditions are reassessed after every action, Domain types are the
> flow. Memorize the four organs. Everything else hangs off them.

**Recall cue:** Four organs → four core objects.

**Review question:** What are the four core objects?

**Review answer:** Actions, Goals, Conditions, Domain

**Metaphor part:** head-slot

**Hero from:** A spinning bronze compass that crawled off the type-ingot.

**Hero now:** A compass-serpent with action-legs, a goal-bell, lantern-ribs, and a domain-heart.

**Hero silhouette:** Compass-headed serpent with four distinct organs.

**Hero tell:** Four different organs, not a plain snake.

**Hero shot:** From above, the four organs fill the frame.

**Hero becomes:** The same compass-serpent whose coils are an undrawn maze with no arrows.


**Key distinction:** Domain is not decoration — types are control flow.

------------------------------------------------------------------------

## Scene 2 --- Undrawn Maze: No FSM

**Visual:** The serpent's coils *are* a maze with no arrows painted.
New legs appear and the path re-weaves. A discarded flowchart slate
lies cracked.

**Overlay:** `inferred plan`

**Narration:**

> Plans are inferred and rebuilt. You do not write an FSM. Adding an
> action should not require rewiring edges. If you are drawing the
> graph, you are fighting the planner.

**Recall cue:** Maze with no arrows → inferred plan.

**Review question:** Do you hand-wire action order?

**Review answer:** No — inferred plan, not an FSM

**Metaphor part:** seeing-slot

**Hero from:** A compass-serpent with action-legs, a goal-bell, lantern-ribs, and a domain-heart.

**Hero now:** The same compass-serpent whose coils are an undrawn maze with no arrows.

**Hero silhouette:** Serpent coiled as a maze with no painted arrows.

**Hero tell:** Cracked flowchart slate on the floor; coils have no arrows.

**Hero shot:** Wide first-person into the coiled maze, no arrows.

**Hero becomes:** The same compass-serpent with a glass-pipe belly that lights when a new type is born.


**Key distinction:** Source order is not execution order.

------------------------------------------------------------------------

## Scene 3 --- Pipe Belly: Return Posts An Effect

**Visual:** The maze-belly becomes glass pipes. When the serpent
births a new shaped ingot, the next pipe lights. No painted branch
signs.

**Overlay:** `return type`

**Narration:**

> Returning a new type posts an effect onto the blackboard. Classifier
> return types pick the next action. There is no `if` in the planner.
> Design the records; they are the wiring.

**Recall cue:** Lit pipe → return type posts an effect.

**Review question:** What does returning a new type do?

**Review answer:** Posts an effect — type chaining

**Metaphor part:** hearing-slot

**Hero from:** The same compass-serpent whose coils are an undrawn maze with no arrows.

**Hero now:** The same compass-serpent with a glass-pipe belly that lights when a new type is born.

**Hero silhouette:** Maze-serpent with a glowing glass-pipe belly.

**Hero tell:** A new ingot appears and the next pipe lights.

**Hero shot:** Low angle on the belly pipes as one lights.

**Hero becomes:** The same compass-serpent wearing four pulsing compass scale-bands.


**Key distinction:** Types, not method order, chain the plan.

------------------------------------------------------------------------

## Scene 4 --- Quadrant Scales: OODA

**Visual:** Four scale-bands around the compass pulse in order after
every door. The pipe-belly stays. No letters on the bands.

**Overlay:** `OODA / REPLAN`

**Narration:**

> After every action Embabel reassesses the world. Observe the
> blackboard, Orient with conditions, Decide a plan, Act. New objects,
> `null`, or flipped conditions change the next step. Not a fixed
> script.

**Recall cue:** Four pulsing bands → OODA replan.

**Review question:** What happens after every action?

**Review answer:** Replan — OODA

**Metaphor part:** scent-slot

**Hero from:** The same compass-serpent with a glass-pipe belly that lights when a new type is born.

**Hero now:** The same compass-serpent wearing four pulsing compass scale-bands.

**Hero silhouette:** Pipe-belly serpent wearing four compass scale-bands.

**Hero tell:** Bands pulse only after a door, then the coils re-weave.

**Hero shot:** Close on the compass head as four bands pulse.

**Hero becomes:** The same compass-serpent with a two-ticket cost-scale rattle on the tail.


**Key distinction:** Replan is the default loop, not an error path.

------------------------------------------------------------------------

## Scene 5 --- Cost Rattle: Same Shape, Cheaper Wins

**Visual:** Two identical tickets hang from a tail-rattle scale. The
light ticket slams down; the heavy ticket floats. Same goal-bell
rings for both.

**Overlay:** `@Action cost`

**Narration:**

> When two actions share the same input and output shape, GOAP uses
> `cost` as a heuristic. Lower cost wins. Cost is not an invoice. Use
> it for preferred defaults and expensive fallbacks — HITL should be
> heavy.

**Recall cue:** Light ticket slams → cheaper cost wins.

**Review question:** What happens when two actions have the same I/O?

**Review answer:** Lower cost wins

**Metaphor part:** passage-slot

**Hero from:** The same compass-serpent wearing four pulsing compass scale-bands.

**Hero now:** The same compass-serpent with a two-ticket cost-scale rattle on the tail.

**Hero silhouette:** Scale-banded serpent with a hanging two-ticket rattle.

**Hero tell:** Identical tickets; only the light one slams the scale.

**Hero shot:** Side shot of the tail rattle slamming the light ticket.

**Hero becomes:** The same compass-serpent whose tail is pressed to a brick wall.


**Key distinction:** Cost is a planner heuristic, not billing.

------------------------------------------------------------------------

## Scene 6 --- Brick Tail: Stuck Is A Status

**Visual:** The rattle-tail meets a brick wall. The serpent is not
dead. A leftover ingot sits on the floor with no matching pipe.
History clips hang from the lantern-ribs.

**Overlay:** `STUCK`

**Narration:**

> Stuck is a status, not a crash. A leftover type with no matching
> action stops the process. Dump blackboard and history. Recovery is
> a StuckHandler — next palace — not a reason to fake the domain.

**Recall cue:** Brick wall → STUCK status.

**Review question:** Is stuck a crash?

**Review answer:** No — STUCK is a status

**Metaphor part:** vault-slot

**Hero from:** The same compass-serpent with a two-ticket cost-scale rattle on the tail.

**Hero now:** The same compass-serpent whose tail is pressed to a brick wall.

**Hero silhouette:** Cost-rattle serpent stopped at a brick wall.

**Hero tell:** Leftover ingot on the floor; the serpent is still breathing.

**Hero shot:** The tail pressed to brick; leftover ingot in the foreground.

**Hero becomes:** The same compass-serpent wearing a fused cheap-path work-cap.


### Floor 1 Recall Route

`Four objects → No FSM → Type posts effect → OODA → cost → Stuck status`

------------------------------------------------------------------------

# 3. Floor 2 --- Four Planners

## Learning Objective

Same `@Action` style; four runtimes. Planner choice is a product
decision.

**Metaphor region:** The Hood Gallery — four hoods fuse in turn

**Hero stage:** Brick-tail serpent grows planner hoods

**Metaphor entry:** We climb from the wall into the hood gallery

**Metaphor exit:** Concierge mouth speaking typed tools

## Scene 1 --- Path Cap: GOAP

**Visual:** A cheap work-cap fuses to the compass. The serpent walks
the shortest lit-pipe path to the goal-bell. Side-quest gold is
ignored.

**Overlay:** `GOAP`

**Narration:**

> GOAP is the default. It takes the cheap typed path to a goal.
> Business workflows belong here. It will skip opportunistic side
> quests. That is a feature.

**Recall cue:** Work-cap → GOAP cheap path.

**Review question:** How does GOAP pick actions?

**Review answer:** Cheap typed path to the goal

**Metaphor part:** works-slot

**Hero from:** The same compass-serpent whose tail is pressed to a brick wall.

**Hero now:** The same compass-serpent wearing a fused cheap-path work-cap.

**Hero silhouette:** Brick-tail serpent with a fused work-cap.

**Hero tell:** Walks only the lit cheap pipes; gold piles ignored.

**Hero shot:** Front view of the fused work-cap walking a lit pipe.

**Hero becomes:** The same compass-serpent with greedy gold-crown claws grabbing value piles.


**Key distinction:** GOAP optimizes path cost, not exploration.

------------------------------------------------------------------------

## Scene 2 --- Gold Claws: Utility

**Visual:** A greedy gold crown grows claws that grab the brightest
value pile, then the next, and may never reach the goal-bell.

**Overlay:** `UTILITY`

**Narration:**

> Utility picks highest net `value`. Good for triage and exploration.
> Design termination or it wanders. `@AchievesGoal` and policies are
> not optional here.

**Recall cue:** Gold claws → Utility greedy value.

**Review question:** What is the Utility risk?

**Review answer:** May not terminate cleanly

**Metaphor part:** grasp-slot

**Hero from:** The same compass-serpent wearing a fused cheap-path work-cap.

**Hero now:** The same compass-serpent with greedy gold-crown claws grabbing value piles.

**Hero silhouette:** Capped serpent with greedy gold claws.

**Hero tell:** Claws full of gold; the goal-bell still distant.

**Hero shot:** Claws grabbing piles, goal-bell far behind.

**Hero becomes:** The same compass-serpent wearing a satchel-hood that zips shut at the goal-bell.


**Key distinction:** Value is not the same knob as GOAP cost.

------------------------------------------------------------------------

## Scene 3 --- Zip Satchel: Hybrid

**Visual:** A satchel-hood fills with gathered piles, then a zipper
slams when the goal-bell is reachable.

**Overlay:** `HYBRID`

**Narration:**

> Hybrid gathers like Utility, then stops on a real goal. You still
> need a real goal type. Gather-then-synthesize is the story.

**Recall cue:** Zipper slam → Hybrid gather then stop.

**Review question:** What does Hybrid add to Utility?

**Review answer:** Gather, then stop on a real goal

**Metaphor part:** stand-slot

**Hero from:** The same compass-serpent with greedy gold-crown claws grabbing value piles.

**Hero now:** The same compass-serpent wearing a satchel-hood that zips shut at the goal-bell.

**Hero silhouette:** Gold-clawed serpent with a zipping satchel-hood.

**Hero tell:** Satchel fills, then the zipper slams at the goal-bell.

**Hero shot:** Satchel zipper slamming as the bell rings.

**Hero becomes:** The same compass-serpent with a concierge top-hat mouth and a description megaphone.


**Key distinction:** Hybrid still needs a real goal, not endless gather.

------------------------------------------------------------------------

## Scene 4 --- Concierge Mouth: Supervisor

**Visual:** A concierge top-hat becomes the mouth. It points at
typed tool-doors. A megaphone grows because mumbled door names
confuse it. Same legs as always.

**Overlay:** `SUPERVISOR`

**Narration:**

> Supervisor lets the LLM pick typed actions. Flexible, less
> deterministic, extra model cost. Action `description`s must be
> excellent. Same `@Action` style, different runtime. Prefer GOAP
> for core business paths.

**Recall cue:** Top-hat mouth → Supervisor LLM pick.

**Review question:** What does Supervisor need on every action?

**Review answer:** Good descriptions — LLM picks typed tools

**Metaphor part:** exit-slot

**Hero from:** The same compass-serpent wearing a satchel-hood that zips shut at the goal-bell.

**Hero now:** The same compass-serpent with a concierge top-hat mouth and a description megaphone.

**Hero silhouette:** Satchel-serpent with a talking top-hat mouth.

**Hero tell:** Megaphone at the mouth; the legs are unchanged.

**Hero shot:** Mouth and megaphone dominate; legs still visible as traces.

**Hero becomes:** The finished bronze compass-serpent the size of a citadel aisle, ready for recall.


**Key distinction:** Type-informed is not deterministic.

### Floor 2 Recall Route

`GOAP → Utility → Hybrid → Supervisor`

------------------------------------------------------------------------

# 4. Final Active-Recall Sequence

End the video with locations only. Ask the learner to name the
concept, then reveal:

### Floor 1

**How a process lives**

`Four objects → No FSM → Type posts effect → OODA → cost → Stuck status`

### Floor 2

**Four planners**

`GOAP → Utility → Hybrid → Supervisor`


# 5. Study Map

Cheat sheet planners. Lessons 03, 10, 13, 16, 17.
Blogs: Rod Johnson planning / LangGraph comparison.
Next palace: Annotation Armory.
