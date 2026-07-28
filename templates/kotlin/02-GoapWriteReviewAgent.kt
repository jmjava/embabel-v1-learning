package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.annotation.Export
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.create
import com.embabel.agent.domain.io.UserInput
import com.embabel.common.ai.model.LlmOptions

/**
 * Starter: classic GOAP agent — UserInput → Draft → Reviewed.
 *
 * Kotlin nuance: actions typically take [OperationContext] and use reified `create<T>()`.
 */
@Agent(description = "TODO: describe agent for selection / autonomy")
class GoapWriteReviewAgent {

    data class Draft(val text: String)
    data class Reviewed(val draft: Draft, val review: String)

    @Action
    fun write(userInput: UserInput, context: OperationContext): Draft =
        context.ai()
            .withLlm(LlmOptions.withAutoLlm().withTemperature(0.7))
            .create(
                """
                Write a short draft inspired by:
                ${userInput.content}
                """.trimIndent(),
            )

    @AchievesGoal(
        description = "TODO: goal description",
        export = Export(remote = true, name = "goapWriteReview"),
    )
    @Action
    fun review(userInput: UserInput, draft: Draft, context: OperationContext): Reviewed {
        val review = context.ai().withAutoLlm().generateText(
            """
            Review this draft for clarity and fit to the user input.
            Draft: ${draft.text}
            User input: ${userInput.content}
            """.trimIndent(),
        )
        return Reviewed(draft, review)
    }
}
