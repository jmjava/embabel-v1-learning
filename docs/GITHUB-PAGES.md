# GitHub Pages (Memory OS videos)

**Live URL (after the first successful deploy):** https://jmjava.github.io/embabel-v1-learning/

## How it is published

- Workflow: [`.github/workflows/pages.yml`](../.github/workflows/pages.yml)
- Deploy runs [`scripts/validate-pages-docs.sh`](../scripts/validate-pages-docs.sh)
  first. Broken palace Markdown front matter (`**Purpose:**` and the other
  memory-os header labels) fails the job before upload. This is not a film
  rebuild and does not run `memoryos validate` / gate on recordings.
  Published 8822fcb films fail current evaluate/gate (2.5s vs 12.5s
  think pause; no reverse walk). That mismatch is a checked record in
  `videos/memory-os/ingest-manifest.json`, not a recut.
- Artifact root is [`docs/`](.) so [`index.html`](index.html) is the homepage.
- Players embed MP4s from [`videos/recordings/`](videos/recordings/).
- Checkout uses `lfs: true`. Track new recordings with Git LFS:

  ```bash
  git lfs track 'docs/videos/recordings/*.mp4'
  git add .gitattributes docs/videos/recordings/*.mp4
  ```

- Cache-busting: the workflow appends `?v=<short-sha>` to every
  `videos/recordings/*.mp4` URL in the *deployed* `index.html`.

## Generate recordings

```bash
memoryos build docs/videos/memory-os/embabel-cheatsheet.palace.yaml --force
# build = narrate + images + fit + gate + render
memoryos render docs/videos/memory-os/embabel-cheatsheet.palace.yaml --floor floor-1 --force
memoryos render docs/videos/memory-os/embabel-cheatsheet.palace.yaml --floor floor-2 --force
./scripts/publish-memoryos-videos.sh
```

Raw engine output stays under `docs/videos/memory-os/build/` (gitignored).
The publish script is fail-closed: it copies only into `docs/videos/recordings/`,
rejects a symlink, file, or escaped destination (including
`MEMORYOS_PUBLISH_DEST`), requires [`index.html`](index.html) to embed those
three MP4 names, and checks destination size after copy. It does not rebuild
films. Pin stays `8822fcb`.

## If the site returns 404

1. Confirm **Actions → Deploy to GitHub Pages** succeeded on `main`.
2. Repo **Settings → Pages** source must be **GitHub Actions**.
3. The uploaded artifact root must contain `index.html`.
