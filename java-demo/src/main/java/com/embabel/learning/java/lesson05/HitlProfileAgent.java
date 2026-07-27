package com.embabel.learning.java.lesson05;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.core.hitl.WaitFor;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.PersonProfile;

/**
 * Lesson 05 (Java): combine LLM extraction with HITL fallback.
 *
 * <h2>Planning nuance</h2>
 * {@link #askUserForProfile} has {@code cost = 100}. GOAP prefers the cheap
 * {@link #extractProfile} path when it can succeed. If extraction returns {@code null}
 * (via {@code createObjectIfPossible}), the planner can choose the costly HITL action.
 */
@Lesson(
        value = LessonOrder.HITL,
        title = "Human-in-the-loop with WaitFor",
        guideRefs = {"4.19.9 Human-in-the-Loop with WaitFor"},
        counterpart = "com.embabel.learning.kotlin.lesson05.HitlProfileAgent"
)
@Agent(description = "Collect a person profile, falling back to a human form if needed")
public class HitlProfileAgent {

    public record WelcomeNote(String message) {
    }

    @Action
    public PersonProfile extractProfile(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm().createObjectIfPossible(
                """
                        Extract name and interest from: %s
                        """.formatted(userInput.getContent()),
                PersonProfile.class
        );
    }

    /**
     * HITL fallback. Breakpoint here to inspect process WAITING transition.
     */
    @Action(cost = 100.0)
    public PersonProfile askUserForProfile(UserInput userInput) {
        return WaitFor.formSubmission(
                "Please provide name and interest (input was: " + userInput.getContent() + ")",
                PersonProfile.class
        );
    }

    @AchievesGoal(description = "User has been welcomed with a personalized note")
    @Action
    public WelcomeNote welcome(PersonProfile profile, Ai ai) {
        return ai.withDefaultLlm().createObject(
                """
                        Write a one-sentence welcome for %s who likes %s.
                        """.formatted(profile.name(), profile.interest()),
                WelcomeNote.class
        );
    }
}
