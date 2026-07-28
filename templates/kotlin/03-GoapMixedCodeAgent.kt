package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.common.createObjectIfPossible
import com.embabel.agent.domain.io.UserInput
import org.springframework.stereotype.Service

/**
 * Starter: GOAP chain mixing LLM extraction and plain code.
 */
@Agent(description = "TODO: personalized brief from user interests")
class GoapMixedCodeAgent(
    private val normalizer: InterestNormalizer,
) {

    data class Person(val name: String, val interest: String)
    data class InterestSummary(val summary: String)
    data class Brief(val name: String, val text: String)

    @Action
    fun extract(userInput: UserInput, context: OperationContext): Person? =
        context.ai().withDefaultLlm().createObjectIfPossible(
            "Extract name and interest from: ${userInput.content}",
        )

    /** Non-LLM action — code agency. */
    @Action
    fun summarize(person: Person): InterestSummary =
        InterestSummary(normalizer.normalize(person.interest))

    @AchievesGoal(description = "Brief written")
    @Action
    fun write(person: Person, summary: InterestSummary, context: OperationContext): Brief =
        context.ai().withDefaultLlm().createObject(
            """
            Write a 3-sentence brief for ${person.name} about ${summary.summary}.
            """.trimIndent(),
        )

    @Service
    class InterestNormalizer {
        fun normalize(interest: String?): String =
            interest?.trim()?.lowercase()?.takeIf { it.isNotBlank() }
                ?: "general curiosity"
    }
}
