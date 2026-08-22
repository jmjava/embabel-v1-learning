package com.embabel.learning.common.curriculum;

/**
 * Official Embabel Cookbook 1.5 chapters mapped onto this learning repo.
 * <p>
 * Source of truth: <a href="https://github.com/embabel/embabel-cookbook">embabel/embabel-cookbook</a>
 * published at <a href="https://docs.embabel.com/embabel-cookbook/1.5.0/">docs.embabel.com</a>.
 * Video narration lives under {@code docs/demos/narration/} and Memory OS ingest
 * under {@code docs/videos/memory-os/}.
 */
public enum CookbookChapter {

    ACTION_DOMAIN_TYPE_CHAINING(
            "action-domain-type-chaining",
            "Action Domain Type Chaining",
            1,
            LessonOrder.COOKBOOK_TYPE_CHAINING,
            "Part 1: Agent Behavior",
            "Planner walks a sealed request type to the matching travel activity."),

    ACTION_CONDITION(
            "action-condition",
            "Action Condition",
            2,
            LessonOrder.CONDITIONS,
            "Part 1: Agent Behavior",
            "pre/post conditions split direct vs connecting flights."),

    AGENT_STUCK_STATE(
            "agent-stuck-state",
            "Agent Stuck State",
            3,
            LessonOrder.STUCK,
            "Part 1: Agent Behavior",
            "Non-travel input classifies to a type no action can consume."),

    ACTION_HEURISTICS(
            "action-heuristics",
            "Action Heuristics",
            4,
            LessonOrder.COOKBOOK_HEURISTICS,
            "Part 1: Agent Behavior",
            "GOAP cost steers the planner to the cheapest matching action."),

    REPEAT_UNTIL_ACCEPTABLE(
            "repeat-until-acceptable",
            "Repeat Until Acceptable",
            5,
            LessonOrder.WORKFLOWS,
            "Part 1: Agent Behavior",
            "Subprocess retries until a score threshold is met."),

    AGENT_DEBUGGING(
            "agent-debugging",
            "Agent Debugging",
            6,
            LessonOrder.STUCK,
            "Part 1: Agent Behavior",
            "History, blackboard dumps, and guided breakpoints."),

    OBJECT_CREATION(
            "object-creation",
            "Object Creation",
            7,
            LessonOrder.INJECTED_AI,
            "Part 2: Agentic AI APIs",
            "creating(Class).withExample().fromPrompt() builds a typed object."),

    CREATE_OBJECT_IF_POSSIBLE(
            "create-object-if-possible",
            "Create Object If Possible",
            8,
            LessonOrder.COOKBOOK_CREATE_IF_POSSIBLE,
            "Part 2: Agentic AI APIs",
            "Nullable extraction returns null when the prompt is insufficient."),

    PROMPT_CONTRIBUTORS(
            "prompt-contributors",
            "Prompt Contributors",
            9,
            LessonOrder.COOKBOOK_MESSAGES_AND_TOOLS,
            "Part 2: Agentic AI APIs",
            "fromMessages() plus personas / system prompts."),

    THINKING(
            "thinking",
            "Thinking",
            10,
            LessonOrder.COOKBOOK_THINKING,
            "Part 2: Agentic AI APIs",
            "PromptRunner.thinking() returns a ThinkingResponse with rationale."),

    STREAMING(
            "streaming",
            "Streaming",
            11,
            LessonOrder.COOKBOOK_STREAMING,
            "Part 2: Agentic AI APIs",
            "StreamingPromptRunnerBuilder emits typed objects as they arrive."),

    TOOL_CALL(
            "tool-call",
            "Tool Call",
            12,
            LessonOrder.COOKBOOK_MESSAGES_AND_TOOLS,
            "Part 2: Agentic AI APIs",
            "withToolObject() plus ToolCallLoggingInspector.");

    private final String slug;
    private final String title;
    private final int officialOrder;
    private final int lessonNumber;
    private final String part;
    private final String summary;

    CookbookChapter(
            String slug,
            String title,
            int officialOrder,
            int lessonNumber,
            String part,
            String summary) {
        this.slug = slug;
        this.title = title;
        this.officialOrder = officialOrder;
        this.lessonNumber = lessonNumber;
        this.part = part;
        this.summary = summary;
    }

    public String slug() {
        return slug;
    }

    public String title() {
        return title;
    }

    public int officialOrder() {
        return officialOrder;
    }

    public int lessonNumber() {
        return lessonNumber;
    }

    public String part() {
        return part;
    }

    public String summary() {
        return summary;
    }

    public String officialUrl() {
        return "https://docs.embabel.com/embabel-cookbook/1.5.0/#" + slug;
    }

    public String officialRepoPath() {
        return "manuscript/" + slug + ".adoc";
    }

    public String narrationFile() {
        return "docs/demos/narration/%02d-%s.md".formatted(officialOrder, slug);
    }
}
