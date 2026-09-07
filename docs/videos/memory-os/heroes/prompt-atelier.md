# Prompt Atelier Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Memorize LLM call-shape extras that the filmed Foundry
floor 2 does not isolate: injected `Ai` without an agent, `withId`,
personas, mix-models, Bean Validation, conservative temperature / local
models.

**Audience:** Lesson 01, lesson 02 personas, VERSIONS, advanced nuances.

**Learning method:** Method of loci. One locus = one concept.

**Video style:** First-person atelier beside the mold-mouth. Every
locus is the next organ of the glass fountain-pen. No lettering in
stills.

**Narration style:** Mnemonic first, then the rule.

**Metaphor:** A side studio off the Model-Mouth. A fountain-pen spark
learns to speak without becoming a full foundry creature.

**Hero:** A glass fountain-pen the size of a person. Jarred spark →
named lantern → mask-sashes → two-throated choir → validation stamp
→ local lantern plus boring thermostat.

**Chassis:** a glass fountain-pen the size of a person

**Hero origin:** A loose glass fountain-pen sparking beside the mold-mouth.

**Camera:** first-person, eye-level, wide-angle, standing just inside the threshold, looking at the next organ of the same hero

**Hero scale:** the hero is architectural — it fills at least 40% of the frame and is the first thing the eye hits

**Metaphor material:** Teal glass, quill-light, quiet bronze desks.

**Core rule:** One locus = one concept. Last organ grew this one.

------------------------------------------------------------------------

# 1. Building Overview

One floor, six loci. Typed `creating`, `ifPossible`, messages,
thinking, streaming, inspectors remain in the Type-Foundry.

Teaching source: lesson 01, cheat-sheet LLM patterns, nuances.

------------------------------------------------------------------------

# 2. Floor 1 --- Call Shape Extras

## Learning Objective

You can use Embabel’s `Ai` from any Spring bean. Name the interaction.
Specialize with contributors. Mix models. Validate. Prefer boring
temperature and local models when the step is small.

**Metaphor region:** The Side Desk

**Hero stage:** Spark becomes a disciplined PromptRunner sprite

**Metaphor entry:** Loose spark

**Metaphor exit:** Local lantern + thermostat

## Scene 1 --- Spring Jar: Injected Ai

**Visual:** The spark is trapped in a Spring-jar on a plain bean desk.
No agent plaque. The jar still pours typed ink.

**Overlay:** `Ai`

**Narration:**

> Inject `Ai` into any Spring bean. You can add typed LLM features
> without inventing a full agent process. No planner, no blackboard
> yet. Lesson 01.

**Recall cue:** Spark in a jar → injected Ai, no agent.

**Review question:** Must you write an `@Agent` to call an LLM?

**Review answer:** No — inject Ai on any Spring bean

**Metaphor part:** head-slot

**Hero from:** A loose glass fountain-pen sparking beside the mold-mouth.

**Hero now:** The same fountain-pen trapped in a Spring-jar on a bean desk.

**Hero silhouette:** Glass fountain-pen in a Spring-jar.

**Hero tell:** No agent plaque on the desk; the jar still pours ink.

**Hero shot:** The jarred pen fills the frame on a plain desk.

**Hero becomes:** The same jarred fountain-pen with a name-lantern hanging from the lid.


**Key distinction:** `Ai` helper ≠ GOAP process.

------------------------------------------------------------------------

## Scene 2 --- Name Lantern: withId

**Visual:** A lantern hangs from the lid, lighting only this
interaction in the log-fog.

**Overlay:** `withId`

**Narration:**

> `withId(...)` names the interaction for logs and observability.
> Teaching demos should always name calls. Unnamed sparks vanish in
> the fog.

**Recall cue:** Lid lantern → withId.

**Review question:** How do you name an LLM interaction for logs?

**Review answer:** withId

**Metaphor part:** seeing-slot

**Hero from:** The same fountain-pen trapped in a Spring-jar on a bean desk.

**Hero now:** The same jarred fountain-pen with a name-lantern hanging from the lid.

**Hero silhouette:** Jarred pen with a hanging name-lantern.

**Hero tell:** Only this lantern cuts the log-fog.

**Hero shot:** Lantern hanging from the lid into log-fog.

**Hero becomes:** The same jarred fountain-pen wearing two mask-sashes.


**Key distinction:** Naming is observability, not prompting.

------------------------------------------------------------------------

## Scene 3 --- Mask Sashes: Personas / Contributors

**Visual:** Two mask-sashes wrap the jar: a wild writer-mask and a
sober reviewer-mask. The prompt string stays thin.

**Overlay:** `PromptContributor`

**Narration:**

> Personas and prompt contributors specialize tone without baking it
> into every prompt string. Writer vs reviewer is a sash, not a
> concatenated blob. `fromMessages` lives in the Foundry.

