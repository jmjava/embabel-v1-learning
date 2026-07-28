package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.PlannerType
import com.embabel.agent.api.common.createObject
import com.embabel.agent.domain.io.UserInput

/**
 * Starter: Supervisor planner — LLM selects typed actions.
 * Action descriptions matter.
 */
@Agent(
    description = "TODO: supervisor orchestration description",
    planner = PlannerType.SUPERVISOR,
)
class SupervisorAgent {

    data class Plan(val outline: String)
    data class Result(val body: String)

    @Action(description = "Create an outline from the user request")
    fun plan(userInput: UserInput, context: OperationContext): Plan =
        context.ai().withAutoLlm().createObject(
            "Outline for: ${userInput.content}",
        )

    @AchievesGoal(description = "Final result delivered")
    @Action(description = "Expand the outline into the final result")
    fun finish(plan: Plan, userInput: UserInput, context: OperationContext): Result =
        context.ai().withAutoLlm().createObject(
            """
            Expand outline into a result.
            Outline: ${plan.outline}
            Request: ${userInput.content}
            """.trimIndent(),
        )
}
