# Embabel Cheat Sheet Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Generate a guided study video that teaches Embabel from the
learning-repo cheat sheet — planner rules first, then PromptRunner APIs —
as a two-floor memory palace.

**Audience:** JVM engineers who know Spring and want Embabel 1.0 plus
1.5 extras as recallable rooms, not a slide deck and not a cookbook
travel plot.

**Learning method:** Method of loci / memory palace. Every locus must
have one dominant concept, one exaggerated visual mnemonic, and one
concise technical explanation.

**Video style:** First-person cinematic walkthrough of one living
type-foundry. The narrator physically moves from locus to locus.
Technical labels appear briefly as clean overlays. Visuals should be
exaggerated, memorable, and slightly absurd without becoming
distracting. Every locus is the next metamorphosis of the same hero —
never a reused generic hallway and never a new disconnected prop.

**Narration style:** Calm technical instructor. Explain the mnemonic
first, then connect it to the Embabel rule. Pause briefly between
loci to encourage active recall.

**Metaphor:** The palace is one living type-foundry. We enter as raw
input and the building grows pipes, organs, and stamps as Embabel
plans, then opens a mouth that speaks typed objects.

**Hero:** A glowing bronze type-ingot that starts unlabeled, sprouts
typed pipes and a compass eye, grows a split workbench and a bolted
toolbox, then becomes a mold-mouth that can slump to null, wear
message-skin, think in a glass skull, stream JSON vertebrae, and
sprout an inspector clipboard.

**Hero origin:** A sealed unlabeled bronze seed held in the foundry
gate, stamped only `UserInput`.

**Metaphor material:** Warm bronze architecture, glass pipes of
flowing types, coal-orange light in the foundry, cooler teal light
in the model-mouth.

**Core rule:** One locus = one concept. One memorable image = one
retrieval hook.

------------------------------------------------------------------------

# 1. Building Overview

The foundry has two floors that match the cheat sheet, not cookbook
chapters:

1.  **Floor 1 --- 1.0 mental model:** Types, replan, code+LLM, tools
    per call, named bindings, planner hats.
2.  **Floor 2 --- PromptRunner + 1.5 extras:** `creating`,
    `createObjectIfPossible`, messages, thinking, streaming,
    inspectors.

Teaching source: `docs/CHEATSHEET.md`. Dual versions:
`docs/VERSIONS.md`. Official recipes stay in the Cookbook; this
palace is the recall list.

------------------------------------------------------------------------

# 2. Floor 1 --- 1.0 Mental Model

## Learning Objective

Remember the rules that stay true on Embabel 1.0 and 1.5: types are
the wiring, the process replans after every action, tools attach per
call, and planner choice is a product decision.

**Metaphor region:** The Foundry — metal, pipes, stamps

**Hero stage:** The seed opens into a pipe-creature

**Metaphor entry:** We arrive at the unlabeled seed

**Metaphor exit:** Four hats stack on the creature; we climb toward
the model-mouth

## Scene 1 --- Pipe Hall: Types Are The Wiring

**Visual:** Giant glass pipes labeled `UserInput`, `Story`,
`ReviewedStory` snake through the hall. Floor tiles numbered 1-2-3
are crossed out. The bronze seed splits and becomes a pipe-creature
whose body is those types. No `if` signs anywhere.

**Overlay:** `@Action types`

**Narration:**

> Actions do not run in source order. GOAP walks blackboard types.
> Returning a new type posts an effect. Design domain objects
> carefully — they are the control flow.

**Recall cue:** Glass pipes → types are the wiring.

**Metaphor part:** The seed splits into typed pipes.

**Hero from:** Sealed unlabeled bronze seed stamped `UserInput`.

**Hero now:** A pipe-creature whose body is labeled type-tubes.

**Hero becomes:** The lead pipe grows a spinning compass eye.

**Key distinction:** Source order is not execution order.

------------------------------------------------------------------------

## Scene 2 --- Compass Rotunda: Replan After Every Action

**Visual:** A huge OODA compass spins after every door the
pipe-creature walks through. Observe, Orient, Decide, Act glow in
sequence. The creature's lead pipe grows a compass for an eye.

**Overlay:** `REPLAN`

**Narration:**

> After every action Embabel reassesses the world. Plans are not
> fixed scripts. New objects, `null`, or flipped conditions change
> the next step. This is an OODA loop.

**Recall cue:** Spinning compass → replan.

**Metaphor part:** The compass eye.

**Hero from:** A pipe-creature of labeled type-tubes.

**Hero now:** The same creature with a spinning OODA compass for an
eye.

**Hero becomes:** The body splits into a steel stamp-hand and a
glowing quill-hand.

------------------------------------------------------------------------

## Scene 3 --- Split Workbench: Mix Code And LLM

**Visual:** One workbench, two arms of the same creature. The left
is a Spring steel stamp that prints deterministic results. The
right is a glowing quill that writes typed LLM objects. A sign
reads "not every action needs a model."

