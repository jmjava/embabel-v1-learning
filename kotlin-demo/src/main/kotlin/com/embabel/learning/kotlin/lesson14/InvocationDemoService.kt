package com.embabel.learning.kotlin.lesson14

import com.embabel.agent.api.invocation.AgentInvocation
import com.embabel.agent.core.AgentPlatform
import com.embabel.agent.core.ProcessOptions
import com.embabel.agent.core.Verbosity
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.ReviewedStory
import org.springframework.stereotype.Service

/**
 * Lesson 14 (Kotlin): AgentInvocation + ProcessOptions.
 */
@Lesson(
    value = LessonOrder.INVOCATION,
    title = "AgentInvocation and ProcessOptions",
    guideRefs = ["4.18 Invoking Embabel Agents", "4.16 ProcessOptions"],
    counterpart = "com.embabel.learning.java.lesson14.InvocationDemoService",
)
@Service
class InvocationDemoService(
    private val agentPlatform: AgentPlatform,
) {
    fun writeAndReview(prompt: String): ReviewedStory {
        val options = ProcessOptions.DEFAULT.withVerbosity(
            Verbosity.DEFAULT.withShowPrompts(true),
        )
        val invocation: AgentInvocation<ReviewedStory> = AgentInvocation
            .builder(agentPlatform)
            .options(options)
            .build(ReviewedStory::class.java)
        return invocation.invoke(UserInput(prompt))
    }
}
