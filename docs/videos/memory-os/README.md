# Memory OS palace (cheat sheet)

Primary renderer: [jmjava/memory-os](https://github.com/jmjava/memory-os)
(private; Cloud Agents use `BROAD_REPO_TOKEN` to install the CLI).
Installed HEAD for this generation: `d551e69` (PR #9 scene contract:
`must_show` + `stamp_text`, then narrate → images → `fit` → gate).

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
memoryos plan    docs/videos/memory-os/embabel-cheatsheet.palace.yaml --force
memoryos enrich  docs/videos/memory-os/embabel-cheatsheet.palace.yaml --force --target-seconds 30
memoryos narrate docs/videos/memory-os/embabel-cheatsheet.palace.yaml --dry-run
memoryos images  docs/videos/memory-os/embabel-cheatsheet.palace.yaml --dry-run
memoryos fit     docs/videos/memory-os/embabel-cheatsheet.palace.yaml --dry-run
memoryos gate    docs/videos/memory-os/embabel-cheatsheet.palace.yaml --spec-only
memoryos build   docs/videos/memory-os/embabel-cheatsheet.palace.yaml --force
```

Build order is narrate → images → fit → gate → render.
One locus stays on screen until its narration finishes (minimum 30s).
Do not vendor memory-os into this repo; refresh the CLI from git during install.

GitHub Pages player: https://jmjava.github.io/embabel-v1-learning/

After a successful `memoryos build`, run `./scripts/publish-memoryos-videos.sh` and
commit the LFS-tracked MP4s under `docs/videos/recordings/`.
