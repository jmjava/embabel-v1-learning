package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Export;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.LlmOptions;

/**
 * Starter: classic GOAP agent — UserInput → Draft → Reviewed.
 */
@Agent(description = "TODO: describe agent for selection / autonomy")
public class GoapWriteReviewAgent {

    public record Draft(String text) {
    }

    public record Reviewed(Draft draft, String review) {
    }

    @Action
    public Draft write(UserInput userInput, Ai ai) {
        return ai.withLlm(LlmOptions.withAutoLlm().withTemperature(0.7))
                .creating(Draft.class)
                .fromPrompt("""
                        Write a short draft inspired by:
                        %s
                        """.formatted(userInput.getContent()));
    }

    @AchievesGoal(
            description = "TODO: goal description",
            export = @Export(remote = true, name = "goapWriteReview"))
    @Action
    public Reviewed review(UserInput userInput, Draft draft, Ai ai) {
        var review = ai.withAutoLlm().generateText("""
                Review this draft for clarity and fit to the user input.
                Draft: %s
                User input: %s
                """.formatted(draft.text(), userInput.getContent()));
        return new Reviewed(draft, review);
    }
}
