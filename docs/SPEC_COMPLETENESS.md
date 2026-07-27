# Making the learning spec more complete

The original request asked for an extensive Embabel demo with Java+Kotlin, linkable docs, guided tests/breakpoints, and iteration until complete **plus** asking/answering what would make the spec more complete.

## Questions that improve the spec (asked + answered)

### 1. Which Embabel version is the curriculum pinned to?

**Answer applied:** Pin to **Embabel 1.0.0** (latest Maven Central release at build time), Spring Boot **3.5.13**, Java **21**. Document upgrade notes when moving to snapshots (`1.5.0-SNAPSHOT` used by official examples).

### 2. Should demos require live LLM keys?

**Answer applied:** Default path is **offline unit tests** with `FakeOperationContext`. Shell profiles activate OpenAI/Anthropic only when env keys exist. Learning should not block on paid APIs.

### 3. How deep should RAG / MCP / A2A / Observability go?

**Answer applied:** Core curriculum covers annotation agents, planners, tools, HITL, conditions, states, workflows, guardrails, stuck recovery, invocation, and Kotlin DSL.  
**Still valuable next modules (not blocking v1):**

- Lucene/Tika `ToolishRag` lab with local corpus
- MCP server export + `@SecureAgentTool`
- A2A server profile
- OpenTelemetry/Zipkin observability starter
- Streaming + thinking callbacks
- Budget guardrail / cost events
- ConcurrentAgentProcess
- Skills loading from local dir/GitHub

### 4. How should “linkable Javadocs” work day-to-day?

**Answer applied:** Heavy class/package Javadoc with `{@link}`, `@Lesson`, `@DebugGuide`, plus Markdown maps in `docs/`. Generate site via `javadoc:javadoc`.  
**Enhancement:** publish Dokka for Kotlin and a single aggregated docs site (Dokku/Javadoc aggregator).

### 5. Can breakpoints be set automatically?

**Answer applied:** `@DebugGuide` documents them; tests are the guided entrypoints.  
**Enhancement:** IntelliJ run configuration checked into `.idea/runConfigurations` with breakpoint files, or a JUnit extension that prints breakpoint targets before each guided test.

### 6. Should Java and Kotlin share domain types?

**Answer applied:** Yes — `learning-common` holds shared records/services so differences are in agent style, not business model drift.

### 7. How do we prove coverage of “all major functions”?

**Answer applied:** Curriculum table in `WALKTHROUGH.md` mapped to official guide sections.  
**Enhancement:** a checklist test that reflects over `@Lesson` annotations and fails if a `LessonOrder` constant lacks a class in either language (except Kotlin-only DSL).

### 8. What about enterprise concerns (security, tenancy, persistence)?

**Answer applied:** `ProcessOptions.withToolCallContext` and secure-tool docs called out.  
**Enhancement:** dedicated lesson for JWT-secured MCP, blackboard persistence, and early-termination budgets.

### 9. Should examples be copies of embabel-agent-examples?

**Answer applied:** Inspired by official examples (StarNews, Support, Supervisor kitchen, RepeatUntil, Researcher conditions) but rewritten as a **teaching curriculum** with cross-links and tests — not a raw fork.

### 10. How will learners know they “finished”?

**Answer applied:** Complete lessons 01→15, run all guided tests under the debugger, and write a short note comparing Java vs Kotlin for lessons 02, 05, and 15.  
**Enhancement:** optional quiz agent that uses Embabel against this repo’s Javadoc (meta!).

## Recommended next iterations

1. Add `rag-demo` module with ToolishRag + local docs corpus.
2. Add secured MCP profile module.
3. Add integration tests using `EmbabelMockitoIntegrationTest` for Lesson 02 end-to-end planning.
4. Add breakpoint printer JUnit extension.
5. Add lesson coverage reflective test.
6. Publish aggregated Javadoc/Dokka to GitHub Pages.
