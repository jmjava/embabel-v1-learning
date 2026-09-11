---
leftover: 10
action: lock
sha: 8822fcb5ae813f491daeff7d5f37fce7ff10d213
prefix: 8822fcb
films_rebuilt: false
---

# Leftover #10 — lock consumer pin at 8822fcb

Do not bump `8822fcb` from a memory-os leftover. Films are not rebuilt.

A pin change requires a new leftover under `leftovers/memory-os/` with
`action: bump` that names the new SHA, plus `pin_lock.authorizing_leftover`
on `docs/videos/memory-os/ingest-manifest.json`. Changing the pin without
that leftover must go red.
