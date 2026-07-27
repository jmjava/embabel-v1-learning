package com.embabel.learning.kotlin.lesson08

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.tool.Subagent
import com.embabel.agent.domain.io.UserInput
import com.embabel.common.ai.model.LlmOptions
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.ReviewedStory
import com.embabel.learning.kotlin.lesson02.WriteAndReviewAgent

/**
 * Lesson 08 (Kotlin): subagent handoff.
 */
@Lesson(
    value = LessonOrder.SUBAGENTS,
    title = "Subagent handoffs",
    guideRefs = ["4.9.6 Subagent: Agent Handoffs as Tools"],
    counterpart = "com.embabel.learning.java.lesson08.SubagentHandoffAgent",
)
@Agent(description = "Use a story subagent to produce an anthology blurb")
class SubagentHandoffAgent {

    data class AnthologyBlurb(val blurb: String)

    @AchievesGoal(description = "Anthology blurb created via subagent")
    @Action
    fun compose(userInput: UserInput, context: OperationContext): AnthologyBlurb =
        context.promptRunner()
            .withLlm(LlmOptions.withAutoLlm())
            .withTool(Subagent.ofClass(WriteAndReviewAgent::class.java).consuming(UserInput::class.java))
            .createObject(
                """
                The user wants an anthology blurb.
                Use the write-and-review subagent to create/review a short story first,
                then summarize it into a 2-sentence blurb.

                User request: ${userInput.content}
                """.trimIndent(),
            )

    fun subagentGoalType(): Class<ReviewedStory> = ReviewedStory::class.java
}
