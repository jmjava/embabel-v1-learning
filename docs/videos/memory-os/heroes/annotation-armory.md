# Annotation Armory Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Memorize how you *declare* an Embabel agent: annotations,
action knobs, export, Kotlin DSL, and the Java/Kotlin call-shape split.

**Audience:** JVM engineers studying lessons 02, 10, 14, 15.

**Learning method:** Method of loci. One locus = one concept.

**Video style:** First-person armory. Every locus is the next organ of
the brass clockwork scarab. No lettering in stills.

**Narration style:** Mnemonic first, then the rule.

**Metaphor:** An armory of living stamps. The scarab *is* the Spring
component growing its badges.

**Hero:** A brass clockwork scarab that starts unmarked, grows a
description plaque, sheds a drawer-twin, grows typed-pipe legs, a
done-bell, a knob-panel, an export keyhole, a teal ribbon bow, and
twin antennae (steel needle vs glass orb).

**Chassis:** a brass clockwork scarab

**Hero origin:** An unmarked bronze scarab on the armory lintel.

**Camera:** first-person, eye-level, wide-angle, standing just inside the threshold, looking at the next organ of the same hero

**Hero scale:** the hero is architectural — it fills at least 40% of the frame and is the first thing the eye hits

**Metaphor material:** Bronze carapace, stamp-ink orange, glass legs.

**Core rule:** One locus = one concept. Last organ grew this one.

------------------------------------------------------------------------

# 1. Building Overview

One floor of eight loci (head → exit, then two finishing organs).

Teaching source: cheat-sheet Annotations table; lessons 02, 10, 14, 15;
`docs/JAVA_VS_KOTLIN.md`.

------------------------------------------------------------------------

# 2. Floor 1 --- Agent Anatomy

## Learning Objective

Know what each annotation *does*, that goal is not “last method,” and
that Kotlin DSL is an alternate registration, not a different planner.

**Metaphor region:** The Stamp Hall

**Hero stage:** Unmarked scarab becomes a fully badged agent

**Metaphor entry:** Unmarked bean on the lintel

**Metaphor exit:** Twin antennae for Java vs Kotlin

## Scene 1 --- Plaque Thorax: @Agent

**Visual:** A description plaque grows on the thorax. Other scarabs
turn toward it. No etched words — the plaque is a blank shiny plate
that *attracts* selection light.

**Overlay:** `@Agent`

**Narration:**

> `@Agent` makes a Spring component that is also an agent. The
> `description` is used for selection. If the plaque is vague, the
> wrong scarab gets chosen.

**Recall cue:** Shiny plaque → @Agent description.

**Review question:** What is `@Agent` description used for?

**Review answer:** Selection

**Metaphor part:** head-slot

**Hero from:** An unmarked bronze scarab on the armory lintel.

**Hero now:** The same scarab with a blank selection plaque grown on its thorax.

**Hero silhouette:** Bronze scarab with a shiny blank thorax plaque.

**Hero tell:** Other scarabs turn toward the plaque.

**Hero shot:** Close on the thorax plaque as neighboring scarabs turn.

**Hero becomes:** The same scarab with a drawer-twin split from its abdomen.


**Key distinction:** `@Agent` is not just a stereotype — description
matters.

------------------------------------------------------------------------

## Scene 2 --- Drawer Twin: @EmbabelComponent

**Visual:** A smaller scarab splits from the abdomen and becomes a
library drawer of spare legs. It has no goal-bell. The original still
has the plaque.

**Overlay:** `@EmbabelComponent`

**Narration:**

> `@EmbabelComponent` holds reusable actions and conditions. It is
> not a full agent. Supervisor kitchens share action libraries this
> way. Do not hang `@AchievesGoal` on the drawer-twin and expect a
> process.

**Recall cue:** Drawer-twin → action library, not an agent.

**Review question:** Is `@EmbabelComponent` an agent?

**Review answer:** No — reusable actions/conditions

**Metaphor part:** seeing-slot

**Hero from:** The same scarab with a blank selection plaque grown on its thorax.

**Hero now:** The same scarab with a drawer-twin split from its abdomen.

