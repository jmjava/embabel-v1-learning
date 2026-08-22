# Memory OS palace (paused)

Primary renderer: [jmjava/memory-os](https://github.com/jmjava/memory-os)
(private; Cloud Agents use `BROAD_REPO_TOKEN` to install the CLI).

**Memory OS is upgrading. Do not generate videos yet.**
Plan: [`PLAN.md`](PLAN.md). Study source: [`docs/CHEATSHEET.md`](../../CHEATSHEET.md).

## Bundle

| File | Role |
|------|------|
| `embabel-cookbook-15.md` | Palace Markdown (authoring) |
| `embabel-cookbook-15.palace.yaml` | Compiled engine spec (`memoryos compile`) |
| `ingest-manifest.json` | Chapter ↔ lesson ↔ locus map |

Generated assets stay local and are gitignored: `images/`, `audio/`, `build/`.

## Generate assets

```bash
# already on PATH in the reusable Cloud Agent environment
memoryos compile docs/videos/memory-os/embabel-cookbook-15.md \
  --id embabel-cookbook-15 \
  -o docs/videos/memory-os/embabel-cookbook-15.palace.yaml
memoryos validate docs/videos/memory-os/embabel-cookbook-15.palace.yaml

# live OpenAI. If OPENAI_API_KEY is a Cursor crsr_ value, the memoryos
# wrapper uses CURSOR_API_KEY when that value is an sk- project key.
memoryos images  docs/videos/memory-os/embabel-cookbook-15.palace.yaml --dry-run
memoryos narrate docs/videos/memory-os/embabel-cookbook-15.palace.yaml --dry-run
memoryos build   docs/videos/memory-os/embabel-cookbook-15.palace.yaml --floor floor-1
```

One locus stays on screen until its narration finishes (minimum 30s).
Do not vendor memory-os into this repo; refresh the CLI from git during install.

GitHub Pages player: https://jmjava.github.io/embabel-v1-learning/

After a successful `memoryos build`, run `./scripts/publish-memoryos-videos.sh` and
commit the LFS-tracked MP4s under `docs/videos/recordings/`.
