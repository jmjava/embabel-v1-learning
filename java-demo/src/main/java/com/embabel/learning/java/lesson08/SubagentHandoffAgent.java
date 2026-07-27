package com.embabel.learning.java.lesson08;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.api.tool.Subagent;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.LlmOptions;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.ReviewedStory;
import com.embabel.learning.java.lesson02.WriteAndReviewAgent;

/**
 * Lesson 08 (Java): call another agent as a tool.
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>{@code Subagent.ofClass(...).consuming(...)} defines the JSON schema the LLM sees</li>
 *   <li>Subagent inherits parent verbosity / process options</li>
 *   <li>Useful for composition without hard-coding a sequential call in Java</li>
 * </ul>
 */
@Lesson(
        value = LessonOrder.SUBAGENTS,
        title = "Subagent handoffs",
        guideRefs = {"4.9.6 Subagent: Agent Handoffs as Tools", "4.6.14 Running Subagents"},
        counterpart = "com.embabel.learning.kotlin.lesson08.SubagentHandoffAgent"
)
@Agent(description = "Use a story subagent to produce an anthology blurb")
public class SubagentHandoffAgent {

    public record AnthologyBlurb(String blurb) {
    }

    @AchievesGoal(description = "Anthology blurb created via subagent")
    @Action
    public AnthologyBlurb compose(UserInput userInput, OperationContext context) {
        return context.promptRunner()
                .withLlm(LlmOptions.withAutoLlm())
                .withTool(Subagent.ofClass(WriteAndReviewAgent.class).consuming(UserInput.class))
                .createObject(
                        """
                                The user wants an anthology blurb.
                                Use the write-and-review subagent to create/review a short story first,
                                then summarize it into a 2-sentence blurb.
                                
                                User request: %s
                                """.formatted(userInput.getContent()),
                        AnthologyBlurb.class
                );
    }

    /**
     * Helper for unit tests that want to assert the goal type linkage.
     */
    public Class<ReviewedStory> subagentGoalType() {
        return ReviewedStory.class;
    }
}
