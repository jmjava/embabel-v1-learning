# Memory OS plan (cheat-sheet palace)

Memory OS `70bc124` is installed (`plan` + evolving hero). Authoring lives in
[`embabel-cheatsheet.md`](embabel-cheatsheet.md). This table is the locus map.

## Goal

A recall palace for **learning Embabel**, not a filmed walkthrough of the
official cookbook travel recipes. Loci come from
[`docs/CHEATSHEET.md`](../../CHEATSHEET.md) (concepts extracted from the
[User Guide](https://docs.embabel.com/embabel-agent/guide/1.5.0-SNAPSHOT/)
and [Cookbook 1.5](https://docs.embabel.com/embabel-cookbook/1.5.0/)).

The existing [`embabel-cookbook-15.md`](embabel-cookbook-15.md) / compiled
YAML stay as a reference draft from the previous cookbook-chapter layout.
They should be **replaced**, not re-rendered, after the Memory OS upgrade.

## Palace shape (draft)

Two floors, twelve loci, one concept per room. Same method-of-loci rules as
before: exaggerated visual, one overlay label, one spoken rule.

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

## Authoring steps (after Memory OS upgrades)

1. Confirm the new Memory OS compile/validate CLI still accepts palace Markdown.
2. Rewrite authoring Markdown from this table (new file, e.g.
   `embabel-cheatsheet.palace.md`) — do **not** copy cookbook chapter prose.
3. `memoryos compile` + `validate` only. No images, TTS, or MP4 until the
   upgraded renderer is confirmed.
4. Then generate assets and publish via [`docs/GITHUB-PAGES.md`](../../GITHUB-PAGES.md).

## Out of scope now

- Re-filming `docs/videos/recordings/*.mp4`
- Live OpenAI image/TTS calls
- Docgen narration rewrites (optional later; cheat sheet is the source)

## Environment note

Cloud Agent install still needs `BROAD_REPO_TOKEN` for private `memory-os`.
`OPENAI_API_KEY` / `CURSOR_API_KEY` names in this environment are swapped;
the install wrapper remaps a `crsr_` OpenAI slot to an `sk-` Cursor slot.
That matters only when generation resumes.
