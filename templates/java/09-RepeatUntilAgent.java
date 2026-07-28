package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.ActionContext;
import com.embabel.agent.api.common.workflow.loop.RepeatUntilAcceptableBuilder;
import com.embabel.agent.api.common.workflow.loop.TextFeedback;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.LlmOptions;

/**
 * Starter: RepeatUntilAcceptable writer/reviewer loop.
 */
@Agent(description = "TODO: rewrite until score threshold met")
public class RepeatUntilAgent {

    public record Story(String text) {
    }

    @AchievesGoal(description = "Acceptable story produced")
    @Action
    public Story rewrite(UserInput userInput, ActionContext actionContext) {
        var writer = actionContext.ai().withLlm(LlmOptions.withAutoLlm());
        var reviewer = actionContext.ai().withLlm(LlmOptions.withAutoLlm());

        return RepeatUntilAcceptableBuilder
                .returning(Story.class)
                .withMaxIterations(5)
                .withScoreThreshold(0.8)
                .repeating(ctx -> {
                    var feedback = ctx.lastFeedbackOr("");
                    return writer.createObject(
                            """
                                    Write a short story inspired by: %s
                                    Feedback: %s
                                    """.formatted(userInput.getContent(), feedback),
                            Story.class
                    );
                })
                .withEvaluator(ctx -> reviewer.createObject(
                        """
                                Score 0.0-1.0 and give feedback.
                                Story: %s
                                """.formatted(ctx.getResultToEvaluate()),
                        TextFeedback.class
                ))
                .build()
                .asSubProcess(actionContext, Story.class);
    }
}
