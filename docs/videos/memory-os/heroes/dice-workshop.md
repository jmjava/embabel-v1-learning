# DICE Workshop Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Memorize Domain-Integrated Context Engineering: the domain
object *is* the tool surface, attachment is per call, groups and custom
tools are opt-in, and ToolCallContext never enters the JSON schema.

**Audience:** Lessons 04 and 07, plus Rod Johnson’s DICE blog.

**Learning method:** Method of loci. One locus = one concept.

**Video style:** First-person workshop. Every locus is the next organ
of the shop-window mannequin. No lettering in stills.

**Narration style:** Mnemonic first, then the rule.

**Metaphor:** A bank-support workshop. The customer statue comes alive
and grows tools from its own pockets.

**Hero:** A bronze shop-window mannequin. Sewn pockets unzip into
wrenches; one pocket stays sewn; a toolbox bolts onto a single
handshake; a web satchel, a clock-hand, and a sealed coat-envelope
finish the body.

**Chassis:** a bronze shop-window mannequin

**Hero origin:** A bronze shop mannequin with every pocket sewn shut.

**Camera:** first-person, eye-level, wide-angle, standing just inside the threshold, looking at the next organ of the same hero

**Hero scale:** the hero is architectural — it fills at least 40% of the frame and is the first thing the eye hits

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

**Hero stage:** Sewn statue becomes a tool-bodied mannequin

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

**Hero from:** A bronze shop mannequin with every pocket sewn shut.

**Hero now:** The same mannequin alive as a person-shaped domain statue, pockets still sewn.

**Hero silhouette:** Bronze customer-mannequin, pockets still sewn.

**Hero tell:** Skin is structured pockets, not fog.

**Hero shot:** Front of the living mannequin, sewn pockets catching light.

**Hero becomes:** The same mannequin with selected pockets unzipped into wrenches, clamped to one desk.


**Key distinction:** Domain is the bridge to existing systems.

------------------------------------------------------------------------

## Scene 2 --- Unzipped Wrenches: @Tool + withToolObject

**Visual:** Selected pockets unzip. Wrenches only exist while a
handshake-clamp holds that mannequin to one prompt desk.

**Overlay:** `withToolObject`

**Narration:**

> `@Tool` methods on the domain are invisible until
> `withToolObject(instance)` on that PromptRunner. Lookup stays in
> your repository. The model gets the minimum surface you unzip.

**Recall cue:** Handshake clamp → withToolObject.

**Review question:** When does the model see `@Tool` methods?

**Review answer:** Only after withToolObject on that call

**Metaphor part:** seeing-slot

**Hero from:** The same mannequin alive as a person-shaped domain statue, pockets still sewn.

**Hero now:** The same mannequin with selected pockets unzipped into wrenches, clamped to one desk.

**Hero silhouette:** Mannequin with unzipped wrench-pockets at one desk.

**Hero tell:** Clamp on one handshake; other desks have no wrenches.

**Hero shot:** Close on unzipped wrenches clamped to one desk.

**Hero becomes:** The same mannequin with one pocket still sewn shut.


**Key distinction:** Annotation without attachment is a silent bug.

------------------------------------------------------------------------

## Scene 3 --- Sewn Pocket: Unannotated Stays Private

**Visual:** One pocket stays sewn. A hidden note never becomes a
wrench. The model cannot pick it.

**Overlay:** `private`

**Narration:**

> Unannotated methods stay application-private. Expose the minimum
> tool surface you can justify. Internal risk notes, raw SQL, and
> admin paths do not belong in the model’s toolbox.

**Recall cue:** Sewn pocket → private method.

**Review question:** Are all domain methods tools?

**Review answer:** No — only annotated ones, and only when attached

**Metaphor part:** hearing-slot

**Hero from:** The same mannequin with selected pockets unzipped into wrenches, clamped to one desk.

**Hero now:** The same mannequin with one pocket still sewn shut.

**Hero silhouette:** Wrench-mannequin with one stubborn sewn pocket.

**Hero tell:** A hidden note inside the sewn pocket, no wrench there.

**Hero shot:** One sewn pocket in the foreground, wrenches behind.

**Hero becomes:** The same mannequin whose wrench-belt is bolted to a single desk.


**Key distinction:** Privacy is the default; exposure is opt-in.

------------------------------------------------------------------------

## Scene 4 --- Desk Clamp: Per PromptRunner

