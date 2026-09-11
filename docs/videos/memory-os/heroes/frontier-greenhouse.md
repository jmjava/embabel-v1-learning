# Frontier Greenhouse Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Memorize *next* Embabel APIs from 1.0 blogs and the User
Guide that this repo does not yet teach as lessons. Extract rules
only. Do not film until a lesson exists.

**Audience:** Learners who finished palaces 0–F and are reading
[`docs/READING.md`](../../../READING.md).

**Learning method:** Method of loci. One locus = one concept.

**Video style:** First-person glass greenhouse. Every locus is the
next organ of the stained-glass library-cart. No lettering in stills.
Teal light.

**Narration style:** Mnemonic first, then the rule. Say when a lesson
is still missing.

**Metaphor:** A greenhouse of unread books. The walking library-cart
*is* retrieval, skills, keys, memory, folding tools, parallel hands,
locks, and local seed-beds.

**Hero:** A stained-glass library-cart on walking greenhouse roots.
Search-hands → skinny skill-spines → racing visitor keyholes →
night-persistent shelf → folding nested doll → parallel arms →
lock-disc → local seed-bed with a spend-meter.

**Chassis:** a stained-glass library-cart on walking greenhouse roots

**Hero origin:** An empty stained-glass book standing on a library-cart.

**Camera:** first-person, eye-level, wide-angle, standing just inside the threshold, looking at the next organ of the same hero

**Hero scale:** the hero is architectural — it fills at least 40% of the frame and is the first thing the eye hits

**Metaphor material:** Teal glass, living shelves, bronze lock-discs.

**Core rule:** One locus = one concept. Last organ grew this one.
These organs are **study hooks for future lessons**, not current
guided tests.

------------------------------------------------------------------------

# 1. Building Overview

One floor, eight loci. Sources: Igor Dayen 1.0 features blog; Rod
Johnson agentic RAG; Jettro RAG walkthrough; hub RAG/MCP docs.

Status: **compile-clean, not filmed**. Do not run `memoryos plan` /
`images` / `build` until a matching lesson exists.

------------------------------------------------------------------------

# 2. Floor 1 --- Next Modules

## Learning Objective

Know the names and the one-line rules so a later lab has a palace to
land in. Do not pretend the java-demo contains these yet.

**Metaphor region:** The Living Stacks

**Hero stage:** Empty book becomes a cart of tools

**Metaphor entry:** Empty glass book

**Metaphor exit:** Local seed-bed + budget meter

## Scene 1 --- Search Hands: ToolishRag

**Visual:** The book grows hands that *choose* which shelf to pull:
nearness-hand, word-hand, pattern-hand, widen-hand. Nothing is stuffed
into the mouth automatically.

**Overlay:** `ToolishRag`

**Narration:**

> ToolishRag exposes search as typed tools. The model decides when
> and what to retrieve. Not a silent inject-then-generate pipeline.
> Lucene can back text + vector. Only tools the store implements
> appear.

**Recall cue:** Choosing hands → agentic RAG.

**Review question:** Who decides what to retrieve in ToolishRag?

**Review answer:** The model, via search tools — not a silent pipeline

**Metaphor part:** head-slot

**Hero from:** An empty stained-glass book standing on a library-cart.

**Hero now:** The same library-cart with four search-hands grown from the book.

**Hero silhouette:** Glass book-cart with four unlike search-hands.

**Hero tell:** Hands pick shelves; the mouth is not pre-stuffed.

**Hero shot:** Four search-hands choosing shelves.

**Hero becomes:** The same library-cart whose shelf-spines are skinny names, bodies off-shelf.


**Key distinction:** Agentic retrieval ≠ one-shot RAG.

------------------------------------------------------------------------

## Scene 2 --- Skinny Spines: Skills

**Visual:** Shelf spines show only short names. Full instruction-flesh
loads when a spine is pulled. Scripts in a side drawer become tools.

**Overlay:** `SKILL.md`

**Narration:**

> Agent Skills: YAML frontmatter plus Markdown body. The system
> prompt carries ~50–100 tokens of name/description. `activate`
> fetches full instructions. Load from disk or GitHub. Huge
> instruction libraries must not dump into every prompt.

