package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PlannerType;
import com.embabel.agent.domain.io.UserInput;

/**
 * Starter: Utility planner — greedy highest net value.
 * Design clear termination via @AchievesGoal.
 */
@Agent(
        description = "TODO: triage / opportunistic gathering",
        planner = PlannerType.UTILITY
)
public class UtilityAgent {

    public record Signal(double score, String rationale) {
    }

    public record Decision(String queue, String summary) {
    }

    @Action(description = "Score urgency", value = 1.0)
    public Signal score(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm().createObject(
                "Score urgency 0-1 for: " + userInput.getContent(),
                Signal.class
        );
    }

    @AchievesGoal(description = "Ticket routed")
    @Action(description = "Choose a queue", value = 0.5)
    public Decision route(UserInput userInput, Signal signal, Ai ai) {
        return ai.withDefaultLlm().createObject(
                """
                        Route ticket. score=%s rationale=%s message=%s
                        Queue one of: BILLING, TECH, VIP, GENERAL
                        """.formatted(signal.score(), signal.rationale(), userInput.getContent()),
                Decision.class
        );
    }
}
