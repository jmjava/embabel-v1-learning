# GitHub Pages (Memory OS videos)

**Live URL (after the first successful deploy):** https://jmjava.github.io/embabel-v1-learning/

## How it is published

- Workflow: [`.github/workflows/pages.yml`](../.github/workflows/pages.yml)
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
memoryos build docs/videos/memory-os/embabel-cheatsheet.palace.yaml
memoryos render docs/videos/memory-os/embabel-cheatsheet.palace.yaml --floor floor-1
memoryos render docs/videos/memory-os/embabel-cheatsheet.palace.yaml --floor floor-2
./scripts/publish-memoryos-videos.sh
```

Raw engine output stays under `docs/videos/memory-os/build/` (gitignored).
The publish script copies the three palace films into `docs/videos/recordings/`.

## If the site returns 404

1. Confirm **Actions → Deploy to GitHub Pages** succeeded on `main`.
2. Repo **Settings → Pages** source must be **GitHub Actions**.
3. The uploaded artifact root must contain `index.html`.
