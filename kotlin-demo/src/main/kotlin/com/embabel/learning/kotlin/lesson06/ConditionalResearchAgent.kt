package com.embabel.learning.kotlin.lesson06

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.annotation.Condition
import com.embabel.agent.api.annotation.RequireNameMatch
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.Critique
import com.embabel.learning.common.domain.FinalReport
import com.embabel.learning.common.domain.ResearchDraft

/**
 * Lesson 06 (Kotlin): conditions + named bindings.
 *
 * Annotation arrays use Kotlin list syntax: `pre = [DRAFT_BAD]`.
 */
@Lesson(
    value = LessonOrder.CONDITIONS,
    title = "Conditions and named bindings",
    guideRefs = ["4.6.4 The @Condition annotation", "4.6.6 Binding by name"],
    counterpart = "com.embabel.learning.java.lesson06.ConditionalResearchAgent",
)
@Agent(description = "Research a topic, critique it, and rewrite until acceptable")
class ConditionalResearchAgent {

    companion object {
        const val DRAFT_OK = "draftSatisfactory"
        const val DRAFT_BAD = "draftUnsatisfactory"
    }

    @Action(outputBinding = "fastDraft")
    fun draftFast(userInput: UserInput, context: OperationContext): ResearchDraft =
        context.ai().withDefaultLlm().createObject(
            "Write a brief research draft (3 sentences) about: ${userInput.content}",
        )

    @Action
    fun critique(@RequireNameMatch fastDraft: ResearchDraft, context: OperationContext): Critique =
        context.ai().withDefaultLlm().createObject(
            """
            Critique this draft. Set accepted=true only if it is specific and useful.
            Draft: ${fastDraft.content}
            """.trimIndent(),
        )

    @Condition(name = DRAFT_OK)
    fun draftOk(critique: Critique): Boolean = critique.accepted

    @Condition(name = DRAFT_BAD)
    fun draftBad(critique: Critique): Boolean = !critique.accepted

    @Action(
        pre = [DRAFT_BAD],
        post = [DRAFT_OK],
        canRerun = true,
        outputBinding = "carefulDraft",
    )
    fun rewriteCarefully(
        userInput: UserInput,
        @RequireNameMatch fastDraft: ResearchDraft,
        critique: Critique,
        context: OperationContext,
    ): ResearchDraft =
        context.ai().withDefaultLlm().createObject(
            """
            Rewrite the draft carefully for topic [${userInput.content}].
            Previous draft: ${fastDraft.content}
            Feedback: ${critique.feedback}
            """.trimIndent(),
        )

    @AchievesGoal(description = "Publish an accepted research report")
    @Action(pre = [DRAFT_OK])
    fun publish(critique: Critique, userInput: UserInput, context: OperationContext): FinalReport =
        context.ai().withDefaultLlm().createObject(
            """
            Create a final report titled from the user input.
            User input: ${userInput.content}
            Critique feedback: ${critique.feedback}
            """.trimIndent(),
        )
}
