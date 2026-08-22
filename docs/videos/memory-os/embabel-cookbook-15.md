# Embabel Cookbook 1.5 Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Generate a guided study video that teaches Embabel Agent
Framework 1.5 by walking the official Cookbook chapters as a two-floor
memory palace.

**Audience:** JVM engineers who know Spring and want the Cookbook 1.5
planner and PromptRunner APIs as recallable rooms, not a slide deck.

**Learning method:** Method of loci / memory palace. Every locus must
have one dominant concept, one exaggerated visual mnemonic, and one
concise technical explanation.

**Video style:** First-person cinematic walkthrough of a grand
travel-academy building. The narrator physically moves from locus to
locus. Technical labels appear briefly as clean overlays. Visuals should
be exaggerated, memorable, and slightly absurd without becoming
distracting. Every locus is its own room: a unique oversized hero object,
accent color, and lighting — never a reused generic hallway.

**Narration style:** Calm technical instructor. Explain the mnemonic
first, then connect it to the Embabel 1.5 API. Pause briefly between
loci to encourage active recall.

**Core rule:** One locus = one concept. One memorable image = one
retrieval hook.

------------------------------------------------------------------------

# 1. Building Overview

The academy has two floors that match the official Cookbook parts:

1.  **Floor 1 --- Planner Behavior:** How GOAP chooses and fails.
2.  **Floor 2 --- Agentic AI APIs:** How `Ai` / `PromptRunner` create,
    think, stream, and call tools.

Each scene is one Cookbook chapter. Teaching code lives in this repo
under `cookbook15` and lessons 01, 06, 07, 09, and 13.

------------------------------------------------------------------------

# 2. Floor 1 --- Planner Behavior

## Learning Objective

Remember how Embabel 1.5 plans: types first, then conditions, cost,
loops, stuck status, and the three debug views.

## Scene 1 --- Arrival Hall: Domain Type Chaining

**Visual:** A giant brass luggage carousel sorts glowing suitcases
labeled `FlightRequest` and `ItineraryRequest` onto two different
conveyor belts. Source-order numbers on the floor are crossed out.

**Overlay:** `@Action types`

**Narration:**

> Actions do not run in source order. They run when their input types
> appear. Classify user input into a flight request or an itinerary
> request, and the planner chains to the matching action, then to a
> shared travel activity that achieves the goal.

**Recall cue:** Carousel belts → type chaining.

------------------------------------------------------------------------

## Scene 2 --- Ticket Windows: Action Conditions

**Visual:** Two ticket windows. One lights up only when a neon sign
reads `directFlightRequested`. The other lights only for `stopsAllowed`.
A clerk stamps `pre` on each window.

**Overlay:** `@Condition pre`

**Narration:**

> When two actions want similar input, named conditions decide which is
> eligible. Put the condition name in `pre`. Keep condition methods
> cheap and side-effect free. The planner may evaluate them more than
> once.

**Recall cue:** Lit window → named condition.

------------------------------------------------------------------------

## Scene 3 --- Dead-End Corridor: Stuck State

**Visual:** A corridor labeled `NonTravelInquiry` ends at a brick wall
stamped `STUCK`. The travel door is locked. A clipboard shows action
history with only `classifyRequest`.

**Overlay:** `STUCK`

**Narration:**

> Stuck is a status, not a crash. If classify produces a type no later
> action consumes, the process stops with `STUCK`. Dump the blackboard
> and the history, then attach a StuckHandler or ask a follow-up.

**Recall cue:** Brick wall → stuck status.

------------------------------------------------------------------------

## Scene 4 --- Bargain Counter: Action Heuristics

**Visual:** Two identical gold tickets sit on a scale. The cheap ticket
weighs `0.1` and slams the scale down. The premium ticket weighs `0.9`
and floats. Both tickets say they achieve the same goal.

**Overlay:** `@Action cost`

**Narration:**

> When two actions produce the same goal type, GOAP uses action cost as
> a heuristic. Lower cost wins. Cost is not a dollar invoice. Use it for
> preferred defaults and expensive fallbacks.

**Recall cue:** Scale → cheaper action wins.

------------------------------------------------------------------------

## Scene 5 --- Revision Studio: Repeat Until Acceptable

**Visual:** A film-editing loop: draft reel, scoreboard showing `0.2`
then `0.9`, red feedback stamp, then a three-day itinerary accepted.
A clock on the wall reads `maxIterations`.

**Overlay:** `RepeatUntilAcceptable`

**Narration:**

> `RepeatUntilAcceptableBuilder` is a subprocess on `ActionContext`.
> Name the return type, set max iterations and a score threshold. The
> repeating lambda drafts. The evaluator returns `TextFeedback`. Below
> threshold, feedback feeds the next attempt.

**Recall cue:** Scoreboard loop → repeat until.

------------------------------------------------------------------------

## Scene 6 --- Observation Deck: Agent Debugging

**Visual:** Three giant windows overlook the academy: History, Blackboard,
and Status. A fourth window is a debugger with `@DebugGuide` sticky notes.

**Overlay:** `history + blackboard`

**Narration:**

> When a plan surprises you, look at action history, the blackboard
> types, and process status. Official cookbook tests log
> `blackboard.infoString`. This repo adds `@DebugGuide` breakpoints so
> you can step FakeOperationContext without a live model.

