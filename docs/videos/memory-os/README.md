# Memory OS palace (cheat sheet)

Primary renderer: [jmjava/memory-os](https://github.com/jmjava/memory-os)
(private; Cloud Agents use `BROAD_REPO_TOKEN` to install the CLI).
Installed HEAD for this generation: `8822fcb` (PR #10 incremental freeze
on top of the PR #9 scene contract: `must_show` + `stamp_text`, then
narrate → images → `fit` → gate).

Study source: [`docs/CHEATSHEET.md`](../../CHEATSHEET.md).
This is **not** a filmed walkthrough of cookbook travel recipes.

## Study campus (memorize every key concept)

Category / subcategory map and hero-evolution palaces for Memory OS:

- [`STUDY_CAMPUS.md`](STUDY_CAMPUS.md) — taxonomy + recall routes
- [`CONVENTIONS.md`](CONVENTIONS.md) — palace authoring contract
- [`heroes/`](heroes/) — one evolving hero per category (compile-clean Markdown; not filmed)
- [`../../READING.md`](../../READING.md) — blogs mapped onto those categories

Filmed track remains the Type-Foundry cheat-sheet palace below.

## Bundle

| File | Role |
|------|------|
| `embabel-cheatsheet.md` | Palace Markdown (authoring) |
| `embabel-cheatsheet.palace.yaml` | Compiled + planned + enriched engine spec |
| `ingest-manifest.json` | Locus ↔ lesson ↔ cheat-sheet map; `campus_palaces` stubs |
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
memoryos freeze  docs/videos/memory-os/embabel-cheatsheet.palace.yaml floor-1 floor-2
```

Build order is narrate → images → fit → gate → render.
One locus stays on screen until its narration finishes (minimum 30s).
Do not vendor memory-os into this repo; refresh the CLI from git during install.

GitHub Pages player: https://jmjava.github.io/embabel-v1-learning/

After a successful `memoryos build`, run `./scripts/publish-memoryos-videos.sh`
(fail-closed: destination must be `docs/videos/recordings/`) and commit the
LFS-tracked MP4s. The script does not rebuild films. Pin stays `8822fcb`.

Published cheat-sheet films fail current `memoryos evaluate` / `gate`
checks: think pauses are 2.5s (8822fcb default) vs the current 12.5s
method hold, and there is no reverse walk. That gap is recorded in
`ingest-manifest.json` (`published_film_gate`) and checked by
`scripts/check-published-film-gate-record.sh`. Do not rebuild films to
clear it. Leftover #10 locks the consumer pin at `8822fcb`:
`scripts/check-memoryos-pin-lock.sh` goes red if the pin changes without
an explicit leftover under `leftovers/memory-os/`.
