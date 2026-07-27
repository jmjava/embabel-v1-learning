package com.embabel.learning.kotlin.lesson13

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.StuckHandler
import com.embabel.agent.api.common.StuckHandlerResult
import com.embabel.agent.api.common.StuckHandlingResultCode
import com.embabel.agent.core.AgentProcess
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder

/**
 * Lesson 13 (Kotlin): StuckHandler recovery.
 */
@Lesson(
    value = LessonOrder.STUCK,
    title = "Stuck recovery",
    guideRefs = ["4.6.12 Implementing the StuckHandler interface"],
    counterpart = "com.embabel.learning.java.lesson13.SelfUnstickingAgent",
)
@Agent(description = "Demo agent that unsticks itself by adding a missing Dog")
class SelfUnstickingAgent : StuckHandler {

    data class Dog(val name: String)
    data class Frog(val name: String)

    @AchievesGoal(description = "Transform dog into frog")
    @Action
    fun toFrog(dog: Dog): Frog = Frog(dog.name)

    override fun handleStuck(agentProcess: AgentProcess): StuckHandlerResult {
        agentProcess.addObject(Dog("Duke"))
        return StuckHandlerResult(
            message = "Seeded Dog('Duke') and requesting replan",
            handler = this,
            code = StuckHandlingResultCode.REPLAN,
            agentProcess = agentProcess,
        )
    }
}
