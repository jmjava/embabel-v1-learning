package com.embabel.learning.kotlin.lesson10

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.PlannerType
import com.embabel.agent.api.common.createObject
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder

/**
 * Lesson 10b (Kotlin): Utility planner triage.
 */
@Lesson(
    value = LessonOrder.PLANNERS,
    title = "Utility planner triage",
    guideRefs = ["4.20.1 Utility AI"],
    counterpart = "com.embabel.learning.java.lesson10.UtilityTriageComponent",
)
@Agent(
    description = "Triage an inbound support message by urgency",
    planner = PlannerType.UTILITY,
)
class UtilityTriageAgent {

    data class UrgencyScore(val score: Double, val rationale: String)
    data class TriageDecision(val queue: String, val summary: String)

    @Action(description = "Score urgency of the inbound message", value = 1.0)
    fun score(userInput: UserInput, context: OperationContext): UrgencyScore =
        context.ai().withDefaultLlm().createObject(
            "Score urgency 0.0-1.0 for this support message and explain: ${userInput.content}",
        )

    @AchievesGoal(description = "Route the ticket to a queue")
    @Action(description = "Choose a support queue from the urgency score", value = 0.5)
    fun route(userInput: UserInput, urgency: UrgencyScore, context: OperationContext): TriageDecision =
        context.ai().withDefaultLlm().createObject(
            """
            Route this ticket. urgency=${urgency.score} rationale=${urgency.rationale}
            Message: ${userInput.content}
            Choose queue one of: BILLING, TECH, VIP, GENERAL
            """.trimIndent(),
        )
}