**Hero silhouette:** Plaque-scarab with a smaller drawer-scarab attached.

**Hero tell:** The twin has spare legs but no goal-bell.

**Hero shot:** Side view of the abdomen splitting into a drawer-twin.

**Hero becomes:** The same scarab whose legs are glass type-pipes.


**Key distinction:** Shared actions ≠ a second process.

------------------------------------------------------------------------

## Scene 3 --- Pipe Legs: @Action

**Visual:** Each walking leg is a glass pipe. Ingots enter one end
and a new shape exits the other. Stepping-stones with numbers are
absent.

**Overlay:** `@Action`

**Narration:**

> `@Action` methods are planner-callable steps. Parameter and return
> types drive GOAP. Source order of methods is not execution order.

**Recall cue:** Pipe legs → @Action types.

**Review question:** What drives GOAP for an action?

**Review answer:** Parameter and return types

**Metaphor part:** hearing-slot

**Hero from:** The same scarab with a drawer-twin split from its abdomen.

**Hero now:** The same scarab whose legs are glass type-pipes.

**Hero silhouette:** Drawer-scarab walking on glass pipe-legs.

**Hero tell:** Ingots enter a leg and a new shape exits; no numbered stones.

**Hero shot:** Low angle on glass pipe-legs with an ingot entering.

**Hero becomes:** The same scarab with a done-bell on one pipe-leg.


**Key distinction:** The method list is not the plan.

------------------------------------------------------------------------

## Scene 4 --- Done Bell: @AchievesGoal

**Visual:** A golden bell grows on one pipe-leg only — not necessarily
the rear leg. When that leg finishes, the armory gate opens. Other
legs keep walking until then.

**Overlay:** `@AchievesGoal`

**Narration:**

> `@AchievesGoal` marks completion. It is not “the last method in the
> file.” The process is done when this action’s return type is the
> goal, whenever the planner reaches it.

**Recall cue:** Bell on one leg → @AchievesGoal.

**Review question:** Does `@AchievesGoal` mean last method in the class?

**Review answer:** No — it marks done, not source order

**Metaphor part:** scent-slot

**Hero from:** The same scarab whose legs are glass type-pipes.

**Hero now:** The same scarab with a done-bell on one pipe-leg.

**Hero silhouette:** Pipe-legged scarab with a bell on a single leg.

**Hero tell:** The bell is not on the rear leg; the gate opens when it rings.

**Hero shot:** The done-bell on a middle leg, not the rear.

**Hero becomes:** The same scarab with a knob-panel on every pipe-leg.


**Key distinction:** Done is a goal annotation, not file position.

------------------------------------------------------------------------

## Scene 5 --- Knob Panel: Action Attributes

**Visual:** Each pipe-leg grows a panel: paired gate-latches, a
hanging weight, a hinge that can swing twice, a megaphone, and a
tiny broom that only sweeps looping chambers.

**Overlay:** `@Action knobs`

**Narration:**

> Memorize the knobs: `pre`/`post` name conditions; `cost`/`value`
> feed GOAP vs Utility; `canRerun` allows a rewrite to fire again;
> `description` is mandatory thinking-fuel for Supervisor;
> `clearBlackboard` belongs on looping `@State` actions, not on the
> goal action.

**Recall cue:** Leg panel → action knobs.

**Review question:** Where does `clearBlackboard` belong?

**Review answer:** Looping @State actions, not the goal

**Metaphor part:** passage-slot

**Hero from:** The same scarab with a done-bell on one pipe-leg.

**Hero now:** The same scarab with a knob-panel on every pipe-leg.

**Hero silhouette:** Bell-scarab with mechanical knob-panels on its legs.

**Hero tell:** Five different widgets on the panels; the broom is small.

**Hero shot:** Knobs along every leg, broom smallest.

**Hero becomes:** The same scarab with a remote keyhole in the carapace.


**Key distinction:** Knobs are product decisions, not decoration.

------------------------------------------------------------------------

## Scene 6 --- Keyhole Shell: @Export

**Visual:** A keyhole opens in the carapace. Three waiting keys hang
outside. The scarab does not walk out unless the keyhole is cut.

