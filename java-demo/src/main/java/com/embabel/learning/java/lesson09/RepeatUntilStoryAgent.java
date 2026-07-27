package com.embabel.learning.java.lesson09;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.ActionContext;
import com.embabel.agent.api.common.workflow.loop.RepeatUntilAcceptableBuilder;
import com.embabel.agent.api.common.workflow.loop.TextFeedback;
import com.embabel.common.ai.model.LlmOptions;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.Story;

/**
 * Lesson 09 (Java): {@link RepeatUntilAcceptableBuilder} writer/reviewer loop.
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>Requires {@link ActionContext} (not plain {@code Ai}) because it runs a subprocess</li>
 *   <li>{@code withScoreThreshold} / {@code withMaxIterations} bound nondeterminism</li>
 *   <li>Evaluator returns {@link TextFeedback} with score + feedback text</li>
 *   <li>Some models reject custom temperature — keep options conservative in demos</li>
 * </ul>
 */
@Lesson(
        value = LessonOrder.WORKFLOWS,
        title = "RepeatUntil workflow builder",
        guideRefs = {"4.7 DSL / Standard Workflows"},
        counterpart = "com.embabel.learning.kotlin.lesson09.RepeatUntilStoryAgent"
)
@Agent(description = "Keep rewriting a story until a reviewer score threshold is met")
public class RepeatUntilStoryAgent {

    @AchievesGoal(description = "An acceptable story has been produced")
    @Action
    public Story rewriteUntilSatisfied(UserInput userInput, ActionContext actionContext) {
        var writer = actionContext.ai().withLlm(LlmOptions.withAutoLlm());
        var reviewer = actionContext.ai().withLlm(LlmOptions.withAutoLlm());

        return RepeatUntilAcceptableBuilder
                .returning(Story.class)
                .withMaxIterations(5)
                .withScoreThreshold(0.8)
                .repeating(context -> {
                    var feedback = context.lastFeedbackOr("");
                    return writer.createObject(
                            """
                                    Write a creative short story inspired by: %s
                                    
                                    Consider feedback: %s
                                    """.formatted(userInput.getContent(), feedback),
                            Story.class
                    );
                })
                .withEvaluator(context -> reviewer.createObject(
                        """
                                Score creativity and relevance (0.0-1.0) and give feedback.
                                Story: %s
                                User input: %s
                                """.formatted(context.getResultToEvaluate(), userInput.getContent()),
                        TextFeedback.class
                ))
                .build()
                .asSubProcess(actionContext, Story.class);
    }
}
