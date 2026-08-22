package com.embabel.learning.kotlin.cookbook15

import com.embabel.agent.api.annotation.LlmTool
import com.embabel.agent.api.common.Ai
import com.embabel.agent.api.tool.callback.LogLevel
import com.embabel.agent.api.tool.callback.ToolCallLoggingInspector
import com.embabel.chat.SystemMessage
import com.embabel.chat.UserMessage
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.TripBrief
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Lesson 21 (Kotlin): fromMessages + tool inspectors.
 *
 * Counterpart: `com.embabel.learning.java.cookbook15.MessageAndToolTripPlanner`
 */
@Lesson(
    value = LessonOrder.COOKBOOK_MESSAGES_AND_TOOLS,
    title = "Cookbook 1.5 — messages, tools, inspectors",
    guideRefs = ["Cookbook: Prompt Contributors", "Cookbook: Tool Call"],
    counterpart = "com.embabel.learning.java.cookbook15.MessageAndToolTripPlanner",
)
@Component
class MessageAndToolTripPlanner(private val ai: Ai) {

    fun planFromMessages(destinationCue: String, extras: String): TripBrief {
        val messages = listOf(
            SystemMessage("You are a travel assistant."),
            UserMessage("Create a structured travel plan for $destinationCue."),
            UserMessage(extras),
        )
        return ai.withDefaultLlm()
            .withId("cookbook15-from-messages")
            .creating(TripBrief::class.java)
            .fromMessages(messages)
    }

    fun planWithTools(destination: String): TripBrief =
        ai.withDefaultLlm()
            .withId("cookbook15-tools")
            .withToolObject(TravelLookupTools())
            .withToolCallInspectors(ToolCallLoggingInspector(LogLevel.INFO, logger))
            .creating(TripBrief::class.java)
            .fromPrompt(
                """
                Plan a 3-day trip to $destination.
                Use tools to check weather and top attractions.
                """.trimIndent(),
            )

    class TravelLookupTools {
        @LlmTool(description = "Get current weather for a destination.")
        fun getWeather(destination: String): String =
            "Sunny, 24C, low humidity — good outdoor weather in $destination."

        @LlmTool(description = "Get top attractions for a destination.")
        fun getTopAttractions(destination: String): String =
            "Highlights in $destination: a landmark, a museum, a river walk."
    }

    companion object {
        private val logger = LoggerFactory.getLogger(MessageAndToolTripPlanner::class.java)
    }
}
