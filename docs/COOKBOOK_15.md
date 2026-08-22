# Official sources → cheat sheet (not a cookbook clone)

This repo does **not** try to be a second copy of
[embabel/embabel-cookbook](https://github.com/embabel/embabel-cookbook).
The Cookbook is executable travel-domain recipes. We extract the **rules**
into [`CHEATSHEET.md`](CHEATSHEET.md) and keep 1.0 + 1.5 on the same `main`
([`VERSIONS.md`](VERSIONS.md)).

Published book: [docs.embabel.com/embabel-cookbook/1.5.0](https://docs.embabel.com/embabel-cookbook/1.5.0/)  
User Guide: [1.0](https://docs.embabel.com/embabel-agent/guide/1.0.0-SNAPSHOT/) · [1.5](https://docs.embabel.com/embabel-agent/guide/1.5.0-SNAPSHOT/)

## What we extracted

Cookbook chapters follow Introduction / Key Concepts / How It Works / Conclusion.
The table is the Key Concepts line, not the travel plot.

| Cookbook chapter | Rule to memorize | Already in this repo |
|------------------|------------------|----------------------|
| Action Domain Type Chaining | Return types are the plan. No ad hoc branching. | Lessons 02–03; cheat sheet “Types are the wiring” |
| Action Condition | `@Condition` is a boolean gate for mutually exclusive actions | Lesson 06 |
| Agent Stuck State | Wrong leftover type + no matching action = `STUCK` | Lesson 13 |
| Action Heuristics | Same I/O shape → `cost` picks the cheap one | Lesson 17 (`-Pembabel-15`) |
| Repeat Until Acceptable | `TextFeedback` score vs threshold; `maxIterations` safety cap | Lesson 09 |
| Agent Debugging | Starters + scan/deploy; debug the planner, not source order | [`BREAKPOINTS.md`](BREAKPOINTS.md) |
| Object Creation | `Ai` → `PromptRunner.creating(T).fromPrompt` | Lesson 01 |
| Create Object If Possible | Insufficient prompt → `null`, no throw → replan | Lesson 18 (`-Pembabel-15`) |
| Prompt Contributors | `fromMessages(SystemMessage, UserMessage)` beats one fat string | Lesson 21 (`-Pembabel-15`) |
| Thinking **1.5** | `thinking()` → `ThinkingResponse` (result + blocks); model must support it | Lesson 19 (`-Pembabel-15`) |
| Streaming **1.5** | `StreamingPromptRunnerBuilder` + `createObjectStream` | Lesson 20 (`-Pembabel-15`) |
| Tool Call | `@LlmTool` + `withToolObject` + inspectors | Lessons 07 + 21 (`-Pembabel-15`) |

Guide differentiators we keep in the cheat sheet: dynamic GOAP planning (not an
FSM), typed domain objects as control flow, Spring injection, mix LLMs, test
with fakes first.

## Memory OS

Cheat-sheet palace (not cookbook travel rooms):
[`videos/memory-os/embabel-cheatsheet.md`](videos/memory-os/embabel-cheatsheet.md).
Player: https://jmjava.github.io/embabel-v1-learning/

## Run

```bash
./mvnw test                 # Embabel 1.0
./mvnw test -Pembabel-15    # Embabel 1.5 + extras
```