**Overlay:** `code + LLM`

**Narration:**

> Mix code agency with LLM agency. Deterministic Spring services
> inside `@Action` methods are cheaper, testable, and safer. Use
> the model only at the typed boundary.

**Recall cue:** Stamp and quill → code plus LLM.

**Metaphor part:** Split workbench arms.

**Hero from:** Pipe-creature with a compass eye.

**Hero now:** The creature grows a steel stamp-hand and a glowing
quill-hand.

**Hero becomes:** A toolbox bolts onto the quill wrist only.

------------------------------------------------------------------------

## Scene 4 --- Bolted Toolbox: Tools Attach Per Call

**Visual:** A toolbox is bolted to the quill wrist, not to the
foundry walls. `@Tool` wrenches sit in the box. A second desk has
no box and the wrenches are invisible. A tag reads
`withToolObject`.

**Overlay:** `withToolObject`

**Narration:**

> Tools attach to a PromptRunner, not the whole agent forever.
> `@Tool` and `@LlmTool` are invisible until that call chain
> attaches them. Scope tools narrowly.

**Recall cue:** Wrist toolbox → per-call tools.

**Metaphor part:** Toolbox on one wrist.

**Hero from:** Stamp-hand and quill-hand.

**Hero now:** A toolbox bolted only onto the quill wrist.

**Hero becomes:** Two identical crates hang from the pipes; only
the labeled one opens.

------------------------------------------------------------------------

## Scene 5 --- Named Crates: Bindings Beat Latest Wins

**Visual:** Two identical bronze crates. The unlabeled crate is
latest-wins and swallows the first. The crate stamped
`outputBinding` opens only for `@RequireNameMatch`. The creature
hangs both crates from its pipes.

**Overlay:** `@RequireNameMatch`

**Narration:**

> Latest object of a type wins unless you name it. Use
> `outputBinding` plus `@RequireNameMatch` when two values share a
> type. Otherwise the wrong instance surprises you.

**Recall cue:** Labeled crate → named binding.

**Metaphor part:** Named crates on the pipes.

**Hero from:** Toolbox on the quill wrist.

**Hero now:** Two crates hang from the pipes; only the named crate
opens.

**Hero becomes:** Four hats stack on the compass head.

------------------------------------------------------------------------

## Scene 6 --- Hat Rack: Planner Choice

**Visual:** Four hats stack on the compass head: a cheap path
cap (GOAP), a greedy gold crown (Utility), a gather-then-stop
satchel-hat (Hybrid), and an LLM concierge top hat (Supervisor).
Same pipe-creature, different hat, different walk.

**Overlay:** `planner`

**Narration:**

> Planner choice is a product decision. GOAP takes the cheap typed
> path to a goal. Utility grabs highest net value. Hybrid gathers
> then stops. Supervisor lets the LLM pick typed tools — flexible,
> less deterministic, extra cost. Same `@Action` style, different
> runtime.

**Recall cue:** Four hats → four planners.

**Metaphor part:** Hats on the compass head.

**Hero from:** Named crates on the pipes.

**Hero now:** Four hats stacked on the compass head.

**Hero becomes:** The creature's mouth becomes a record-shaped
mold.

### Floor 1 Recall Route

`Types → Replan → Code+LLM → Per-call tools → Named bindings → Planner hats`

------------------------------------------------------------------------

# 3. Floor 2 --- PromptRunner And 1.5 Extras

## Learning Objective

Remember how `Ai` and `PromptRunner` create typed objects, fail
softly, speak in messages, and — on Embabel 1.5 — think, stream,
and inspect tool calls.

**Metaphor region:** The Model-Mouth — teal light, glass, breath

**Hero stage:** The pipe-creature becomes a speaking mold

**Metaphor entry:** We climb from hats into a glowing mouth

**Metaphor exit:** An inspector clipboard is the last organ

## Scene 1 --- Mold Mouth: Typed Create Object

**Visual:** The creature's mouth is a steel mold that only accepts
a record-shaped ingot. `creating(Story.class)` is engraved on the
mold. An example ingot sits beside it. A validation stamp waits.

**Overlay:** `creating(T)`

**Narration:**

> Prefer typed `createObject` and `creating(T).fromPrompt` over
> free text. `Ai` gives you a PromptRunner. Add an example and
> optional validation, then `fromPrompt`. Selection later is
> usually by goal return type.

**Recall cue:** Record mold → typed create.

**Metaphor part:** The mold-mouth.

**Hero from:** Four hats on the compass head.

**Hero now:** The mouth is a record-shaped mold.

**Hero becomes:** Soft clay slumps through a hole labeled `null`.

------------------------------------------------------------------------

## Scene 2 --- Null Pit: Create Object If Possible

**Visual:** Soft clay tries the mold and slumps through a hole
labeled `null`. A warning lantern glows; nothing explodes. A
side door marked `REPLAN` opens. The same creature now looks
half-melted.

