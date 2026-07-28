package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PlannerType;
import com.embabel.agent.domain.io.UserInput;

/**
 * Starter: Supervisor planner — LLM selects typed actions.
 * Action descriptions matter.
 */
@Agent(
        description = "TODO: supervisor orchestration description",
        planner = PlannerType.SUPERVISOR
)
public class SupervisorAgent {

    public record Plan(String outline) {
    }

    public record Result(String body) {
    }

    @Action(description = "Create an outline from the user request")
    public Plan plan(UserInput userInput, Ai ai) {
        return ai.withAutoLlm().createObject(
                "Outline for: " + userInput.getContent(),
                Plan.class
        );
    }

    @AchievesGoal(description = "Final result delivered")
    @Action(description = "Expand the outline into the final result")
    public Result finish(Plan plan, UserInput userInput, Ai ai) {
        return ai.withAutoLlm().createObject(
                """
                        Expand outline into a result.
                        Outline: %s
                        Request: %s
                        """.formatted(plan.outline(), userInput.getContent()),
                Result.class
        );
    }
}
