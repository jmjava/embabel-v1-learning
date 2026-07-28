package com.example.embabel.starter

import com.embabel.agent.api.common.Ai
import jakarta.validation.constraints.Pattern
import org.springframework.stereotype.Component

/**
 * Starter: use Embabel Ai without an @Agent.
 * Copy → rename → inject into shell/controllers.
 *
 * Kotlin nuance: validation needs `@field:Pattern` on data-class properties.
 */
@Component
class InjectedAiComponent(
    private val ai: Ai,
) {

    data class InventedThing(
        val name: String,
        @field:Pattern(regexp = ".+", message = "kind required")
        val kind: String,
    )

    fun invent(topic: String): InventedThing =
        ai.withDefaultLlm()
            .withId("invent-thing")
            .creating(InventedThing::class.java)
            .withExample("good", InventedThing("Fluffox", "creature"))
            .fromPrompt(
                """
                Invent a fictional thing related to: $topic
                """.trimIndent(),
            )
}