**Overlay:** `createObjectIfPossible`

**Narration:**

> `createObjectIfPossible` is the nullable path. Incomplete
> prompts return `null`, log a warning, and do not throw. A null
> result is a signal to replan — often toward HITL.

**Recall cue:** Slump hole → null then replan.

**Metaphor part:** The null pit under the mold.

**Hero from:** Record-shaped mold-mouth.

**Hero now:** Half-melted clay slumping through a `null` hole.

**Hero becomes:** Envelope-skin labeled System and User.

------------------------------------------------------------------------

## Scene 3 --- Envelope Skin: Messages And Contributors

**Visual:** The clay grows envelope-skin. A `SystemMessage` sash
and a `UserMessage` sash stack in order. A single fat string
scroll is crossed out. `fromMessages` is etched on the stack.

**Overlay:** `fromMessages`

**Narration:**

> Prompt contributors build the request from structured messages,
> not one concatenated blob. `SystemMessage` plus `UserMessage`
> via `fromMessages`. Personas and contributors specialize
> without baking tone into every prompt string.

**Recall cue:** Stacked envelopes → fromMessages.

**Metaphor part:** Envelope-skin.

**Hero from:** Half-melted clay at the null hole.

**Hero now:** The same body wearing System and User envelopes.

**Hero becomes:** A glass skull with a thinking ticker.

------------------------------------------------------------------------

## Scene 4 --- Glass Skull: Thinking Traces

**Visual:** A glass skull grows on the envelope body. Inside, a
ticker prints `<decision_reasoning>`. A gate reads
`supportsThinking`. A card shows `ThinkingResponse` with result
plus thinking blocks. A 1.5 badge hangs from the skull.

**Overlay:** `thinking()`

**Narration:**

> On Embabel 1.5, `thinking()` asks for an explicit rationale
> plus the typed result. Call it only when `supportsThinking()`
> is true. Read `thinkingBlocks`. This is an extra, not a
> replacement for 1.0 object creation.

**Recall cue:** Glass skull ticker → thinking traces.

**Metaphor part:** The glass skull.

**Hero from:** Envelope-skinned body.

**Hero now:** A glass skull ticking decision reasoning.

**Hero becomes:** A conveyor spine of JSON bricks.

------------------------------------------------------------------------

## Scene 5 --- Conveyor Spine: Streaming Objects

**Visual:** The spine becomes a conveyor. JSON bricks arrive one
by one and freeze into typed records. A gate reads
`supportsStreaming`. A stopwatch hangs. A 1.5 badge is riveted
to the belt.

**Overlay:** `createObjectStream`

**Narration:**

> `createObject` waits. Streaming emits typed objects as they
> arrive. Wrap the runner in `StreamingPromptRunnerBuilder`,
> call `createObjectStream`, and collect with `doOnNext`. Guard
> on `supportsStreaming`. Another 1.5 extra.

**Recall cue:** One brick at a time → stream.

**Metaphor part:** Conveyor spine.

**Hero from:** Glass skull with a thinking ticker.

**Hero now:** A conveyor spine turning JSON into records.

**Hero becomes:** An inspector clipboard as the last organ.

------------------------------------------------------------------------

## Scene 6 --- Inspector Clipboard: Tool Call Logs

**Visual:** An inspector clipboard grows as the last organ. It
photographs every wrench swing from the wrist toolbox. `@LlmTool`
wrenches hang in view. The mold-mouth then prints a typed plan.

**Overlay:** `withToolCallInspectors`

**Narration:**

> Mark methods `@LlmTool` or `@Tool`, attach them with
> `withToolObject`, and on 1.5 add `withToolCallInspectors` to
> log each call. The model should use the tools before writing
> the typed result. Invisible tools are the classic bug.

**Recall cue:** Clipboard photo → inspected tool call.

**Metaphor part:** The inspector clipboard.

**Hero from:** Conveyor spine of JSON bricks.

**Hero now:** A clipboard organ watching the wrist toolbox.

**Hero becomes:** The finished living foundry, ready for recall.

### Floor 2 Recall Route

`creating → ifPossible → fromMessages → thinking → stream → inspectors`

------------------------------------------------------------------------

# 4. Final Active-Recall Sequence

End the video with locations only. Ask the learner to name the
concept, then reveal:

### Floor 1

**Mental model**

`Types → Replan → Code+LLM → Per-call tools → Named bindings → Planner hats`

### Floor 2

**PromptRunner**

`creating → ifPossible → fromMessages → thinking → stream → inspectors`

------------------------------------------------------------------------

# 5. Study Map

Cheat sheet: `docs/CHEATSHEET.md`

Versions: `docs/VERSIONS.md` — default 1.0; `-Pembabel-15` for extras.

Official sources (extracted, not cloned):

- User Guide: https://docs.embabel.com/embabel-agent/guide/1.5.0-SNAPSHOT/
- Cookbook 1.5: https://docs.embabel.com/embabel-cookbook/1.5.0/
