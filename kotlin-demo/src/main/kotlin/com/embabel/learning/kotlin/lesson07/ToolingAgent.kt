package com.embabel.learning.kotlin.lesson07

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
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import java.time.Instant

/**
 * Lesson 07 (Kotlin): tool groups + custom `@LlmTool`.
 */
@Lesson(
    value = LessonOrder.TOOLS,
    title = "Tool groups and custom tools",
    guideRefs = ["4.9 Tools"],
    counterpart = "com.embabel.learning.java.lesson07.ToolingAgent",
)
@Agent(description = "Answer a research question using tools and a diagnostic custom tool")
class ToolingAgent {

    data class ToolingAnswer(val answer: String, val diagnostics: String)

    class ClockTools {
        @LlmTool(
            name = "server_time",
            description = "Returns the current server time in ISO-8601 format",
            returnDirect = false,
        )
        fun serverTime(
            @LlmTool.Param(description = "Optional reason for the call", required = false)
            reason: String?,
            context: ToolCallContext,
        ): String = "${Instant.now()} reason=$reason metaKeys=${context.toMap().keys}"
    }

    @AchievesGoal(description = "Produce an answer that used tools")
    @Action
    fun answer(userInput: UserInput, context: OperationContext): ToolingAnswer =
        context.ai()
            .withDefaultLlm()
            .withId("lesson07-tooling")
            .withToolGroup(CoreToolGroups.WEB)
            .withTool(Tool.fromInstance(ClockTools()).first())
            .createObject(
                """
                IMPORTANT: First call server_time to record when you started.
                Then answer the user question, using web tools if needed.

                Question: ${userInput.content}

                Return diagnostics summarizing which tools you used.
                """.trimIndent(),
            )
}
