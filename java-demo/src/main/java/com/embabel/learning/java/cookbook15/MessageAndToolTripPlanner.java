package com.embabel.learning.java.cookbook15;

import com.embabel.agent.api.annotation.LlmTool;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PromptRunner;
import com.embabel.agent.api.tool.callback.LogLevel;
import com.embabel.agent.api.tool.callback.ToolCallLoggingInspector;
import com.embabel.chat.Message;
import com.embabel.chat.SystemMessage;
import com.embabel.chat.UserMessage;
import com.embabel.learning.common.curriculum.CookbookChapter;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.TripBrief;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Lesson 21: cookbook prompt contributors + tool calls.
 * <p>
 * Combines {@code creating().fromMessages()} with {@code withToolObject} and
 * {@link ToolCallLoggingInspector} — the two Part 2 recipes that sit on top
 * of injected {@code Ai} rather than a planner.
 *
 * @see CookbookChapter#PROMPT_CONTRIBUTORS
 * @see CookbookChapter#TOOL_CALL
 */
@Lesson(
        value = LessonOrder.COOKBOOK_MESSAGES_AND_TOOLS,
        title = "Cookbook 1.5 — messages, tools, inspectors",
        guideRefs = {"Cookbook: Prompt Contributors", "Cookbook: Tool Call"},
        counterpart = "com.embabel.learning.kotlin.cookbook15.MessageAndToolTripPlanner"
)
@Component
public class MessageAndToolTripPlanner {

    private static final Logger logger = LoggerFactory.getLogger(MessageAndToolTripPlanner.class);

    private final Ai ai;

    public MessageAndToolTripPlanner(Ai ai) {
        this.ai = ai;
    }

    public TripBrief planFromMessages(String destinationCue, String extras) {
        List<Message> messages = List.of(
                new SystemMessage("You are a travel assistant."),
                new UserMessage("Create a structured travel plan for " + destinationCue + "."),
                new UserMessage(extras)
        );
        return ai.withDefaultLlm()
                .withId("cookbook15-from-messages")
                .creating(TripBrief.class)
                .fromMessages(messages);
    }

    public TripBrief planWithTools(String destination) {
        PromptRunner runner = ai.withDefaultLlm()
                .withId("cookbook15-tools")
                .withToolObject(new TravelLookupTools())
                .withToolCallInspectors(new ToolCallLoggingInspector(LogLevel.INFO, logger));
        return runner.creating(TripBrief.class)
                .fromPrompt("""
                        Plan a 3-day trip to %s.
                        Use tools to check weather and top attractions.
                        """.formatted(destination));
    }

    /**
     * Deterministic lookup tools — same idea as the official cookbook travel tooling.
     */
    public static class TravelLookupTools {

        @LlmTool(description = "Get current weather for a destination.")
        public String getWeather(String destination) {
            return "Sunny, 24C, low humidity — good outdoor weather in " + destination + ".";
        }

        @LlmTool(description = "Get top attractions for a destination.")
        public String getTopAttractions(String destination) {
            return "Highlights in " + destination + ": a landmark, a museum, a river walk.";
        }
    }
}
