package com.embabel.learning.java.lesson10;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.EmbabelComponent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PlannerType;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;

/**
 * Lesson 10b (Java): Utility planner — pick highest net-value action greedily.
 *
 * <p>Also demonstrates {@link EmbabelComponent}: reusable action library that is not
 * itself a complete agent (here we still wrap a small agent for a runnable demo).
 *
 * <h2>Nuance</h2>
 * Utility is great for opportunistic triage/chat-like exploration. Design clear
 * termination ({@link AchievesGoal}) or it may keep exploring.
 */
@Lesson(
        value = LessonOrder.PLANNERS,
        title = "Utility planner triage",
        guideRefs = {"4.20.1 Utility AI", "4.6.2 @EmbabelComponent"},
        counterpart = "com.embabel.learning.kotlin.lesson10.UtilityTriageAgent"
)
@Agent(
        description = "Triage an inbound support message by urgency",
        planner = PlannerType.UTILITY
)
public class UtilityTriageComponent {

    public record UrgencyScore(double score, String rationale) {
    }

    public record TriageDecision(String queue, String summary) {
    }

    /**
     * Illustrative reusable fragment; in larger systems, put shared actions here.
     */
    @EmbabelComponent
    public static class SharedTriageActions {
        // Placeholder for study: EmbabelComponent contributes actions without being an agent.
    }

    @Action(description = "Score urgency of the inbound message", value = 1.0)
    public UrgencyScore score(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm().createObject(
                """
                        Score urgency 0.0-1.0 for this support message and explain:
                        %s
                        """.formatted(userInput.getContent()),
                UrgencyScore.class
        );
    }

    @AchievesGoal(description = "Route the ticket to a queue")
    @Action(description = "Choose a support queue from the urgency score", value = 0.5)
    public TriageDecision route(UserInput userInput, UrgencyScore urgency, Ai ai) {
        return ai.withDefaultLlm().createObject(
                """
                        Route this ticket. urgency=%s rationale=%s
                        Message: %s
                        Choose queue one of: BILLING, TECH, VIP, GENERAL
                        """.formatted(urgency.score(), urgency.rationale(), userInput.getContent()),
                TriageDecision.class
        );
    }
}
