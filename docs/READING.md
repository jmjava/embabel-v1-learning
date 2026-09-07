# External reading (blogs → this curriculum)

Searched September 2026. Official teaching source remains the
[User Guide](https://docs.embabel.com/embabel-agent/guide/1.5.0-SNAPSHOT/)
and [Cookbook](https://docs.embabel.com/embabel-cookbook/1.5.0/) extracted
into [`CHEATSHEET.md`](CHEATSHEET.md). Blogs below **add motivation,
comparisons, and next modules** — they are not a second cookbook.

Map: [`videos/memory-os/STUDY_CAMPUS.md`](videos/memory-os/STUDY_CAMPUS.md).

## How to use

1. Study the matching palace / lesson first.
2. Read the blog for *why* and for APIs we have not demoed yet.
3. Do not paste travel recipes into this repo. Extract a rule, then add
   a locus or a future lesson.

## Category A — Planning core

| Piece | Why it belongs here |
|-------|---------------------|
| [Embabel: A New Agent Platform For the JVM](https://medium.com/@springrod/embabel-a-new-agent-platform-for-the-jvm-1c83402e0014) (Rod Johnson, May 2025) | Official intro: deterministic GOAP vs state machines |
| [The Embabel Vision](https://medium.com/@springrod/the-embabel-vision-967654f13793) | Why inferred plans beat hand-wired FSMs |
| [AI for your Gen AI: How and Why Embabel Plans](https://medium.com/@springrod/ai-for-your-gen-ai-how-and-why-embabel-plans-3930244218f6) | Trust-then-verify replan loop |
| [Embabel vs LangGraph](https://medium.com/@springrod/build-better-agents-in-java-vs-python-embabel-vs-langgraph-f7951a0d855c) | RepeatUntil vs graph loops; typed vs detyped |
| [InfoQ: Embabel 1.0](https://www.infoq.com/news/2026/08/embabel-1/) | Spring AI : Embabel :: Servlet : Spring MVC |
| [GOAP in Java](https://levelup.gitconnected.com/goal-oriented-action-planning-in-java-deterministic-planning-for-ai-agents-1354ebbd2af0) | Types as preconditions/effects |

## Category B — Agent definition

| Piece | Why it belongs here |
|-------|---------------------|
| [Baeldung: Creating an AI Agent in Java](https://www.baeldung.com/java-embabel-agent-framework) | Official-guide tutorial: URL → quiz agent |
| [Jettro: Hands-On Introduction](https://coenradie.com/posts/building-agents-with-embabel-a-hands-on-introduction/) | `@Action` / `@AchievesGoal` from a social-post agent |
| [Dan Vega: First Look](https://www.danvega.dev/blog/embabel-first-look) | Write/review, shell commands, mix models by role |
| [Dan Vega: Embabel 1.0 GA](https://www.danvega.dev/blog/embabel-1-0-ga) | GA scope; still Boot 3.5 / Spring AI 1.1, not Boot 4 |
| [Bell-SW step-by-step](https://bell-sw.com/blog/build-ai-agents-in-java-with-embabel-step-by-step-guide/) | Embabel vs Spring AI layers |

## Category C — Domain & DICE tools

| Piece | Why it belongs here |
|-------|---------------------|
| [Context Engineering Needs Domain Understanding](https://medium.com/@springrod/context-engineering-needs-domain-understanding-b4387e8e4bf8) | DICE: domain objects structure context *and* outputs |
| [From Alchemy to Engineering](https://medium.com/@springrod/from-alchemy-to-engineering-building-type-safe-gen-ai-applications-with-embabel-c3d89b7c989f) | Typed prompts; `@Tool` on domain types |
| [You Can Build Better AI Agents in Java Than Python](https://medium.com/@springrod/you-can-build-better-ai-agents-in-java-than-python-868eaf008493) | CrewAI YAML vs typed Spring agents |
| [Embabel vs Pydantic AI](https://medium.com/@springrod/build-better-agents-in-java-than-python-embabel-vs-pydantic-ai-ab373c149108) | Repository/domain split vs mixed `deps` |
| [Dan Vega: MCP Servers and Tools](https://www.danvega.dev/blog/embabel-mcp-servers-and-tools) | `CoreToolGroups.WEB`, MCP client, research → draft |

## Category D — LLM boundary / HITL

| Piece | Why it belongs here |
|-------|---------------------|
| [Craig Walls: HITL recipe](https://www.linkedin.com/pulse/spring-ai-recipe-adding-human-in-the-loop-craig-walls-ehkzc) | Unclear type → pause → human → replan (no graph edges) |
| [Rod: Why You Should Use Local Models](https://medium.com/@springrod/why-you-should-use-local-models-a3fce1124c94) | Mix cheap/local models per focused action |
| [Code Unboxing interview](https://www.youtube.com/watch?v=FfxmAR6AGrI) | Focused actions → smaller models; unit-test POJOs |

## Category E — Production envelope & 1.0 extras

| Piece | Why it belongs here |
|-------|---------------------|
| [Igor Dayen: 1.0.0 New Features](https://medium.com/embabel/embabel-agentic-ai-framework-release-1-0-0-new-features-332f375d0fba) | Thinking, streaming, inspectors, ToolishRag, Skills, BYOK, unfolding tools, chat store, `@SecureAgentTool` |
| [Year-end update](https://medium.com/@springrod/embabel-year-end-update-building-the-best-agent-framework-25ed98728e79) | Supervisor, RAG, MCP guide project |

## Category F — Frontier (not yet a lesson here)

These match [`SPEC_COMPLETENESS.md`](SPEC_COMPLETENESS.md) “next modules.”
Palace: Frontier Greenhouse.

| Piece | Concept to extract later |
|-------|-------------------------|
| [Rod: Agentic RAG chatbot](https://medium.com/@springrod/building-a-chatbot-with-embabel-agentic-rag-b26a8346cb16) | `ToolishRag` + Lucene `SearchOperations`; model decides when to search |
| [Jettro: Agentic RAG walkthrough](https://jettro.dev/agentic-rag-with-embabel-a-complete-walkthrough-d2ea2a258998) | Vector / text / regex / expand tools |
| [Jasper Blues: From Docs to Dialog](https://medium.com/embabel/from-docs-to-dialog-2b613901db60) | Toolish RAG + MCP export of the same tools |
| [BootcampToProd: MCP server](https://bootcamptoprod.com/embabel-build-mcp-server/) | `@Export(remote=true)` file-ops MCP |
| [Hub: RAG reference](https://hub.embabel.com/reference/rag) | Facade: only expose tools the store implements |
| [Hub: MCP integrations](https://hub.embabel.com/reference/integrations) | Consume + publish MCP |

## Official code (not blogs, still study)

- [embabel-agent](https://github.com/embabel/embabel-agent)
- [embabel-cookbook](https://github.com/embabel/embabel-cookbook) — extract rules, don’t clone travel plots
- Examples / Java template / Kotlin template / Tripper — linked from User Guide §8
- [Embabel HUB](https://hub.embabel.com) — live ToolishRag + chat-store demo

## What we will not copy in

Cookbook travel chapters, full vendor tutorials, or prompt dumps. This
repo keeps **rules + teaching demos**. If a blog adds an API we need in
a lesson, add it under `SPEC_COMPLETENESS` and a Frontier locus first.
