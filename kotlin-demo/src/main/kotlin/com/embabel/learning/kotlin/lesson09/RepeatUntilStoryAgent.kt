package com.embabel.learning.kotlin.lesson09

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.ActionContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.common.workflow.loop.RepeatUntilAcceptableBuilder
import com.embabel.agent.api.common.workflow.loop.TextFeedback
import com.embabel.agent.domain.io.UserInput
import com.embabel.common.ai.model.LlmOptions
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.Story

/**
 * Lesson 09 (Kotlin): RepeatUntilAcceptable writer/reviewer loop.
 */
@Lesson(
    value = LessonOrder.WORKFLOWS,
    title = "RepeatUntil workflow builder",
    guideRefs = ["4.7 DSL / Standard Workflows"],
    counterpart = "com.embabel.learning.java.lesson09.RepeatUntilStoryAgent",
)
@Agent(description = "Keep rewriting a story until a reviewer score threshold is met")
class RepeatUntilStoryAgent {

    @AchievesGoal(description = "An acceptable story has been produced")
    @Action
    fun rewriteUntilSatisfied(userInput: UserInput, actionContext: ActionContext): Story {
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
                    Write a creative short story inspired by: ${userInput.content}

                    Consider feedback: $feedback
                    """.trimIndent(),
                )
            }
            .withEvaluator { ctx ->
                reviewer.createObject(
                    """
                    Score creativity and relevance (0.0-1.0) and give feedback.
                    Story: ${ctx.resultToEvaluate}
                    User input: ${userInput.content}
                    """.trimIndent(),
                )
            }
            .build()
            .asSubProcess(actionContext, Story::class.java)
    }
}
