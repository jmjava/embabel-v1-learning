# Embabel 1.5 + official Cookbook

This branch pins the learning repo to **Embabel Agent 1.5.0** and maps every chapter in
[embabel/embabel-cookbook](https://github.com/embabel/embabel-cookbook) onto a lesson,
guided test, and video script.

Published book: [docs.embabel.com/embabel-cookbook/1.5.0](https://docs.embabel.com/embabel-cookbook/1.5.0/)

## Why a second track

Lessons 01–15 stay the 1.0 study path (GOAP, HITL, tools, planners, DSL).  
Lessons 16–21 are the **1.5 cookbook track**: travel-domain counterparts of the official
recipes, written so they run offline with `FakeOperationContext`.

The official cookbook tests call a live LLM (OpenAI / Anthropic / Ollama). Ours keep
the same APIs (`createObjectIfPossible`, `thinking()`, `StreamingPromptRunnerBuilder`,
action `cost`, `fromMessages`, `ToolCallLoggingInspector`) but stay unit-testable.

## Chapter map

| # | Official chapter | This repo | Video narration |
|---|------------------|-----------|-----------------|
| 1 | Action Domain Type Chaining | Lesson 16 `cookbook15.TypeChainingTravelAgent` | `docs/demos/narration/01-action-domain-type-chaining.md` |
| 2 | Action Condition | Lesson 06 (conditions) | `02-action-condition.md` |
| 3 | Agent Stuck State | Lesson 13 | `03-agent-stuck-state.md` |
| 4 | Action Heuristics | Lesson 17 `HeuristicTravelAgent` | `04-action-heuristics.md` |
| 5 | Repeat Until Acceptable | Lesson 09 | `05-repeat-until-acceptable.md` |
| 6 | Agent Debugging | `@DebugGuide` + `docs/BREAKPOINTS.md` | `06-agent-debugging.md` |
| 7 | Object Creation | Lesson 01 | `07-object-creation.md` |
| 8 | Create Object If Possible | Lesson 18 `PossibleTripPlanner` | `08-create-object-if-possible.md` |
| 9 | Prompt Contributors | Lesson 21 `MessageAndToolTripPlanner` | `09-prompt-contributors.md` |
| 10 | Thinking | Lesson 19 `ThinkingTripPlanner` | `10-thinking.md` |
| 11 | Streaming | Lesson 20 `StreamingTripPlanner` | `11-streaming.md` |
| 12 | Tool Call | Lesson 21 + Lesson 07 | `12-tool-call.md` |

Catalog code: `CookbookChapter` / `CookbookChapterCatalog` in `learning-common`.

## Videos

Primary path is a Memory OS palace (method of loci) compiled from
[`docs/videos/memory-os/embabel-cookbook-15.md`](videos/memory-os/embabel-cookbook-15.md).

```bash
memoryos compile docs/videos/memory-os/embabel-cookbook-15.md \
  --id embabel-cookbook-15 \
  -o docs/videos/memory-os/embabel-cookbook-15.palace.yaml
memoryos validate docs/videos/memory-os/embabel-cookbook-15.palace.yaml
memoryos build docs/videos/memory-os/embabel-cookbook-15.palace.yaml --floor floor-1
```

Spoken chapter scripts in [`docs/demos/narration/`](demos/narration/) remain available
for [documentation-generator](https://github.com/jmjava/documentation-generator).

Published player: https://jmjava.github.io/embabel-v1-learning/

See [`docs/videos/memory-os/README.md`](videos/memory-os/README.md) and [`docs/GITHUB-PAGES.md`](GITHUB-PAGES.md).

## Run

```bash
./mvnw test
./mvnw -pl java-demo spring-boot:run   # optional live LLM
```
