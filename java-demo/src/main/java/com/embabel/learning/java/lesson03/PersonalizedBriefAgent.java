package com.embabel.learning.java.lesson03;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Export;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.PersonProfile;
import com.embabel.learning.common.service.InterestService;

/**
 * Lesson 03 (Java): richer GOAP chain mixing code and LLM steps.
 *
 * <h2>Inferred plan</h2>
 * {@code UserInput -> PersonProfile -> InterestSummary -> PersonalizedBrief}
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>{@code createObjectIfPossible} returns {@code null} when extraction fails,
 *       causing the planner to replan (often toward HITL in more advanced agents)</li>
 *   <li>{@link #summarizeInterest} has no {@link Ai} parameter — pure domain code</li>
 *   <li>Returning objects of new types is how you "post" effects onto the blackboard</li>
 * </ul>
 */
@Lesson(
        value = LessonOrder.GOAP_MULTI_ACTION,
        title = "Multi-action GOAP with mixed code/LLM",
        guideRefs = {"1.4 Core Concepts", "4.2 Agent Process Flow", "4.4 Domain Objects"},
        counterpart = "com.embabel.learning.kotlin.lesson03.PersonalizedBriefAgent"
)
@Agent(description = "Build a personalized brief from a person's interests")
public class PersonalizedBriefAgent {

    public record InterestSummary(String summary) {
    }

    public record PersonalizedBrief(String name, String brief) {
    }

    private final InterestService interestService;

    public PersonalizedBriefAgent(InterestService interestService) {
        this.interestService = interestService;
    }

    @Action
    public PersonProfile extractPerson(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm().createObjectIfPossible(
                """
                        Extract a person name and primary interest from:
                        %s
                        """.formatted(userInput.getContent()),
                PersonProfile.class
        );
    }

    /**
     * Non-LLM action — demonstrates code agency inside GOAP.
     */
    @Action
    public InterestSummary summarizeInterest(PersonProfile person) {
        return new InterestSummary(interestService.summarizeInterest(person.interest()));
    }

    @AchievesGoal(
            description = "A personalized brief has been written",
            export = @Export(remote = true, name = "javaPersonalizedBrief")
    )
    @Action
    public PersonalizedBrief writeBrief(PersonProfile person, InterestSummary summary, Ai ai) {
        return ai.withDefaultLlm().createObject(
                """
                        Write a 3-sentence personalized brief for %s.
                        Their interest summary: %s
                        """.formatted(person.name(), summary.summary()),
                PersonalizedBrief.class
        );
    }
}
