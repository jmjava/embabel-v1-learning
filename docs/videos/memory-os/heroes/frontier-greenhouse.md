# Frontier Greenhouse Memory Palace

## Video Generation & Study Script Specification

**Purpose:** Memorize *next* Embabel APIs from 1.0 blogs and the User
Guide that this repo does not yet teach as lessons. Extract rules
only. Do not film until a lesson exists.

**Audience:** Learners who finished palaces 0–F and are reading
[`docs/READING.md`](../../../READING.md).

**Learning method:** Method of loci. One locus = one concept.

**Video style:** First-person glass greenhouse. Every locus is the
next organ of the Glass-Librarian. No lettering in stills. Teal light.

**Narration style:** Mnemonic first, then the rule. Say when a lesson
is still missing.

**Metaphor:** A greenhouse of unread books. The librarian *is*
retrieval, skills, keys, memory, folding tools, parallel hands,
locks, and local seed-beds.

**Hero:** A glass librarian grown from an empty book. Search-hands →
skinny skill-spines → user-key ring → persistent shelf → folding
nested tools → parallel arms → lock-disc → ONNX seed-bed with a
budget meter.

**Hero origin:** An empty glass book on a pedestal — knowledge not
yet retrievable.

**Metaphor material:** Teal glass, living shelves, bronze lock-discs.

**Core rule:** One locus = one concept. Last organ grew this one.
These organs are **study hooks for future lessons**, not current
guided tests.

------------------------------------------------------------------------

# 1. Building Overview

One floor, eight loci. Sources: Igor Dayen 1.0 features blog; Rod
Johnson agentic RAG; Jettro RAG walkthrough; hub RAG/MCP docs.

Status: **authoring only**. No `memoryos compile` until SPEC
completeness grows matching demos.

------------------------------------------------------------------------

# 2. Floor 1 --- Next Modules

## Learning Objective

Know the names and the one-line rules so a later lab has a palace to
land in. Do not pretend the java-demo contains these yet.

**Metaphor region:** The Living Stacks

**Hero stage:** Empty book becomes a librarian of tools

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

**Hero from:** An empty glass book on a pedestal.

**Hero now:** The same book-librarian with four search-hands.

**Hero silhouette:** Glass book with four unlike search-hands

**Hero tell:** Hands pick shelves; mouth is not pre-stuffed

**Hero becomes:** Thin skill-spines with names only, bodies off-shelf.

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

**Hero from:** Librarian with search-hands.

**Hero now:** The same librarian whose shelf-spines are skinny names.

**Hero silhouette:** Search-handed librarian before a wall of thin spines

**Hero tell:** Pulling a spine fattens it; others stay thin

**Hero becomes:** A ring of visitor keys that probe before they work.

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

**Hero from:** Librarian with skinny skill-spines.

**Hero now:** The same librarian with a ring of racing visitor keyholes.

**Hero silhouette:** Spine-librarian with many racing keyholes

**Hero tell:** Tiny probe-click; first keyhole that lights wins

**Hero becomes:** A shelf that remembers conversations after dark.

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

**Hero from:** Librarian with visitor keyholes.

**Hero now:** The same librarian standing at a night-persistent shelf.

**Hero silhouette:** Keyhole-librarian beside a stone conversation shelf

**Hero tell:** Lamps out; stone shelf still full; a cloud-shelf empty

**Hero becomes:** Nested tool-dolls that only open when asked.

**Key distinction:** Persistence is an SPI choice.

------------------------------------------------------------------------

## Scene 5 --- Nested Dolls: UnfoldingTool

**Visual:** A closed doll shows a short face. Inner wrenches appear
only after the librarian opens it. Categories inside.

**Overlay:** `UnfoldingTool`

**Narration:**

> Progressive / folding tools hide inner tools until the facade is
> invoked. Short description always visible; details after commit.
> Use when a huge tool list hurts planning or tokens.

**Recall cue:** Nested doll → unfolding tools.

**Review question:** When do child tools become visible?

**Review answer:** After the LLM invokes the facade

**Metaphor part:** passage-slot

**Hero from:** Librarian at the night shelf.

**Hero now:** The same librarian holding a closed nested tool-doll.

**Hero silhouette:** Shelf-librarian with a nested doll of wrenches

**Hero tell:** Doll closed at first; inner wrenches after it opens

**Hero becomes:** Two arms that work at the same time.

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

**Hero from:** Librarian with a nested tool-doll.

**Hero now:** The same librarian with two arms swinging wrenches at once.

**Hero silhouette:** Doll-librarian with two parallel wrench-arms

**Hero tell:** Two hourglasses: per-arm and batch

**Hero becomes:** The unused lock-disc from the vault snaps onto MCP
doors.

**Key distinction:** Parallel is for independence, not for every call.

------------------------------------------------------------------------

## Scene 7 --- Lock Disc: @SecureAgentTool

**Visual:** The vault’s unused lock-disc snaps onto MCP doors. A
JWT-shaped coin must match before a remote wrench turns. SpEL light.

**Overlay:** `@SecureAgentTool`

**Narration:**

> Spring Security SpEL on `@Action` methods exported as MCP tools.
> `secured` profile: JWT on `/sse/`, `/mcp/`, `/message/`. Method
> annotation wins over class. Remote tools need per-tool authority.

**Recall cue:** Lock-disc on MCP door → SecureAgentTool.

**Review question:** What enforces per-tool MCP access?

**Review answer:** @SecureAgentTool SpEL + JWT on MCP endpoints

**Metaphor part:** works-slot

**Hero from:** Librarian with parallel wrench-arms.

**Hero now:** The same librarian snapping a lock-disc onto MCP doors.

**Hero silhouette:** Parallel-armed librarian locking MCP doors

**Hero tell:** JWT-coin at the disc; SpEL light on the wrench

**Hero becomes:** A soil bed of local embedding seeds plus a spend
meter.

**Key distinction:** Export without security is an open vault.

------------------------------------------------------------------------

## Scene 8 --- Seed Bed: ONNX + Budgets

**Visual:** Seeds sprout embeddings in-process (no cloud watering
can). A spend-meter and a token-ruler grow from the bed.

**Overlay:** `ONNX / budget`

**Narration:**

> ONNX embeddings (`all-MiniLM-L6-v2`) run locally when enabled.
> Pair with invocation budgets and token estimates so calls die
> before they bankrupt you. Local + budgeted is the greenhouse
> moral.

**Recall cue:** Local seeds + meter → ONNX and budgets.

**Review question:** Why run embeddings on ONNX here?

**Review answer:** In-process, no external embedding API

**Metaphor part:** grasp-slot

**Hero from:** Librarian locking MCP doors.

**Hero now:** The same librarian tending an ONNX seed-bed with a
spend-meter.

**Hero silhouette:** Lock-librarian over a glowing seed-bed and meter

**Hero tell:** No cloud watering can; meter and ruler on the soil

**Hero becomes:** The finished greenhouse librarian, waiting for
real lessons.

**Key distinction:** Frontier names are hooks, not homework — yet.

### Floor 1 Recall Route

`ToolishRag → Skills → BYOK → Chat store → Unfold → Parallel → Secure MCP → ONNX`

------------------------------------------------------------------------

# 3. Final Active-Recall Sequence

Hands, spines, keys, night shelf, dolls, twin arms, lock, seeds.

------------------------------------------------------------------------

# 4. Study Map

[`docs/READING.md`](../../../READING.md) Category F.
[`docs/SPEC_COMPLETENESS.md`](../../../SPEC_COMPLETENESS.md) next modules.
Do not add java-demo code from this palace until a lesson is scheduled.
