package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.validation.guardrails.UserInputGuardRail;
import com.embabel.agent.core.Blackboard;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.core.validation.ValidationError;
import com.embabel.common.core.validation.ValidationResult;
import com.embabel.common.core.validation.ValidationSeverity;

import java.util.List;

/**
 * Starter: attach a CRITICAL user-input guardrail.
 */
@Agent(description = "TODO: safe generation with guardrails")
public class GuardedAgent {

    public record Joke(String text) {
    }

    public static final class NoPasswordTopics implements UserInputGuardRail {
        @Override
        public String getName() {
            return "NoPasswordTopics";
        }

        @Override
        public String getDescription() {
            return "Rejects password topics";
        }

        @Override
        public ValidationResult validate(String input, Blackboard blackboard) {
            if (input != null && input.toLowerCase().contains("password")) {
                return new ValidationResult(false, List.of(
                        new ValidationError(
                                "password-topic",
                                "Password topics are not allowed",
                                ValidationSeverity.CRITICAL
                        )
                ));
            }
            return ValidationResult.Companion.getVALID();
        }
    }

    @AchievesGoal(description = "Safe joke produced")
    @Action
    public Joke joke(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm()
                .withGuardRails(new NoPasswordTopics())
                .createObject("Short clean joke about: " + userInput.getContent(), Joke.class);
    }
}