**Overlay:** `@Export`

**Narration:**

> `@Export(remote=true)` is how shell, MCP, and A2A discover goals.
> Application code should still invoke explicitly. Pair remote export
> with security when the door faces the network.

**Recall cue:** Keyhole → @Export remote.

**Review question:** How do shell/MCP/A2A discover goals?

**Review answer:** @Export(remote=true)

**Metaphor part:** vault-slot

**Hero from:** The same scarab with a knob-panel on every pipe-leg.

**Hero now:** The same scarab with a remote keyhole in the carapace.

**Hero silhouette:** Knob-scarab with a keyhole in its shell.

**Hero tell:** Three keys waiting outside; the scarab stays in until the hole is cut.

**Hero shot:** Keyhole in the shell with three waiting keys, no lettering.

**Hero becomes:** The same scarab wearing a teal ribbon tied in a bean-bow.


**Key distinction:** Export is discovery, not the only way to call.

------------------------------------------------------------------------

## Scene 7 --- DSL Ribbon: Kotlin agent { }

**Visual:** A teal ribbon ties around the plaque and knots into a
bean-bow. Pipe-legs still work. A Java stamp-press stands beside,
unchanged.

**Overlay:** `agent { }`

**Narration:**

> Kotlin may register a DSL `agent { transformation…; goal(…) }` as
> a `@Bean`. Same platform underneath. Annotations remain the Java
> default and are fine in Kotlin. DSL is compact, not a different
> planner.

**Recall cue:** Bean-bow ribbon → Kotlin DSL.

**Review question:** Does the Kotlin DSL use a different planner?

**Review answer:** No — same platform; different registration

**Metaphor part:** works-slot

**Hero from:** The same scarab with a remote keyhole in the carapace.

**Hero now:** The same scarab wearing a teal ribbon tied in a bean-bow.

**Hero silhouette:** Keyhole-scarab with a teal ribbon bow.

**Hero tell:** Ribbon knots as a bow; a Java stamp-press still stands beside it.

**Hero shot:** Teal ribbon bow on the plaque; stamp-press beside.

**Hero becomes:** The same scarab with twin antennae: a steel needle and a glass orb.


**Key distinction:** DSL is registration style, not new semantics.

------------------------------------------------------------------------

## Scene 8 --- Twin Antennae: Ai vs OperationContext

**Visual:** Two antennae: a steel needle that drinks the spark
directly, and a glass orb that must be opened before the spark
pours. Same plaque, same pipe-legs.

**Overlay:** `Ai / OperationContext`

**Narration:**

> Java lessons often take `Ai`. Kotlin lessons often take
> `OperationContext` and call `context.ai()`. HITL is `WaitFor` vs
> `fromForm`. Records vs data classes. The planner does not change.

**Recall cue:** Twin antennae → Java vs Kotlin call shape.

**Review question:** Does GOAP change between Java and Kotlin?

**Review answer:** No — call shape changes, planning does not

**Metaphor part:** grasp-slot

**Hero from:** The same scarab wearing a teal ribbon tied in a bean-bow.

**Hero now:** The same scarab with twin antennae: a steel needle and a glass orb.

**Hero silhouette:** Ribbon-scarab with two unlike antennae.

**Hero tell:** One steel needle, one glass orb; the plaque is unchanged.

**Hero shot:** Head-on: two unlike antennae filling the frame.

**Hero becomes:** The finished brass clockwork scarab, ready for recall.


**Key distinction:** Language delta is API shape, not GOAP.

### Floor 1 Recall Route

`Agent → Component → Action → Goal → Knobs → Export → DSL → Ai vs Context`

------------------------------------------------------------------------

# 3. Final Active-Recall Sequence

End the video with locations only. Ask the learner to name the
concept, then reveal:

### Floor 1

**Agent anatomy**

`Agent → Component → Action → Goal → Knobs → Export → DSL → Ai vs Context`


# 4. Study Map

Lessons 02, 10, 14, 15. `docs/JAVA_VS_KOTLIN.md`.
Next palace: DICE Workshop.
