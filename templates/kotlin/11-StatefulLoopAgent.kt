package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.annotation.State
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.domain.io.UserInput

/**
 * Starter: @State machine loop with clearBlackboard on refine.
 *
 * Keep state classes nested/non-inner (no `inner`) so they serialize cleanly.
 */
@Agent(description = "TODO: iterative draft refinement via states")
class StatefulLoopAgent {

    sealed interface LoopOutcome

    @State
    data class Drafting(val topic: String, val draft: String, val iteration: Int) : LoopOutcome {
        @Action(clearBlackboard = true)
        fun refine(context: OperationContext): LoopOutcome {
            if (iteration >= 2) return Done(topic, draft)
            val improved = context.ai().withDefaultLlm().generateText(
                "Improve draft (iteration ${iteration + 1}) about $topic:\n$draft",
            )
            return Drafting(topic, improved, iteration + 1)
        }
    }

    @State
    data class Done(val topic: String, val draft: String) : LoopOutcome {
        @AchievesGoal(description = "Draft finished")
        @Action
        fun finish(): Report = Report(topic, draft)
    }

    data class Report(val topic: String, val finalDraft: String)

    @Action
    fun start(userInput: UserInput, context: OperationContext): Drafting {
        val draft = context.ai().withDefaultLlm().generateText(
            "Rough 2-sentence draft about: ${userInput.content}",
        )
        return Drafting(userInput.content, draft, 0)
    }
}
