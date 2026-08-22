# Cookbook 1.5 video bundle

Narrated explainers for the official Embabel Cookbook 1.5 chapters.

## Tools

| Tool | Role |
|------|------|
| [documentation-generator](https://github.com/jmjava/documentation-generator) (`docgen`) | TTS + Manim + ffmpeg composition |
| [memory-os](https://github.com/jmjava/memory-os) | Target ingest for the finished videos and transcripts |

`docgen` is an external CLI. Do **not** vendor it into this repo.

```bash
pip install 'docgen @ git+https://github.com/jmjava/documentation-generator.git'
```

## Layout

```
docs/demos/
  docgen.yaml              # bundle config
  narration/               # spoken scripts, one file per chapter
  hints/                   # owner hints for scene-spec generation
docs/videos/memory-os/
  ingest-manifest.json     # Memory OS ingest contract
```

## Generate (when keys and docgen extras are available)

```bash
cd docs/demos
docgen timestamps --engine local   # after TTS
docgen scene-spec-generate --all
docgen generate-all                # needs OPENAI_API_KEY for TTS / images
```

This environment does not bake rendered MP4s. Scripts and the ingest manifest
are the source of truth so Memory OS or a later agent can render offline.
