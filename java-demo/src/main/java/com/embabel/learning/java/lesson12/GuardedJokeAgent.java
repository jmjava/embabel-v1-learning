package com.embabel.learning.java.lesson12;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.api.validation.guardrails.UserInputGuardRail;
import com.embabel.agent.core.Blackboard;
import com.embabel.common.core.validation.ValidationError;
import com.embabel.common.core.validation.ValidationResult;
import com.embabel.common.core.validation.ValidationSeverity;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;

import java.util.List;

/**
 * Lesson 12 (Java): attach a user-input guardrail to a PromptRunner.
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>{@code CRITICAL} validation errors block LLM execution</li>
 *   <li>Guardrails can inspect the {@link Blackboard}</li>
 *   <li>Prefer narrow, testable rules; combine with domain validation</li>
 * </ul>
 *
 * <p>If SPI package names differ slightly across Embabel versions, see the guided test
 * which also validates the pure guardrail logic independently.
 */
@Lesson(
        value = LessonOrder.GUARDRAILS,
        title = "Guardrails",
        guideRefs = {"4.31 Working with Guardrails"},
        counterpart = "com.embabel.learning.kotlin.lesson12.GuardedJokeAgent"
)
@Agent(description = "Tell a joke after validating the user topic")
public class GuardedJokeAgent {

    public record Joke(String text) {
    }

    /**
     * Blocks topics containing the word {@code password}.
     */
    public static final class NoPasswordTopics implements UserInputGuardRail {
        @Override
        public String getName() {
            return "NoPasswordTopics";
        }

        @Override
        public String getDescription() {
            return "Rejects prompts that ask about passwords";
        }

        @Override
        public ValidationResult validate(String input, Blackboard blackboard) {
            if (input != null && input.toLowerCase().contains("password")) {
                return new ValidationResult(false, List.of(
                        new ValidationError(
                                "password-topic",
                                "Topics about passwords are not allowed",
                                ValidationSeverity.CRITICAL
                        )
                ));
            }
            return ValidationResult.Companion.getVALID();
        }
    }

    @AchievesGoal(description = "A safe joke was produced")
    @Action
    public Joke joke(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm()
                .withGuardRails(new NoPasswordTopics())
                .createObject(
                        "Tell a short clean joke about: " + userInput.getContent(),
                        Joke.class
                );
    }
}
