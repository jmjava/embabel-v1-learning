# Memory OS plan (cheat-sheet palace)

Memory OS `4d694ac` is installed (`plan` creature + body-slot walk,
`enrich` speaks that plan, `ground` checks stills against the script,
`gate` + Q→pause→A + whole-animal review).
Authoring lives in [`embabel-cheatsheet.md`](embabel-cheatsheet.md).

## Goal

A recall palace for **learning Embabel**, not a filmed walkthrough of the
official cookbook travel recipes. Loci come from
[`docs/CHEATSHEET.md`](../../CHEATSHEET.md) (concepts extracted from the
[User Guide](https://docs.embabel.com/embabel-agent/guide/1.5.0-SNAPSHOT/)
and [Cookbook 1.5](https://docs.embabel.com/embabel-cookbook/1.5.0/)).

## Palace shape

Two floors, twelve loci, one concept per room. One bronze type-ingot
creature walks guessable body slots (head-slot → seeing-slot → …).
Each organ is absurd and *is* the concept. Overlay labels and Q/A are
stamped by the engine — stills must not paint lettering.

### Floor 1 — 1.0 mental model (always true)

| Locus | Cheat-sheet hook | Visual mnemonic (draft) |
|-------|------------------|-------------------------|
| 1 | Types are the wiring | Colored pipes labeled with Java types, no `if` signs |
| 2 | Replan after every action | OODA compass that spins after each door |
| 3 | Mix code + LLM | Split workbench: Spring stamp vs glowing quill |
| 4 | Tools attach per call | Toolbox bolted onto one prompt desk, not the building |
| 5 | Named bindings | Two identical crates; only the labeled one opens |
| 6 | Planner choice | Four hats: cheap path / greedy value / gather+stop / LLM concierge |

### Floor 2 — PromptRunner + 1.5 extras

| Locus | Cheat-sheet hook | Visual mnemonic (draft) |
|-------|------------------|-------------------------|
| 7 | Typed `createObject` | Mold that only accepts a record-shaped ingot |
| 8 | `createObjectIfPossible` → `null` → replan | Soft clay that slumps into a `null` hole |
| 9 | Messages + contributors | Stack of labeled envelopes (`System` / `User`) |
| 10 | Thinking traces **1.5** | Glass skull with `<decision_reasoning>` ticker |
| 11 | Streaming objects **1.5** | Conveyor of JSON bricks becoming records |
| 12 | Tool inspectors **1.5** | Clipboard watching each wrench swing |

## Status

Rebuilding on memory-os `4d694ac`: `embabel-cheatsheet.md` → plan →
enrich (speak the plan) → narrate → images → `ground` → `gate` →
Q/A + whole-animal-stamped render →
`docs/videos/recordings/embabel-cheatsheet*.mp4`.

## Out of scope

- Re-filming cookbook travel rooms
- Docgen narration rewrites (optional later; cheat sheet is the source)

## Environment note

Cloud Agent install still needs `BROAD_REPO_TOKEN` for private `memory-os`.
`OPENAI_API_KEY` / `CURSOR_API_KEY` names in this environment are swapped;
the install wrapper remaps a `crsr_` OpenAI slot to an `sk-` Cursor slot.
That matters only if those names are still swapped.
