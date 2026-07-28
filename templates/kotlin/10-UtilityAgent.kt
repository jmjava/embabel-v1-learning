package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.PlannerType
import com.embabel.agent.api.common.createObject
import com.embabel.agent.domain.io.UserInput

/**
 * Starter: Utility planner — greedy highest net value.
 * Design clear termination via @AchievesGoal.
 */
@Agent(
    description = "TODO: triage / opportunistic gathering",
    planner = PlannerType.UTILITY,
)
class UtilityAgent {

    data class Signal(val score: Double, val rationale: String)
    data class Decision(val queue: String, val summary: String)

    @Action(description = "Score urgency", value = 1.0)
    fun score(userInput: UserInput, context: OperationContext): Signal =
        context.ai().withDefaultLlm().createObject(
            "Score urgency 0-1 for: ${userInput.content}",
        )

    @AchievesGoal(description = "Ticket routed")
    @Action(description = "Choose a queue", value = 0.5)
    fun route(userInput: UserInput, signal: Signal, context: OperationContext): Decision =
        context.ai().withDefaultLlm().createObject(
            """
            Route ticket. score=${signal.score} rationale=${signal.rationale} message=${userInput.content}
            Queue one of: BILLING, TECH, VIP, GENERAL
            """.trimIndent(),
        )
}
