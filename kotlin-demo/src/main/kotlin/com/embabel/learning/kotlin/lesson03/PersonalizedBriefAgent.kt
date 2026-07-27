package com.embabel.learning.kotlin.lesson03

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.annotation.Export
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.common.createObjectIfPossible
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.PersonProfile
import com.embabel.learning.common.service.InterestService

/**
 * Lesson 03 (Kotlin): multi-action GOAP with nullable extraction.
 *
 * Kotlin's nullable return type `PersonProfile?` is a natural fit for `createObjectIfPossible`.
 */
@Lesson(
    value = LessonOrder.GOAP_MULTI_ACTION,
    title = "Multi-action GOAP with mixed code/LLM",
    guideRefs = ["1.4 Core Concepts", "4.2 Agent Process Flow"],
    counterpart = "com.embabel.learning.java.lesson03.PersonalizedBriefAgent",
)
@Agent(description = "Build a personalized brief from a person's interests")
class PersonalizedBriefAgent(
    private val interestService: InterestService,
) {

    data class InterestSummary(val summary: String)
    data class PersonalizedBrief(val name: String, val brief: String)

    @Action
    fun extractPerson(userInput: UserInput, context: OperationContext): PersonProfile? =
        context.ai().withDefaultLlm().createObjectIfPossible(
            """
            Extract a person name and primary interest from:
            ${userInput.content}
            """.trimIndent(),
        )

    @Action
    fun summarizeInterest(person: PersonProfile): InterestSummary =
        InterestSummary(interestService.summarizeInterest(person.interest))

    @AchievesGoal(
        description = "A personalized brief has been written",
        export = Export(remote = true, name = "kotlinPersonalizedBrief"),
    )
    @Action
    fun writeBrief(person: PersonProfile, summary: InterestSummary, context: OperationContext): PersonalizedBrief =
        context.ai().withDefaultLlm().createObject(
            """
            Write a 3-sentence personalized brief for ${person.name}.
            Their interest summary: ${summary.summary}
            """.trimIndent(),
        )
}
