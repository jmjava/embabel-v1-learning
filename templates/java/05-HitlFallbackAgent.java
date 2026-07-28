package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.core.hitl.WaitFor;
import com.embabel.agent.domain.io.UserInput;

/**
 * Starter: cheap LLM extract, expensive HITL fallback.
 */
@Agent(description = "TODO: collect profile with HITL fallback")
public class HitlFallbackAgent {

    public record Profile(String name, String interest) {
    }

    public record Welcome(String message) {
    }

    @Action
    public Profile extract(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm().createObjectIfPossible(
                "Extract name and interest from: " + userInput.getContent(),
                Profile.class
        );
    }

    @Action(cost = 100.0)
    public Profile askUser(UserInput userInput) {
        return WaitFor.formSubmission(
                "Please provide name and interest (input was: " + userInput.getContent() + ")",
                Profile.class
        );
    }

    @AchievesGoal(description = "User welcomed")
    @Action
    public Welcome welcome(Profile profile, Ai ai) {
        return ai.withDefaultLlm().createObject(
                "One-sentence welcome for %s who likes %s".formatted(profile.name(), profile.interest()),
                Welcome.class
        );
    }
}
