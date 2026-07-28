package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.ActionContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.common.workflow.loop.RepeatUntilAcceptableBuilder
import com.embabel.agent.api.common.workflow.loop.TextFeedback
import com.embabel.agent.domain.io.UserInput
import com.embabel.common.ai.model.LlmOptions

/**
 * Starter: RepeatUntilAcceptable writer/reviewer loop.
 */
@Agent(description = "TODO: rewrite until score threshold met")
class RepeatUntilAgent {

    data class Story(val text: String)

    @AchievesGoal(description = "Acceptable story produced")
    @Action
    fun rewrite(userInput: UserInput, actionContext: ActionContext): Story {
        val writer = actionContext.ai().withLlm(LlmOptions.withAutoLlm())
        val reviewer = actionContext.ai().withLlm(LlmOptions.withAutoLlm())

        return RepeatUntilAcceptableBuilder
            .returning(Story::class.java)
            .withMaxIterations(5)
            .withScoreThreshold(0.8)
            .repeating { ctx ->
                val feedback = ctx.lastFeedbackOr("")
                writer.createObject(
                    """
                    Write a short story inspired by: ${userInput.content}
                    Feedback: $feedback
                    """.trimIndent(),
                )
            }
            .withEvaluator { ctx ->
                reviewer.createObject(
                    """
                    Score 0.0-1.0 and give feedback.
                    Story: ${ctx.resultToEvaluate}
                    """.trimIndent(),
                )
            }
            .build()
            .asSubProcess(actionContext, Story::class.java)
    }
}
