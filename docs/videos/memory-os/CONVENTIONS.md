# Memory palace conventions (this repo)

Authoring contract for Memory OS palaces in `docs/videos/memory-os/`.
The engine schema lives in Memory OS:

- Palace Markdown (compile input): `docs/spec/palace-markdown.md`
- Engine YAML: `docs/spec/palace-spec.md`
- Method law: `docs/spec/memory-palace-method.md`

`memoryos compile` is the check. Campus scripts must compile with **0
warnings** and survive the chain/lettering audit below. Do not invent a
second mnemonic system.

These palaces are a **memorization study guide** for Embabel concepts,
not travel-plot retellings of the official cookbook.

The filmed Type-Foundry (`embabel-cheatsheet.md`) is **frozen**. Do not
re-film it. New campus palaces follow the labels below; the foundry
predates `Chassis` / `Camera` / `Hero scale` on purpose.

## Front matter (palace Markdown → `palace.yaml`)

Everything before the first floor heading is front matter. Bold labels
map as follows:

| Markdown label | Engine field |
|----------------|--------------|
| `#` title | palace title (`--id` overrides the slug) |
| `**Purpose:**` | `palace.purpose` |
| `**Audience:**` | `palace.audience` |
| `**Learning method:**` | `palace.learning_method` |
| `**Video style:**` | `palace.style.visual` |
| `**Narration style:**` | `palace.style.narration` |
| `**Metaphor:**` | `palace.metaphor.throughline` |
| `**Hero:**` | `palace.metaphor.hero` (organ map in physical words) |
| `**Chassis:**` | `palace.metaphor.chassis` |
| `**Hero origin:**` | `palace.metaphor.hero_origin` |
| `**Metaphor material:**` | `palace.metaphor.material` |
| `**Camera:**` | `palace.metaphor.camera` |
| `**Hero scale:**` | `palace.metaphor.hero_scale` |

Required campus values:

- **Chassis:** one known object family — animal, vehicle, building, or
  machine whose parts a learner can already draw. Unique per palace.
  No blob, no stock watchdog/lion, no reused creature across palaces.
- **Camera:** `first-person, eye-level, wide-angle, standing just inside
  the threshold, looking at the next organ of the same hero`
- **Hero scale:** `the hero is architectural — it fills at least 40% of
  the frame and is the first thing the eye hits`
- **Hero** and **Hero origin** must be paintable: physical objects, no
  digits, identifiers, quoted labels, or shouted acronyms.

## Floors

A level-1 heading containing `Floor <n>` starts a floor. Leading section
numbers are ignored. After the separator (`---`, `—`, `:` or `-`) is the
floor title.

Inside the floor, before any scene:

| Markdown | Engine field |
|----------|--------------|
| `## Learning Objective` | `floor.objective` |
| heading containing `Recall Route` | `floor.recall_route` |
| `**Metaphor region:**` | `floor.metaphor.region` |
| `**Hero stage:**` | `floor.metaphor.hero_stage` |
| `**Metaphor entry:**` | `floor.metaphor.entry` |
| `**Metaphor exit:**` | `floor.metaphor.exit` |
| `**Frozen:** yes` | locks every locus on that floor |

## Scenes (loci)

`## Scene <n> --- Location: Concept` — split on the **last** `:`.

| Markdown | Locus field |
|----------|-------------|
| `**Visual:**` | `visual` — one exaggerated scene; same hero; **no lettering** |
| `**Overlay:**` `` `code` `` | `overlay` — API names live here; engine stamps them |
| `**Narration:**` + `>` | `narration` — mnemonic first, then the Embabel rule |
| `**Recall cue:**` | `recall_cue` |
| `**Review question:**` / `**Review answer:**` | Q/A |
| `**Key distinction:**` | mix-up this locus prevents |
| `**Metaphor part:**` | body-slot name from the walk below |
| `**Hero from:**` | exact previous `Hero now` (locus 1 = Hero origin) |
| `**Hero now:**` | this organ |
| `**Hero becomes:**` | exact next `Hero now` (last locus = finished chassis) |
| `**Hero silhouette:**` | one-line shape |
| `**Hero tell:**` | the one physical detail that *is* the concept |
| `**Hero shot:**` | how to photograph this organ |

Do **not** author `must_show` or `stamp_text` in Markdown.
`memoryos enrich` writes the 3–6 prop contract (hero form first, tell
second). Lettering in that contract is routed to `stamp_text`.

## Hero chain (exact strings)

Speakable without looking at the page:

1. Locus 1 `Hero from` **equals** palace `Hero origin`.
2. Locus N `Hero from` **equals** locus N−1 `Hero now`.
3. Locus N−1 `Hero becomes` **equals** locus N `Hero now`.
4. Last `Hero becomes` is the finished chassis: `The finished <chassis
   without leading article>, ready for recall.`
5. Traces of the previous organ stay visible on the new form.

`memoryos compile` does **not** enforce this. A chain break is an
authoring bug even when compile reports 0 warnings.

## Body-slot walk

Slots are *places*, not a request for textbook anatomy:

```
head-slot → seeing-slot → hearing-slot → scent-slot → passage-slot
         → vault-slot → works-slot → grasp-slot → stand-slot → exit-slot
```

Floor 1 takes the first six. Floor 2 continues. Palaces with 6–8 loci
use the first N slots. Loci past ten are finishing organs on the
**same** body, not a second creature.

Each organ must be absurd **and be the concept**. A random weird object
with no link to the idea has failed. Never a normal eye, ear, heart, or
limb. Causal: the last organ physically enables this one.

## No lettering in stills

Image models must never be asked to paint glyphs. `memoryos plan`
rejects `hero_now` / `hero_tell` that need lettering (digits, CamelCase,
snake_case, `@annotations`, shouted acronyms, quotes). Keep the same
rule on **Visual**, **Hero**, **Hero origin**, **Hero silhouette**, and
**Hero shot**.

API names belong in **Overlay** and in **Narration**. The engine stamps
overlays. A still that paints readable text fails fit.

## Recall section

A level-1 heading containing `Active-Recall` starts recall. Per floor:

```markdown
### Floor 1

**Label**

`route → of → loci`
```

That becomes `recall.floors[]`. Other top-level sections (building
overview, study map) are preserved under `extras` and are not filmed.

## One palace, one hero, one walk

| Rule | Why |
|------|-----|
| One palace = one metaphor building | The building is the category |
| One floor = one subcategory | Recall route stays short |
| One locus = one concept | One image, one retrieval hook |
| One chassis per palace | Paintable character from start to finish |
| 6–10 loci per palace | Classic size; 12 only as two floors of six |

## Shared campus material

All palaces sit in one **Foundry City**:

- **1.0 regions:** warm bronze, coal-orange light, glass type-pipes
- **1.5 / frontier regions:** cooler teal light, glass breath, mold-mouths
- Heroes are **siblings** of the bronze type-ingot, not a new art style

## Compile vs film

Campus Markdown is the source of truth. Check with:

```bash
memoryos compile docs/videos/memory-os/heroes/<palace>.md \
  --id embabel-<palace> -o /tmp/embabel-<palace>.palace.yaml
memoryos validate /tmp/embabel-<palace>.palace.yaml
```

Do **not** commit compile-only YAML (no `must_show` yet). The filmed
foundry YAML is planned + enriched; leave it alone.

Do **not** run `plan` / `enrich` / `images` / `build` on campus palaces
until a film pass is scheduled. The Type-Foundry floors stay frozen.

## Core rule

> One locus = one concept. One memorable image = one retrieval hook.
> Every locus is the next metamorphosis of the same known chassis.
