package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.annotation.LlmTool
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.tool.Tool
import com.embabel.agent.api.tool.ToolCallContext
import com.embabel.agent.core.CoreToolGroups
import com.embabel.agent.domain.io.UserInput
import java.time.Instant

/**
 * Starter: tool group + custom @LlmTool.
 */
@Agent(description = "TODO: answer questions using tools")
class ToolingAgent {

    data class Answer(val text: String, val diagnostics: String)

    class ClockTools {
        @LlmTool(
            name = "server_time",
            description = "Current server time ISO-8601",
            returnDirect = false,
        )
        fun serverTime(
            @LlmTool.Param(description = "Reason", required = false)
            reason: String?,
            context: ToolCallContext,
        ): String = "${Instant.now()} reason=$reason"
    }

    @AchievesGoal(description = "Answered with tools")
    @Action
    fun answer(userInput: UserInput, context: OperationContext): Answer =
        context.ai()
            .withDefaultLlm()
            .withId("tooling-answer")
            .withToolGroup(CoreToolGroups.WEB)
            .withTool(Tool.fromInstance(ClockTools()).first())
            .createObject(
                """
                First call server_time. Then answer using web tools if needed.
                Question: ${userInput.content}
                """.trimIndent(),
            )
}
