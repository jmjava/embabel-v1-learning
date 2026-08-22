# Memory OS palace (cheat sheet)

Primary renderer: [jmjava/memory-os](https://github.com/jmjava/memory-os)
(private; Cloud Agents use `BROAD_REPO_TOKEN` to install the CLI).
Installed HEAD for this generation: `70bc124` (evolving-hero `plan` command).

Study source: [`docs/CHEATSHEET.md`](../../CHEATSHEET.md).
This is **not** a filmed walkthrough of cookbook travel recipes.

## Bundle

| File | Role |
|------|------|
| `embabel-cheatsheet.md` | Palace Markdown (authoring) |
| `embabel-cheatsheet.palace.yaml` | Compiled + planned + enriched engine spec |
| `ingest-manifest.json` | Locus ↔ lesson ↔ cheat-sheet map |
| `PLAN.md` | Locus table used to author the palace |

Generated assets stay local and are gitignored: `images/`, `audio/`, `build/`.

## Generate assets

```bash
memoryos compile docs/videos/memory-os/embabel-cheatsheet.md \
  --id embabel-cheatsheet \
  -o docs/videos/memory-os/embabel-cheatsheet.palace.yaml
memoryos validate docs/videos/memory-os/embabel-cheatsheet.palace.yaml
memoryos plan    docs/videos/memory-os/embabel-cheatsheet.palace.yaml
memoryos enrich  docs/videos/memory-os/embabel-cheatsheet.palace.yaml --target-seconds 28
memoryos images  docs/videos/memory-os/embabel-cheatsheet.palace.yaml --dry-run
memoryos narrate docs/videos/memory-os/embabel-cheatsheet.palace.yaml --dry-run
memoryos build   docs/videos/memory-os/embabel-cheatsheet.palace.yaml
```

One locus stays on screen until its narration finishes (minimum 15s).
Do not vendor memory-os into this repo; refresh the CLI from git during install.

GitHub Pages player: https://jmjava.github.io/embabel-v1-learning/

After a successful `memoryos build`, run `./scripts/publish-memoryos-videos.sh` and
commit the LFS-tracked MP4s under `docs/videos/recordings/`.