**Recall cue:** Thin spines → lazy skills.

**Review question:** What sits in the system prompt for a skill?

**Review answer:** Name and description only until activated

**Metaphor part:** seeing-slot

**Hero from:** The same library-cart with four search-hands grown from the book.

**Hero now:** The same library-cart whose shelf-spines are skinny names, bodies off-shelf.

**Hero silhouette:** Search-handed cart before a wall of thin spines.

**Hero tell:** Pulling a spine fattens it; others stay thin.

**Hero shot:** Thin spines; one pulled spine fattening.

**Hero becomes:** The same library-cart with a ring of racing visitor keyholes.


**Key distinction:** Lazy load is a token strategy.

------------------------------------------------------------------------

## Scene 3 --- Visitor Keys: BYOK

**Visual:** Visitors present their own keys. A tiny probe-click
validates. Several keyholes race; the first that clicks wins. No
need to name the vendor first.

**Overlay:** `ByokFactory`

**Narration:**

> Bring Your Own Key: validate with a cheap probe, then mint an
> `LlmService`. `detectProvider()` can race factories. Product cost
> separates from inference cost when users supply keys.

**Recall cue:** Racing keyholes → BYOK.

**Review question:** What happens on an invalid user key?

**Review answer:** InvalidApiKeyException after a probe call

**Metaphor part:** hearing-slot

**Hero from:** The same library-cart whose shelf-spines are skinny names, bodies off-shelf.

**Hero now:** The same library-cart with a ring of racing visitor keyholes.

**Hero silhouette:** Spine-cart with many racing keyholes.

**Hero tell:** Tiny probe-click; the first keyhole that lights wins.

**Hero shot:** Racing keyholes, first one lighting.

**Hero becomes:** The same library-cart standing at a night-persistent stone shelf.


**Key distinction:** Probe then serve; do not trust the key string.

------------------------------------------------------------------------

## Scene 4 --- Night Shelf: Chat Store

**Visual:** A shelf that stays full after the lamps go out.
Conversations reload by id. An in-memory cloud evaporates; a stored
stone shelf does not.

**Overlay:** `ConversationFactory`

**Narration:**

> Chat store: `IN_MEMORY` vs `STORED`. Conversations for named
> participants reload across sessions. Needed when history must
> survive restarts or analytics.

**Recall cue:** Shelf after dark → persistent conversation.

**Review question:** What is the difference between IN_MEMORY and STORED?

**Review answer:** Ephemeral vs survives restart / reload by id

**Metaphor part:** scent-slot

**Hero from:** The same library-cart with a ring of racing visitor keyholes.

**Hero now:** The same library-cart standing at a night-persistent stone shelf.

**Hero silhouette:** Keyhole-cart beside a stone conversation shelf.

**Hero tell:** Lamps out; stone shelf still full; a cloud-shelf empty.

**Hero shot:** Stone shelf full after dark; cloud-shelf empty.

**Hero becomes:** The same library-cart holding a closed nested tool-doll.


**Key distinction:** Persistence is an SPI choice.

------------------------------------------------------------------------

## Scene 5 --- Nested Dolls: UnfoldingTool

**Visual:** A closed doll shows a short face. Inner wrenches appear
only after the cart opens it. Categories inside.

**Overlay:** `UnfoldingTool`

**Narration:**

> Progressive / folding tools hide inner tools until the facade is
> invoked. Short description always visible; details after commit.
> Use when a huge tool list hurts planning or tokens.

**Recall cue:** Nested doll → unfolding tools.

**Review question:** When do child tools become visible?

**Review answer:** After the LLM invokes the facade

**Metaphor part:** passage-slot

**Hero from:** The same library-cart standing at a night-persistent stone shelf.

**Hero now:** The same library-cart holding a closed nested tool-doll.

**Hero silhouette:** Shelf-cart with a nested doll of wrenches.

**Hero tell:** Doll closed at first; inner wrenches after it opens.

**Hero shot:** Closed nested doll in the cart's grip.

**Hero becomes:** The same library-cart with two arms swinging wrenches at once.


**Key distinction:** Disclosure is staged, not dumped.

------------------------------------------------------------------------

## Scene 6 --- Twin Arms: Parallel Tool Loop

