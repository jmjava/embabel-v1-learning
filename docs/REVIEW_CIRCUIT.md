# 60–90 minute review circuit (checklist)

Print this page or keep it open. Check boxes as you go.  
Target: **~60 minutes** core path, **~90 minutes** with quizzes.

Related: [`TOP_10.md`](TOP_10.md) · [`CHEATSHEET.md`](CHEATSHEET.md) · [`CHAPTER_SUMMARIES.md`](CHAPTER_SUMMARIES.md) · [`QUIZ_FLASHCARDS.md`](QUIZ_FLASHCARDS.md) · [`docs/print/embabel-cheatsheet.pdf`](print/embabel-cheatsheet.pdf)

---

## Before you start (2 min)

- [ ] Repo builds: `./mvnw test`
- [ ] IDE open on `java-demo` + `kotlin-demo`
- [ ] Printed or on-screen: [cheat sheet PDF](print/embabel-cheatsheet.pdf) / [`CHEATSHEET.md`](CHEATSHEET.md)

---

## Circuit A — Core path (~60 min)

### Block 1 — Orient (8 min)

- [ ] Skim [`TOP_10.md`](TOP_10.md) (all 10)
- [ ] Skim [`CHEATSHEET.md`](CHEATSHEET.md) mental model + annotations + planners
- [ ] Optional: recite one Memory OS recall route from [`videos/memory-os/STUDY_CAMPUS.md`](videos/memory-os/STUDY_CAMPUS.md)
- [ ] Optional: open PDF and mark 3 things you always forget

### Block 2 — Canonical agent (15 min)

- [ ] Read Lesson 02 summary in [`CHAPTER_SUMMARIES.md`](CHAPTER_SUMMARIES.md)
- [ ] Debug `java-demo` … `WriteAndReviewAgentGuidedTest` (breakpoints from `@DebugGuide`)
- [ ] Debug `kotlin-demo` … `WriteAndReviewAgentGuidedTest`
- [ ] Note one Java vs Kotlin difference you saw

### Block 3 — Tools + conditions + safety (20 min)

- [ ] Lesson 04: skim summary → debug `BankSupportAgentGuidedTest`
- [ ] Lesson 06: skim summary → debug `ConditionalResearchAgentGuidedTest` (either language)
- [ ] Lesson 12: skim summary → debug `GuardedJokeAgentGuidedTest`
- [ ] Say out loud: “tools attach per PromptRunner; conditions are pure; CRITICAL blocks”

### Block 4 — Planners + sharp edges (12 min)

- [ ] Lesson 10 summary: GOAP vs Utility vs Supervisor
- [ ] Run/read `PlannerTypeLessonTest`
- [ ] Skim [`ADVANCED_NUANCES.md`](ADVANCED_NUANCES.md) (planning + tools sections)

### Block 5 — Close the loop (5 min)

- [ ] Answer mixed-review questions at bottom of [`QUIZ_FLASHCARDS.md`](QUIZ_FLASHCARDS.md)
- [ ] Write 3 personal “don’t forget” notes under **Session notes** below

---

## Circuit B — Stretch to 90 min (+30)

Do Circuit A first, then:

### Flashcards (15 min)

Mark each lesson after you can answer without peeking:

- [ ] L01 Injected Ai
- [ ] L02 First @Agent
- [ ] L03 Multi-action GOAP
- [ ] L04 Domain tools
- [ ] L05 HITL
- [ ] L06 Conditions & bindings
- [ ] L07 Tool groups
- [ ] L08 Subagents
- [ ] L09 RepeatUntil
- [ ] L10 Planners
- [ ] L11 @State
- [ ] L12 Guardrails
- [ ] L13 StuckHandler
- [ ] L14 Invocation
- [ ] L15 Kotlin DSL
- [ ] Mixed review (hard mode)

### Extra debug / read (15 min)

Pick **two**:

- [ ] Lesson 05 HITL — `HitlProfileAgentGuidedTest` + read `WaitFor` / `fromForm`
- [ ] Lesson 08 Subagents — `SubagentHandoffAgentGuidedTest`
- [ ] Lesson 11 States — `StatefulDraftAgentGuidedTest`
- [ ] Lesson 13 Stuck — `SelfUnstickingAgentGuidedTest`
- [ ] Lesson 15 DSL — `FactCheckerDslGuidedTest`
- [ ] Skim [`JAVA_VS_KOTLIN.md`](JAVA_VS_KOTLIN.md) end-to-end

---

## Done criteria

You can check “done” when you can:

- [ ] Explain GOAP type wiring in one minute
- [ ] List the four planners and when you’d pick each
- [ ] Explain why `@Tool` needs `withToolObject`
- [ ] Name two ways to loop ( `@State` / RepeatUntil ) and one HITL API
- [ ] Set five useful breakpoints without looking them up ([`BREAKPOINTS.md`](BREAKPOINTS.md))

---

## Session notes

Date: _______________

Things I missed:

1. ________________________________________________
2. ________________________________________________
3. ________________________________________________

Next session focus: ________________________________

---

## Regenerating the printable cheat sheet

```bash
./scripts/generate-cheatsheet-pdf.sh
# or open docs/print/cheatsheet.html → Print → Save as PDF
```
