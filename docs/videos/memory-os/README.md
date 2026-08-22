# Memory OS ingest

Target repo: [github.com/jmjava/memory-os](https://github.com/jmjava/memory-os)

This learning branch could not clone that repository from the current GitHub token
(it is missing or private). The contract below is the integration surface so a
follow-up can wire the renderer without rewriting scripts.

## What Memory OS should receive

Each cookbook chapter becomes one **memory segment**:

| Field | Meaning |
|-------|---------|
| `id` | Stable segment id (`00`–`12`) |
| `title` | Human title |
| `cookbook_slug` | Official cookbook chapter slug |
| `lesson` | Lesson number in this repo |
| `narration_path` | Markdown spoken script |
| `transcript` | Same text, ready for embedding |
| `source_url` | Official cookbook HTML anchor |
| `code_entry` | Fully qualified teaching class |
| `video_path` | Optional rendered MP4 once docgen/memory-os produces it |

`ingest-manifest.json` is the machine-readable catalog.

## Suggested ingest flow

1. Read `ingest-manifest.json`.
2. Load each `narration_path` as the transcript.
3. If `video_path` exists, attach the MP4; otherwise queue render via `docgen`
   or Memory OS’s own compositor.
4. Store embeddings of title + transcript + `code_entry` so later agents can
   recall “how thinking() works in Embabel 1.5”.

## Render without Memory OS

Use the sibling tool [documentation-generator](https://github.com/jmjava/documentation-generator)
and the bundle in `docs/demos/`. The manifest `renderer` field names both tools.
