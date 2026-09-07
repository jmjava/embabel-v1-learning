# Memory palace conventions (this repo)

Authoring contract for Memory OS palaces in `docs/videos/memory-os/`.
The filmed cheat-sheet palace (`embabel-cheatsheet.md`) is the reference
implementation. New study palaces must match this shape so a later
`memoryos compile` can ingest them without a rewrite.

These palaces are a **memorization study guide** for Embabel concepts,
not travel-plot retellings of the official cookbook.

## One palace, one hero, one walk

| Rule | Why |
|------|-----|
| One palace = one metaphor building | The building is the category |
| One floor = one subcategory | Recall route stays short |
| One locus = one concept | Method of loci: one image, one retrieval hook |
| One hero metamorphoses at every locus | Never a reused generic hallway; never a new disconnected prop |
| `Hero becomes` of locus N **is** `Hero from` of locus N+1 | The chain must be speakable without looking at the page |
| Each organ **is** the concept | Absurd, physical, guessable |
| No lettering in stills | Engine stamps `overlay` / `stamp_text`; stills stay pictorial |
| 6–10 loci per palace | Classic palace size; 12 only when two floors of six |

## Body-slot walk (guessable organs)

Use this order. Floor 1 takes the first six. Floor 2 continues with the
rest. If a palace has more than ten loci, loci 11–12 are **finishing
organs on the completed body**, not a second creature.

```
head-slot → seeing-slot → hearing-slot → scent-slot → passage-slot
         → vault-slot → works-slot → grasp-slot → stand-slot → exit-slot
```

The existing type-foundry palace walked this order on floor 1
(pipe-head → compass-eye → stamp/quill hands → wrist toolbox → hanging
crates → hat stack).

## Required fields per locus

Match the cheat-sheet Markdown so compile/plan/enrich stay stable:

| Field | Content |
|-------|---------|
| **Visual** | One exaggerated scene. Same hero. No painted letters. |
| **Overlay** | Short API / rule stamp (engine, not in the still) |
| **Narration** | Mnemonic first, then the Embabel rule. Calm instructor. |
| **Recall cue** | `image → rule` in one line |
| **Review Q/A** | One question, one answer |
| **Metaphor part** | Body slot name from the walk above |
| **Hero from** | Exact previous `Hero now` |
| **Hero now** | This organ |
| **Hero silhouette** | One-line shape for image prompts |
| **Hero tell** | The one detail that proves this is not the last room |
| **Hero becomes** | Exact next `Hero from` |
| **Key distinction** | The mix-up this locus prevents |

Palace header also needs: **Purpose, Audience, Metaphor, Hero, Hero
origin, Metaphor material, Learning objective per floor, Recall route.**

## Causal evolution (must make sense)

An organ may grow only if the previous organ **physically enables it**:

- Pipes can grow an eye (the lead pipe becomes a compass).
- An eye can grow hands (the creature now *does* work).
- A writing hand can bolt on a toolbox (tools attach to that call).
- A toolbox can hang crates (bindings are named objects on the same body).

Illegal: a new mascot, a poster, a second building, a floating logo, a
hallway with a different statue. If the learner cannot say “the last
organ grew this one,” rewrite the locus.

## Shared campus material

All palaces in this repo sit in one **Foundry City**:

- **1.0 regions:** warm bronze, coal-orange light, glass type-pipes
- **1.5 / frontier regions:** cooler teal light, glass breath, mold-mouths
- Heroes are **siblings** of the bronze type-ingot, not a new art style

That way walking the campus still feels like one memory world.

## Core rule

> One locus = one concept. One memorable image = one retrieval hook.
> Every locus is the next metamorphosis of the same hero.
