# DICE Workshop Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Memorize Domain-Integrated Context Engineering: the domain
object *is* the tool surface, attachment is per call, groups and custom
tools are opt-in, and ToolCallContext never enters the JSON schema.

**Audience:** Lessons 04 and 07, plus Rod Johnson’s DICE blog.

**Learning method:** Method of loci. One locus = one concept.

**Video style:** First-person workshop. Every locus is the next organ
of the Customer-Golem. No lettering in stills.

**Narration style:** Mnemonic first, then the rule.

**Metaphor:** A bank-support workshop. The customer statue comes alive
and grows tools from its own pockets.

**Hero:** A bronze customer-golem. Sewn pockets unzip into wrenches;
one pocket stays sewn; a toolbox bolts onto a single handshake; a web
satchel, a clock-hand, and a sealed coat-envelope finish the body.

**Hero origin:** A customer statue with every pocket sewn shut — a
domain record with no tools exposed.

**Metaphor material:** Bank-bronze, leather pockets, coal-orange lamps.

**Core rule:** One locus = one concept. Last organ grew this one.

------------------------------------------------------------------------

# 1. Building Overview

One floor, seven loci (Workshop table) — attachment through OOB
metadata. Inspectors stay in the filmed Foundry.

Teaching source: lesson 04 `BankCustomer`, lesson 07 tooling;
DICE blog.

------------------------------------------------------------------------

# 2. Floor 1 --- Domain As Tools

## Learning Objective

LLMs should call *your* domain behavior, not a pile of untyped
functions. Attachment is narrow. Secrets stay off the schema.

**Metaphor region:** The Pocket Forge

**Hero stage:** Statue becomes a tool-bodied golem

**Metaphor entry:** Sewn-shut statue

**Metaphor exit:** Sealed envelope under the coat

## Scene 1 --- Living Statue: DICE

**Visual:** The statue inhales and becomes a person-shaped domain
object. Prompts wrap around its bronze skin as structured pockets,
not as a fog of strings.

**Overlay:** `DICE`

**Narration:**

> Domain-Integrated Context Engineering: domain objects structure
> both inputs and outputs. They are not mere structs. Behavior lives
> on the type. Agents speak the language of the business.

**Recall cue:** Living statue → DICE domain objects.

**Review question:** What does DICE add to context engineering?

**Review answer:** Typed domain objects structure context and outputs

**Metaphor part:** head-slot

**Hero from:** A customer statue with sewn-shut pockets.

**Hero now:** The same statue alive as a person-shaped domain golem.

**Hero silhouette:** Bronze customer-golem, pockets still sewn

**Hero tell:** Skin is structured pockets, not fog

**Hero becomes:** Wrenches unzip from selected pockets.

**Key distinction:** Domain is the bridge to existing systems.

------------------------------------------------------------------------

## Scene 2 --- Unzipped Wrenches: @Tool + withToolObject

**Visual:** Selected pockets unzip. Wrenches (tool methods) only
exist while a handshake-clamp holds that golem to one prompt desk.

**Overlay:** `withToolObject`

**Narration:**

> `@Tool` methods on the domain are invisible until
> `withToolObject(instance)` on that PromptRunner. Lookup stays in
> your repository. The model gets the minimum surface you unzip.

**Recall cue:** Handshake clamp → withToolObject.

**Review question:** When does the model see `@Tool` methods?

**Review answer:** Only after withToolObject on that call

**Metaphor part:** seeing-slot

**Hero from:** Living customer-golem with sewn pockets.

**Hero now:** The same golem with selected pockets unzipped into
wrenches, clamped to one desk.

**Hero silhouette:** Golem with unzipped wrench-pockets at one desk

**Hero tell:** Clamp on one handshake; other desks have no wrenches

**Hero becomes:** One pocket refuses to unzip.

**Key distinction:** Annotation without attachment is a silent bug.

------------------------------------------------------------------------

## Scene 3 --- Sewn Pocket: Unannotated Stays Private

**Visual:** One pocket stays sewn. A hidden note (`internalRiskNote`)
never becomes a wrench. The model cannot pick it.

**Overlay:** `private`

**Narration:**

> Unannotated methods stay application-private. Expose the minimum
> tool surface you can justify. Internal risk notes, raw SQL, and
> admin paths do not belong in the model’s toolbox.

**Recall cue:** Sewn pocket → private method.

**Review question:** Are all domain methods tools?

**Review answer:** No — only annotated ones, and only when attached

**Metaphor part:** hearing-slot

**Hero from:** Golem with unzipped wrench-pockets.

**Hero now:** The same golem with one pocket still sewn shut.

**Hero silhouette:** Wrench-golem with one stubborn sewn pocket

**Hero tell:** A hidden note inside the sewn pocket, no wrench there

