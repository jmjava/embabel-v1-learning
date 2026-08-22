package com.embabel.learning.kotlin.cookbook15

import com.embabel.agent.api.common.Ai
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.TripBrief
import org.springframework.stereotype.Component

/**
 * Lesson 18 (Kotlin): `createObjectIfPossible`.
 *
 * Counterpart: `com.embabel.learning.java.cookbook15.PossibleTripPlanner`
 */
@Lesson(
    value = LessonOrder.COOKBOOK_CREATE_IF_POSSIBLE,
    title = "Cookbook 1.5 — createObjectIfPossible",
    guideRefs = ["Cookbook: Create Object If Possible"],
    counterpart = "com.embabel.learning.java.cookbook15.PossibleTripPlanner",
)
@Component
class PossibleTripPlanner(private val ai: Ai) {

    fun planIfPossible(userText: String): TripBrief? =
        ai.withDefaultLlm()
            .withId("cookbook15-create-if-possible")
            .createObjectIfPossible(
                """
                Extract a trip brief only if the text names a destination
                and a duration or dates. Otherwise return nothing.
                Text: $userText
                """.trimIndent(),
                TripBrief::class.java,
            )

    companion object {
        fun looksSufficient(userText: String): Boolean {
            val normalized = userText.lowercase()
            val hasPlace = listOf("paris", "london", "rome", "tokyo").any { it in normalized }
            val hasWhen = listOf("day", "weekend", "friday", "sunday").any { it in normalized }
            return hasPlace && hasWhen
        }
    }
}