**Recall cue:** Mask-sashes → contributors, not fat strings.

**Review question:** How do you specialize tone without fat prompts?

**Review answer:** Personas / PromptContributors

**Metaphor part:** hearing-slot

**Hero from:** The same jarred fountain-pen with a name-lantern hanging from the lid.

**Hero now:** The same jarred fountain-pen wearing two mask-sashes.

**Hero silhouette:** Named-jar pen wrapped in two mask-sashes.

**Hero tell:** Two masks; the prompt ribbon stays thin.

**Hero shot:** Two mask-sashes wrapping the jar, thin prompt ribbon.

**Hero becomes:** The same fountain-pen with two role-collared throats, cheap and strong.


**Key distinction:** Contributors compose; they do not replace types.

------------------------------------------------------------------------

## Scene 4 --- Two Throats: Mix Models

**Visual:** The fountain-pen grows two throats: a small cheap throat
for drafts, a larger throat for review. Role-collars, not hard-wired
names.

**Overlay:** `withLlm / role`

**Narration:**

> Mix models per action. Cheap for draft, stronger for review. Pin a
> role in config (`best`, `cheapest`) rather than scattering model
> ids. Local models belong on small typed steps.

**Recall cue:** Two throats → mix models by role.

**Review question:** Must the whole agent use one model?

**Review answer:** No — mix models per action / role

**Metaphor part:** scent-slot

**Hero from:** The same jarred fountain-pen wearing two mask-sashes.

**Hero now:** The same fountain-pen with two role-collared throats, cheap and strong.

**Hero silhouette:** Masked pen with a small throat and a large throat.

**Hero tell:** Role-collars, not nameplates, on the throats.

**Hero shot:** Two unlike throats with role-collars.

**Hero becomes:** The same two-throated fountain-pen with a validation stamp beside the ink.


**Key distinction:** Role config survives model swaps.

------------------------------------------------------------------------

## Scene 5 --- Validation Stamp: Bean Validation

**Visual:** A stamp with a pattern-grid waits. Ink that fails the
grid never leaves the jar. Kotlin needs the stamp on the *field*.

**Overlay:** `@Pattern / Bean Validation`

**Narration:**

> Bean Validation on the result type constrains generation. Java
> records take `@Pattern` on the component. Kotlin data classes need
> `@field:Pattern`. Validation is part of the typed-create path.

**Recall cue:** Pattern stamp → Bean Validation.

**Review question:** Where does Kotlin put `@Pattern`?

**Review answer:** @field:Pattern on the property

**Metaphor part:** passage-slot

**Hero from:** The same fountain-pen with two role-collared throats, cheap and strong.

**Hero now:** The same two-throated fountain-pen with a validation stamp beside the ink.

**Hero silhouette:** Two-throated pen next to a pattern-grid stamp.

**Hero tell:** Failed ink never leaves the jar.

**Hero shot:** Pattern-grid stamp waiting beside the ink.

**Hero becomes:** The same fountain-pen with a local lantern and a locked boring thermostat.


**Key distinction:** Schema + validation beat prompt begging.

------------------------------------------------------------------------

## Scene 6 --- Local Lantern: Temperature And Local Models

**Visual:** A small local lantern lights the desk. A thermostat
locked at a boring setting. A fancy temperature dial is crossed by
a dead needle.

**Overlay:** `local / temperature`

**Narration:**

> Small focused actions can use local or cheap models. Some models
> reject custom temperature — keep reusable builders boring. Privacy
> and cost live in this lantern, not in a god-model.

**Recall cue:** Local lantern + boring thermostat → mix local, keep temp dull.

**Review question:** Should reusable builders set exotic temperatures?

**Review answer:** No — keep options conservative

**Metaphor part:** vault-slot

**Hero from:** The same two-throated fountain-pen with a validation stamp beside the ink.

**Hero now:** The same fountain-pen with a local lantern and a locked boring thermostat.

**Hero silhouette:** Stamped pen with a small lantern and a locked thermostat.

**Hero tell:** The fancy temperature dial's needle is dead.

**Hero shot:** Small local lantern and a locked thermostat.

**Hero becomes:** The finished glass fountain-pen the size of a person, ready for recall.


**Key distinction:** Focused steps enable local models.

### Floor 1 Recall Route

`Injected Ai → withId → Personas → Mix models → Validation → Local/temp`

------------------------------------------------------------------------

# 3. Final Active-Recall Sequence

End the video with locations only. Ask the learner to name the
concept, then reveal:

### Floor 1

**Call-shape extras**

`Injected Ai → withId → Personas → Mix models → Validation → Local/temp`


# 4. Study Map

Lesson 01, 02, 09. Foundry floor 2 for create/think/stream.
Blogs: Dan Vega first look; local-models interview.
Next palace: Looping Observatory.
