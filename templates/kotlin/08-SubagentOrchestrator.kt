package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.tool.Subagent
import com.embabel.agent.domain.io.UserInput
import com.embabel.common.ai.model.LlmOptions

/**
 * Starter: call another agent as a tool.
 * TODO: replace GoapWriteReviewAgent with your specialist agent class.
 */
@Agent(description = "TODO: orchestrate specialists via subagents")
class SubagentOrchestrator {

    data class Summary(val text: String)

    @AchievesGoal(description = "Summary via subagent")
    @Action
    fun compose(userInput: UserInput, context: OperationContext): Summary =
        context.promptRunner()
            .withLlm(LlmOptions.withAutoLlm())
            .withTool(Subagent.ofClass(GoapWriteReviewAgent::class.java).consuming(UserInput::class.java))
            .createObject(
                """
                Use the write/review subagent, then summarize in 2 sentences.
                Request: ${userInput.content}
                """.trimIndent(),
            )
}
