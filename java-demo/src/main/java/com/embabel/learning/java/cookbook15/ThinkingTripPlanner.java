package com.embabel.learning.java.cookbook15;

import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PromptRunner;
import com.embabel.common.core.thinking.ThinkingResponse;
import com.embabel.learning.common.curriculum.CookbookChapter;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.TripBrief;
import org.springframework.stereotype.Component;

/**
 * Lesson 19: Embabel 1.5 thinking traces.
 * <p>
 * {@link PromptRunner#thinking()} asks the model for an explicit rationale
 * (often tagged {@code <decision_reasoning>}) plus the typed result.
 *
 * @see CookbookChapter#THINKING
 */
@Lesson(
        value = LessonOrder.COOKBOOK_THINKING,
        title = "Cookbook 1.5 — thinking traces",
        guideRefs = {"Cookbook: Thinking"},
        counterpart = "com.embabel.learning.kotlin.cookbook15.ThinkingTripPlanner"
)
@Component
public class ThinkingTripPlanner {

    public static final String REASONING_SYSTEM_PROMPT = """
            You are a travel assistant.
            Provide reasoning inside <decision_reasoning>...</decision_reasoning>.
            Keep reasoning to 3-5 bullets covering time, risk, and trade-offs.
            """;

    private final Ai ai;

    public ThinkingTripPlanner(Ai ai) {
        this.ai = ai;
    }

    public ThinkingResponse<TripBrief> planWithThinking(String userText) {
        PromptRunner runner = ai.withDefaultLlm().withId("cookbook15-thinking");
        if (!runner.supportsThinking()) {
            throw new IllegalStateException(
                    "Current PromptRunner does not support thinking(); use a 1.5 model provider");
        }
        return runner
                .withSystemPrompt(REASONING_SYSTEM_PROMPT)
                .thinking()
                .createObject(
                        """
                                Create a short, balanced travel plan for:
                                %s
                                """.formatted(userText),
                        TripBrief.class
                );
    }
}
