package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import org.springframework.stereotype.Service;

/**
 * Starter: GOAP chain mixing LLM extraction and plain code.
 */
@Agent(description = "TODO: personalized brief from user interests")
public class GoapMixedCodeAgent {

    public record Person(String name, String interest) {
    }

    public record InterestSummary(String summary) {
    }

    public record Brief(String name, String text) {
    }

    private final InterestNormalizer normalizer;

    public GoapMixedCodeAgent(InterestNormalizer normalizer) {
        this.normalizer = normalizer;
    }

    @Action
    public Person extract(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm().createObjectIfPossible(
                "Extract name and interest from: " + userInput.getContent(),
                Person.class
        );
    }

    /** Non-LLM action — code agency. */
    @Action
    public InterestSummary summarize(Person person) {
        return new InterestSummary(normalizer.normalize(person.interest()));
    }

    @AchievesGoal(description = "Brief written")
    @Action
    public Brief write(Person person, InterestSummary summary, Ai ai) {
        return ai.withDefaultLlm().createObject(
                """
                        Write a 3-sentence brief for %s about %s.
                        """.formatted(person.name(), summary.summary()),
                Brief.class
        );
    }

    @Service
    public static class InterestNormalizer {
        public String normalize(String interest) {
            if (interest == null || interest.isBlank()) {
                return "general curiosity";
            }
            return interest.trim().toLowerCase();
        }
    }
}
