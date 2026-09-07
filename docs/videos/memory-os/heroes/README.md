# Heroes index

One evolving hero per palace, on a **known chassis**. Read
[`../CONVENTIONS.md`](../CONVENTIONS.md) before editing a chain.
`Hero now` of locus N must equal `Hero from` of locus N+1, and
`Hero becomes` of locus N must equal `Hero now` of locus N+1.

Compile-check (0 warnings required):

```bash
memoryos compile docs/videos/memory-os/heroes/<palace>.md \
  --id embabel-<palace> -o /tmp/embabel-<palace>.palace.yaml
memoryos validate /tmp/embabel-<palace>.palace.yaml
```

Do not `plan` / `enrich` / `images` / `build` until a film pass is
scheduled. Do not re-film the Type-Foundry.

| File | Chassis | Origin | Last organ |
|------|---------|--------|------------|
| [planning-citadel.md](planning-citadel.md) | bronze compass-serpent | Compass that crawled off the type-ingot | Concierge mouth + description megaphone |
| [annotation-armory.md](annotation-armory.md) | brass clockwork scarab | Unmarked bronze scarab | Twin antennae (needle vs glass orb) |
| [dice-workshop.md](dice-workshop.md) | bronze shop-window mannequin | Mannequin, pockets sewn shut | Sealed coat-envelope |
| [prompt-atelier.md](prompt-atelier.md) | glass fountain-pen | Loose pen sparking beside the mold-mouth | Local lantern + boring thermostat |
| [looping-observatory.md](looping-observatory.md) | bronze hourglass-sentinel | Frozen hourglass | Child hourglass with a ticket-mold |
| [safety-vault.md](safety-vault.md) | iron palace-gate that learned to walk | Open gate with no lock | Five-notch spyglass |
| [frontier-greenhouse.md](frontier-greenhouse.md) | stained-glass library-cart | Empty glass book on the cart | Local seed-bed + spend-meter |

Sibling of all of these: the filmed **type-ingot** in
[`../embabel-cheatsheet.md`](../embabel-cheatsheet.md).