**Visual:** Independent wrenches swing together. A timeout hourglass
per arm and one for the batch.

**Overlay:** `toolloop.type=parallel`

**Narration:**

> Independent tool calls in one LLM turn can run concurrently.
> YAML: `embabel.agent.platform.toolloop.type=parallel`. Use when
> fetches do not depend on each other.

**Recall cue:** Twin swinging arms → parallel tool loop.

**Review question:** When is parallel tool loop appropriate?

**Review answer:** Independent tool calls in one turn

**Metaphor part:** vault-slot

**Hero from:** The same library-cart holding a closed nested tool-doll.

**Hero now:** The same library-cart with two arms swinging wrenches at once.

**Hero silhouette:** Doll-cart with two parallel wrench-arms.

**Hero tell:** Two hourglasses: per-arm and batch.

**Hero shot:** Two arms swinging wrenches together.

**Hero becomes:** The same library-cart snapping a lock-disc onto remote doors.


**Key distinction:** Parallel is for independence, not for every call.

------------------------------------------------------------------------

## Scene 7 --- Lock Disc: @SecureAgentTool

**Visual:** The vault’s unused lock-disc snaps onto remote doors.
A token-coin must match before a remote wrench turns. Spell-light
on the lock.

**Overlay:** `@SecureAgentTool`

**Narration:**

> Spring Security SpEL on `@Action` methods exported as MCP tools.
> `secured` profile: JWT on `/sse/`, `/mcp/`, `/message/`. Method
> annotation wins over class. Remote tools need per-tool authority.

**Recall cue:** Lock-disc on MCP door → SecureAgentTool.

**Review question:** What enforces per-tool MCP access?

**Review answer:** @SecureAgentTool SpEL + JWT on MCP endpoints

**Metaphor part:** works-slot

**Hero from:** The same library-cart with two arms swinging wrenches at once.

**Hero now:** The same library-cart snapping a lock-disc onto remote doors.

**Hero silhouette:** Parallel-armed cart locking remote doors.

**Hero tell:** A token-coin at the disc; light on the wrench.

**Hero shot:** Lock-disc snapping onto a remote door.

**Hero becomes:** The same library-cart tending an embedding seed-bed with a spend-meter.


**Key distinction:** Export without security is an open vault.

------------------------------------------------------------------------

## Scene 8 --- Seed Bed: ONNX + Budgets

**Visual:** Seeds sprout embeddings in-process (no cloud watering
can). A spend-meter and a token-ruler grow from the bed.

**Overlay:** `ONNX / budget`

**Narration:**

> Local embeddings (`all-MiniLM-L6-v2`) run locally when enabled.
> Pair with invocation budgets and token estimates so calls die
> before they bankrupt you. Local + budgeted is the greenhouse
> moral.

**Recall cue:** Local seeds + meter → ONNX and budgets.

**Review question:** Why run embeddings on ONNX here?

**Review answer:** In-process, no external embedding API

**Metaphor part:** grasp-slot

**Hero from:** The same library-cart snapping a lock-disc onto remote doors.

**Hero now:** The same library-cart tending an embedding seed-bed with a spend-meter.

**Hero silhouette:** Lock-cart over a glowing seed-bed and meter.

**Hero tell:** No cloud watering can; meter and ruler on the soil.

**Hero shot:** Seed-bed and spend-meter filling the frame.

**Hero becomes:** The finished stained-glass library-cart on walking greenhouse roots, ready for recall.


**Key distinction:** Frontier names are hooks, not homework — yet.

### Floor 1 Recall Route

`ToolishRag → Skills → BYOK → Chat store → Unfold → Parallel → Secure MCP → ONNX`

------------------------------------------------------------------------

# 3. Final Active-Recall Sequence

End the video with locations only. Ask the learner to name the
concept, then reveal:

### Floor 1

**Frontier modules**

`ToolishRag → Skills → BYOK → Chat store → Unfold → Parallel → Secure MCP → ONNX`


# 4. Study Map

[`docs/READING.md`](../../../READING.md) Category F.
[`docs/SPEC_COMPLETENESS.md`](../../../SPEC_COMPLETENESS.md) next modules.
Do not add java-demo code from this palace until a lesson is scheduled.