**Visual:** The wrench-belt is bolted to one prompt desk. A second
desk in the same workshop has no belt. The mannequin’s body is
unchanged.

**Overlay:** `PromptRunner`

**Narration:**

> Tools attach to a PromptRunner instance, not the whole agent
> forever. Attaching in one action does not attach everywhere. Scope
> narrowly per call.

**Recall cue:** One desk bolted → per-call tools.

**Review question:** Do tools attach to the agent for the whole process?

**Review answer:** No — per PromptRunner call

**Metaphor part:** scent-slot

**Hero from:** The same mannequin with one pocket still sewn shut.

**Hero now:** The same mannequin whose wrench-belt is bolted to a single desk.

**Hero silhouette:** Sewn-pocket mannequin bolted to one prompt desk.

**Hero tell:** Second empty desk beside it.

**Hero shot:** Belt bolted to one desk; empty desk beside.

**Hero becomes:** The same mannequin with a web-satchel hanging from the belt.


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

**Hero from:** The same mannequin whose wrench-belt is bolted to a single desk.

**Hero now:** The same mannequin with a web-satchel hanging from the belt.

**Hero silhouette:** Desk-bolted mannequin with a bundled instrument satchel.

**Hero tell:** Provider lamp must be lit or instruments stay dull.

**Hero shot:** Satchel hanging from the belt, dull instruments until the lamp.

**Hero becomes:** The same mannequin with a custom clock-hand grown from one pocket.


**Key distinction:** Bundles are not magically global either.

------------------------------------------------------------------------

## Scene 6 --- Clock Hand: @LlmTool

**Visual:** A custom clock-hand sprouts from a workshop pocket,
discovered only when a stamp-press kisses that pocket.

**Overlay:** `@LlmTool`

**Narration:**

> Custom in-process tools use `@LlmTool`. `Tool.fromInstance`
> discovers them. Prompt text should say when to call. Still
> invisible until attached.

**Recall cue:** Clock-hand → @LlmTool.

**Review question:** How do custom tool methods get discovered?

**Review answer:** Tool.fromInstance on @LlmTool methods

**Metaphor part:** vault-slot

**Hero from:** The same mannequin with a web-satchel hanging from the belt.

**Hero now:** The same mannequin with a custom clock-hand grown from one pocket.

**Hero silhouette:** Satchel-mannequin with a clock-hand tool.

**Hero tell:** Stamp press beside the clock-hand; hand hidden until stamped.

**Hero shot:** Clock-hand growing from a pocket at the stamp press.

**Hero becomes:** The same mannequin with a sealed envelope under the coat.


**Key distinction:** `@LlmTool` and domain `@Tool` are different
attachments with the same per-call rule.

------------------------------------------------------------------------

## Scene 7 --- Coat Envelope: ToolCallContext

**Visual:** A sealed envelope under the coat. Wrenches cannot read it.
It never becomes a tool in the box.

**Overlay:** `ToolCallContext`

**Narration:**

> `ToolCallContext` is out-of-band metadata — auth, tenant — not part
> of the tool JSON schema. The model must not see secrets. Pass it on
> `ProcessOptions` / the call, not in the prompt.

**Recall cue:** Hidden envelope → out-of-band context.

**Review question:** Is ToolCallContext in the LLM tool schema?

**Review answer:** No — out-of-band, not model-visible

**Metaphor part:** works-slot

**Hero from:** The same mannequin with a custom clock-hand grown from one pocket.

**Hero now:** The same mannequin with a sealed envelope under the coat.

**Hero silhouette:** Clock-hand mannequin with a coat-hidden envelope.

**Hero tell:** Envelope under the coat; wrenches cannot open it.

**Hero shot:** Coat lifted just enough to show the sealed envelope.

**Hero becomes:** The finished bronze shop-window mannequin, ready for recall.


**Key distinction:** Schema ≠ security context.

### Floor 1 Recall Route

`DICE → @Tool → Private → Per-call → Groups → @LlmTool → ToolCallContext`

------------------------------------------------------------------------

# 3. Final Active-Recall Sequence

End the video with locations only. Ask the learner to name the
concept, then reveal:

### Floor 1

**Domain as tools**

`DICE → Tool → Private → Per-call → Groups → LlmTool → ToolCallContext`


# 4. Study Map

Lessons 04, 07. DICE blog. Inspectors: Foundry floor 2.
Subagent composition: Looping Observatory locus 10.
Next palace: Prompt Atelier.
