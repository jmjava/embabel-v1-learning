package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.StuckHandler
import com.embabel.agent.api.common.StuckHandlerResult
import com.embabel.agent.api.common.StuckHandlingResultCode
import com.embabel.agent.core.AgentProcess

/**
 * Starter: recover from STUCK by seeding blackboard + REPLAN.
 */
@Agent(description = "TODO: demo stuck recovery")
class StuckHandlerAgent : StuckHandler {

    data class Needed(val value: String)
    data class Out(val value: String)

    @AchievesGoal(description = "Transformed Needed → Out")
    @Action
    fun finish(needed: Needed): Out = Out(needed.value)

    override fun handleStuck(agentProcess: AgentProcess): StuckHandlerResult {
        agentProcess.addObject(Needed("seed"))
        return StuckHandlerResult(
            message = "Seeded Needed and requesting replan",
            handler = this,
            code = StuckHandlingResultCode.REPLAN,
            agentProcess = agentProcess,
        )
    }
}
