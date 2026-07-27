package com.embabel.learning.common.curriculum;

/**
 * Canonical curriculum ordering for the Embabel learning demo.
 * <p>
 * Study path (Java and Kotlin side-by-side unless noted):
 * <ol>
 *   <li>{@code 01} Injected {@code Ai} — use Embabel without a full agent</li>
 *   <li>{@code 02} First {@code @Agent} — Write/Review GOAP basics</li>
 *   <li>{@code 03} Multi-action GOAP — type-driven planning nuances</li>
 *   <li>{@code 04} Domain objects as tools — DICE / {@code @Tool}</li>
 *   <li>{@code 05} Human-in-the-loop — {@code WaitFor}</li>
 *   <li>{@code 06} Conditions &amp; name binding</li>
 *   <li>{@code 07} Tool groups &amp; custom tools</li>
 *   <li>{@code 08} Subagent handoffs</li>
 *   <li>{@code 09} Workflow builders — RepeatUntil / ScatterGather</li>
 *   <li>{@code 10} Planner types — GOAP / Utility / Supervisor</li>
 *   <li>{@code 11} {@code @State} machines &amp; loops</li>
 *   <li>{@code 12} Guardrails</li>
 *   <li>{@code 13} Stuck recovery</li>
 *   <li>{@code 14} Invocation APIs &amp; ProcessOptions</li>
 *   <li>{@code 15} Kotlin DSL agents (Kotlin module only)</li>
 * </ol>
 *
 * <h2>How to use this curriculum</h2>
 * <ol>
 *   <li>Read {@code docs/WALKTHROUGH.md}</li>
 *   <li>Open the {@code package-info.java} / package KDoc for the lesson</li>
 *   <li>Read the agent class Javadoc/KDoc (links to counterparts)</li>
 *   <li>Run the matching {@code *GuidedTest} and set breakpoints listed there</li>
 *   <li>Compare Java vs Kotlin notes in {@code docs/JAVA_VS_KOTLIN.md}</li>
 * </ol>
 */
public final class LessonOrder {

    public static final int INJECTED_AI = 1;
    public static final int FIRST_AGENT = 2;
    public static final int GOAP_MULTI_ACTION = 3;
    public static final int DOMAIN_TOOLS = 4;
    public static final int HITL = 5;
    public static final int CONDITIONS = 6;
    public static final int TOOLS = 7;
    public static final int SUBAGENTS = 8;
    public static final int WORKFLOWS = 9;
    public static final int PLANNERS = 10;
    public static final int STATES = 11;
    public static final int GUARDRAILS = 12;
    public static final int STUCK = 13;
    public static final int INVOCATION = 14;
    public static final int KOTLIN_DSL = 15;

    private LessonOrder() {
    }
}
