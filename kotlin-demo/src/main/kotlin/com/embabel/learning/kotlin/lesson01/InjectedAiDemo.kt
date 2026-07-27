package com.embabel.learning.kotlin.lesson01

import com.embabel.agent.api.common.Ai
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import jakarta.validation.constraints.Pattern
import org.springframework.stereotype.Component

/**
 * Lesson 01 (Kotlin): inject [Ai] into a Spring component.
 *
 * **Java vs Kotlin nuance:** validation on data-class properties needs `@field:Pattern`.
 * Java records accept `@Pattern` directly on components.
 *
 * Counterpart: `com.embabel.learning.java.lesson01.InjectedAiDemo`
 */
@Lesson(
    value = LessonOrder.INJECTED_AI,
    title = "Injected Ai without an Agent",
    guideRefs = ["2.4 Adding a Little AI to Your Application"],
    counterpart = "com.embabel.learning.java.lesson01.InjectedAiDemo",
)
@Component
class InjectedAiDemo(private val ai: Ai) {

    data class MagicalAnimal(
        val name: String,
        @field:Pattern(regexp = ".*ox.*", message = "Species must contain 'ox'")
        val species: String,
    )

    /** Breakpoint: the `fromPrompt` call. */
    fun inventAnimal(): MagicalAnimal =
        ai.withDefaultLlm()
            .withId("lesson01-invent-animal")
            .creating(MagicalAnimal::class.java)
            .withExample("good", MagicalAnimal("Fluffox", "Magicox"))
            .withExample("bad: fails validation", MagicalAnimal("Sparky", "Dragon"))
            .fromPrompt(
                """
                You woke up in a magical forest.
                Invent a fictional animal with a name and species.
                """.trimIndent(),
            )
}
