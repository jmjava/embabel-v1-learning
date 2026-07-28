package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.annotation.Condition
import com.embabel.agent.api.annotation.RequireNameMatch
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.domain.io.UserInput

/**
 * Starter: conditions + named bindings + rerun rewrite.
 *
 * Annotation arrays use Kotlin list syntax: `pre = [DRAFT_BAD]`.
 */
@Agent(description = "TODO: research with critique gate")
class ConditionalResearchAgent {

    companion object {
        const val DRAFT_OK = "draftOk"
        const val DRAFT_BAD = "draftBad"
    }

    data class Draft(val content: String)
    data class Critique(val accepted: Boolean, val feedback: String)
    data class Report(val body: String)

    @Action(outputBinding = "fastDraft")
    fun draft(userInput: UserInput, context: OperationContext): Draft =
        context.ai().withDefaultLlm().createObject(
            "Brief draft about: ${userInput.content}",
        )

    @Action
    fun critique(@RequireNameMatch fastDraft: Draft, context: OperationContext): Critique =
        context.ai().withDefaultLlm().createObject(
            "Critique; accepted=true only if specific. Draft: ${fastDraft.content}",
        )

    @Condition(name = DRAFT_OK)
    fun ok(critique: Critique): Boolean = critique.accepted

    @Condition(name = DRAFT_BAD)
    fun bad(critique: Critique): Boolean = !critique.accepted

    @Action(
        pre = [DRAFT_BAD],
        post = [DRAFT_OK],
        canRerun = true,
        outputBinding = "carefulDraft",
    )
    fun rewrite(
        userInput: UserInput,
        @RequireNameMatch fastDraft: Draft,
        critique: Critique,
        context: OperationContext,
    ): Draft =
        context.ai().withDefaultLlm().createObject(
            """
            Rewrite carefully.
            Topic: ${userInput.content}
            Previous: ${fastDraft.content}
            Feedback: ${critique.feedback}
            """.trimIndent(),
        )

    @AchievesGoal(description = "Report published")
    @Action(pre = [DRAFT_OK])
    fun publish(critique: Critique, userInput: UserInput, context: OperationContext): Report =
        context.ai().withDefaultLlm().createObject(
            "Final report for '${userInput.content}' using feedback: ${critique.feedback}",
        )
}