**Hero becomes:** The clamp proves the toolbox is on the desk, not
the building.

**Key distinction:** Privacy is the default; exposure is opt-in.

------------------------------------------------------------------------

## Scene 4 --- Desk Clamp: Per PromptRunner

**Visual:** The wrench-belt is bolted to one prompt desk. A second
desk in the same workshop has no belt. The golem’s body is unchanged.

**Overlay:** `PromptRunner`

**Narration:**

> Tools attach to a PromptRunner instance, not the whole agent
> forever. Attaching in one action does not attach everywhere. Scope
> narrowly per call.

**Recall cue:** One desk bolted → per-call tools.

**Review question:** Do tools attach to the agent for the whole process?

**Review answer:** No — per PromptRunner call

**Metaphor part:** scent-slot

**Hero from:** Golem with one sewn pocket.

**Hero now:** The same golem whose wrench-belt is bolted to a single
desk.

**Hero silhouette:** Sewn-pocket golem bolted to one prompt desk

**Hero tell:** Second empty desk beside it

**Hero becomes:** A web-satchel hangs from the clamped belt.

**Key distinction:** Agent-scoped tools are a mental bug.

------------------------------------------------------------------------

## Scene 5 --- Web Satchel: Tool Groups

**Visual:** A satchel of bundled instruments (search, fetch) hangs
from the belt. Some instruments only work if a provider lamp is lit.

**Overlay:** `withToolGroup`

**Narration:**

> `CoreToolGroups.WEB` and friends are capability bundles. They may
> need runtime providers or MCP clients. Tell the model *when* to
> use them. Still attached per call.

**Recall cue:** Satchel bundle → tool group.

**Review question:** What is a tool group?

**Review answer:** A capability bundle attached per call

**Metaphor part:** passage-slot

**Hero from:** Golem bolted to one desk.

**Hero now:** The same golem with a web-satchel hanging from the belt.

**Hero silhouette:** Desk-bolted golem with a bundled instrument satchel

**Hero tell:** Provider lamp must be lit or instruments stay dull

**Hero becomes:** A clock-hand grows from a custom pocket.

**Key distinction:** Bundles are not magically global either.

------------------------------------------------------------------------

## Scene 6 --- Clock Hand: @LlmTool

**Visual:** A custom clock-hand sprouts from a workshop pocket,
discovered only when a stamp presses `Tool.fromInstance`.

**Overlay:** `@LlmTool`

**Narration:**

> Custom in-process tools use `@LlmTool`. `Tool.fromInstance`
> discovers them. Prompt text should say when to call. Still
> invisible until attached.

**Recall cue:** Clock-hand → @LlmTool.

**Review question:** How do custom tool methods get discovered?

**Review answer:** Tool.fromInstance on @LlmTool methods

**Metaphor part:** vault-slot

**Hero from:** Golem with a web-satchel.

**Hero now:** The same golem with a custom clock-hand from one pocket.

**Hero silhouette:** Satchel-golem with a clock-hand tool

**Hero tell:** Stamp press beside the clock-hand; hand hidden until stamped

**Hero becomes:** A sealed envelope slides under the coat, never
shown to the wrenches.

**Key distinction:** `@LlmTool` and domain `@Tool` are different
attachments with the same per-call rule.

------------------------------------------------------------------------

## Scene 7 --- Coat Envelope: ToolCallContext

**Visual:** A sealed envelope under the coat (tenant, auth). Wrenches
cannot read it. It never enters the tool JSON.

**Overlay:** `ToolCallContext`

**Narration:**

> `ToolCallContext` is out-of-band metadata — auth, tenant — not part
> of the tool JSON schema. The model must not see secrets. Pass it on
> `ProcessOptions` / the call, not in the prompt.

**Recall cue:** Hidden envelope → out-of-band context.

**Review question:** Is ToolCallContext in the LLM tool schema?

**Review answer:** No — out-of-band, not model-visible

**Metaphor part:** works-slot

**Hero from:** Golem with a clock-hand.

**Hero now:** The same golem with a sealed envelope under the coat.

**Hero silhouette:** Clock-hand golem with a coat-hidden envelope

**Hero tell:** Envelope under the coat; wrenches cannot open it

**Hero becomes:** The finished workshop golem, ready for recall.

**Key distinction:** Schema ≠ security context.

### Floor 1 Recall Route

`DICE → @Tool → Private → Per-call → Groups → @LlmTool → ToolCallContext`

------------------------------------------------------------------------

# 3. Final Active-Recall Sequence

Unzip, sewn, clamp, satchel, clock, envelope. Name which one the model
never sees.

------------------------------------------------------------------------

# 4. Study Map

Lessons 04, 07. DICE blog. Inspectors: Foundry floor 2.
Subagent composition: Looping Observatory locus 10.
Next palace: Prompt Atelier.