**Recall cue:** Three windows → debug views.

### Floor 1 Recall Route

`Types → Conditions → Stuck → Cost → RepeatUntil → Debug`

------------------------------------------------------------------------

# 3. Floor 2 --- Agentic AI APIs

## Learning Objective

Remember the 1.5 PromptRunner recipes: typed create, nullable create,
messages, thinking, streaming, and inspected tool calls.

## Scene 1 --- Type Foundry: Object Creation

**Visual:** A foundry pours molten JSON into a steel mold shaped like a
Java record. A validation stamp reads `@Pattern`. No `@Agent` badge is
in sight.

**Overlay:** `creating(Class)`

**Narration:**

> You do not need an agent. Inject `Ai`, call `creating(YourRecord.class)`,
> add examples and validation, finish with `fromPrompt`. The model must
> return JSON that matches the record.

**Recall cue:** Record mold → object creation.

------------------------------------------------------------------------

## Scene 2 --- Incomplete Desk: Create Object If Possible

**Visual:** A clerk's desk has two trays. A complete passport drops into
`TripBrief`. A scrap that says only "plan a trip" falls through a hole
labeled `null`.

**Overlay:** `createObjectIfPossible`

**Narration:**

> `createObject` will guess. `createObjectIfPossible` is the 1.5 nullable
> path. Enough destination and dates yields an object. A thin prompt
> yields null, so the app can ask a follow-up instead of hallucinating.

**Recall cue:** Hole in the desk → null if impossible.

------------------------------------------------------------------------

## Scene 3 --- Message Gallery: Prompt Contributors

**Visual:** Three framed letters hang in a row: a system portrait, then
two user postcards. They feed a single `TravelPlan` printing press.

**Overlay:** `fromMessages`

**Narration:**

> `fromPrompt` is one string. `fromMessages` is a conversation. A system
> message plus user messages still ends at `creating(TravelPlan.class)`.
> Personas and `RoleGoalBackstory` are the same idea on write-and-review
> agents.

**Recall cue:** Framed letters → fromMessages.

------------------------------------------------------------------------

## Scene 4 --- Glass Thought Room: Thinking

**Visual:** A glass booth fills with handwritten bullets inside tags
`<decision_reasoning>`. Beside the booth sits a finished itinerary and,
on a trapdoor, a stamped `ThinkingException` for New York to Sydney in
half a day.

**Overlay:** `thinking()`

**Narration:**

> Ask `supportsThinking`, then call `thinking()` and `createObject`.
> You get a `ThinkingResponse`: typed result, thinking content, optional
> exception. Impossible trips should fail closed with a reason, not a
> fake plan.

**Recall cue:** Glass booth → thinking trace.

------------------------------------------------------------------------

## Scene 5 --- Conveyor Gallery: Streaming

**Visual:** A conveyor emits three distinct itinerary statues one at a
time. A stopwatch hangs labeled `timeout`. A gate reads
`supportsStreaming`.

**Overlay:** `createObjectStream`

**Narration:**

> `createObject` waits. Streaming emits typed objects as they arrive.
> Wrap the runner in `StreamingPromptRunnerBuilder`, call
> `createObjectStream`, and collect with `doOnNext`. Fake runners usually
> cannot stream; guard on `supportsStreaming`.

**Recall cue:** One statue at a time → stream.

------------------------------------------------------------------------

## Scene 6 --- Tool Workshop: Tool Call

**Visual:** Two brass tools hang on pegs: weather vane and attraction
magnet. An inspector's lamp photographs each grab. Then a `TravelPlan`
scroll prints.

**Overlay:** `@LlmTool`

**Narration:**

> Mark methods `@LlmTool`, pass the object to `withToolObject`, and add
> `ToolCallLoggingInspector` to see each call. The model should use
> weather and attractions before writing the plan.

**Recall cue:** Inspector lamp → tool call log.

### Floor 2 Recall Route

`creating → ifPossible → fromMessages → thinking → stream → tools`

------------------------------------------------------------------------

# 4. Final Active-Recall Sequence

End the video with locations only. Ask the learner to name the concept,
then reveal:

### Floor 1

**Planner**

`Types → Conditions → Stuck → Cost → RepeatUntil → Debug`

### Floor 2

**PromptRunner**

`creating → ifPossible → fromMessages → thinking → stream → tools`

------------------------------------------------------------------------

# 5. Study Map

Official book: https://docs.embabel.com/embabel-cookbook/1.5.0/

Teaching counterparts in this repository:

- Floor 1 Scene 1 → lesson 16 `TypeChainingTravelAgent`
- Floor 1 Scene 2 → lesson 06
- Floor 1 Scene 3 → lesson 13
- Floor 1 Scene 4 → lesson 17 `HeuristicTravelAgent`
- Floor 1 Scene 5 → lesson 09
- Floor 1 Scene 6 → `@DebugGuide`
- Floor 2 Scene 1 → lesson 01
- Floor 2 Scene 2 → lesson 18 `PossibleTripPlanner`
- Floor 2 Scene 3 → lesson 21 `MessageAndToolTripPlanner`
- Floor 2 Scene 4 → lesson 19 `ThinkingTripPlanner`
- Floor 2 Scene 5 → lesson 20 `StreamingTripPlanner`
- Floor 2 Scene 6 → lesson 21 + lesson 07
