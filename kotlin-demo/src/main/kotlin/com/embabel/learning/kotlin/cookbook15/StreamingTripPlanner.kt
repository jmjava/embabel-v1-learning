package com.embabel.learning.kotlin.cookbook15

import com.embabel.agent.api.common.Ai
import com.embabel.agent.api.streaming.StreamingPromptRunnerBuilder
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.TripOption
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Lesson 20 (Kotlin): Embabel 1.5 typed object streaming.
 *
 * Counterpart: `com.embabel.learning.java.cookbook15.StreamingTripPlanner`
 */
@Lesson(
    value = LessonOrder.COOKBOOK_STREAMING,
    title = "Cookbook 1.5 — streaming objects",
    guideRefs = ["Cookbook: Streaming"],
    counterpart = "com.embabel.learning.java.cookbook15.StreamingTripPlanner",
)
@Component
class StreamingTripPlanner(private val ai: Ai) {

    fun streamOptions(userText: String): List<TripOption> {
        val runner = ai.withDefaultLlm().withId("cookbook15-streaming")
        check(runner.supportsStreaming()) {
            "Current PromptRunner does not support streaming(); use a 1.5 model provider"
        }
        val options = CopyOnWriteArrayList<TripOption>()
        StreamingPromptRunnerBuilder(runner)
            .streaming()
            .withPrompt(
                """
                Return exactly three distinct itinerary options for:
                $userText
                Emit each option as a separate JSON object with label and text.
                """.trimIndent(),
            )
            .createObjectStream(TripOption::class.java)
            .timeout(Duration.ofSeconds(120))
            .doOnNext { options.add(it) }
            .blockLast(Duration.ofSeconds(120))
        return options.toList()
    }
}
