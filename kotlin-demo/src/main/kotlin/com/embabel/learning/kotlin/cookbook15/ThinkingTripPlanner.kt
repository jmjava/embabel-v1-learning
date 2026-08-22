package com.embabel.learning.kotlin.cookbook15

import com.embabel.agent.api.common.Ai
import com.embabel.common.core.thinking.ThinkingResponse
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.TripBrief
import org.springframework.stereotype.Component

/**
 * Lesson 19 (Kotlin): Embabel 1.5 thinking traces.
 *
 * Counterpart: `com.embabel.learning.java.cookbook15.ThinkingTripPlanner`
 */
@Lesson(
    value = LessonOrder.COOKBOOK_THINKING,
    title = "Cookbook 1.5 — thinking traces",
    guideRefs = ["Cookbook: Thinking"],
    counterpart = "com.embabel.learning.java.cookbook15.ThinkingTripPlanner",
)
@Component
class ThinkingTripPlanner(private val ai: Ai) {

    fun planWithThinking(userText: String): ThinkingResponse<TripBrief> {
        val runner = ai.withDefaultLlm().withId("cookbook15-thinking")
        check(runner.supportsThinking()) {
            "Current PromptRunner does not support thinking(); use a 1.5 model provider"
        }
        return runner
            .withSystemPrompt(REASONING_SYSTEM_PROMPT)
            .thinking()
            .createObject(
                "Create a short, balanced travel plan for:\n$userText",
                TripBrief::class.java,
            )
    }

    companion object {
        const val REASONING_SYSTEM_PROMPT = """
You are a travel assistant.
Provide reasoning inside <decision_reasoning>...</decision_reasoning>.
Keep reasoning to 3-5 bullets covering time, risk, and trade-offs.
"""
    }
}
