package com.embabel.learning.kotlin.lesson05

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.common.createObjectIfPossible
import com.embabel.agent.core.hitl.fromForm
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.PersonProfile

/**
 * Lesson 05 (Kotlin): HITL via `fromForm` extension (Java uses `WaitFor.formSubmission`).
 */
@Lesson(
    value = LessonOrder.HITL,
    title = "Human-in-the-loop with WaitFor",
    guideRefs = ["4.19.9 Human-in-the-Loop with WaitFor"],
    counterpart = "com.embabel.learning.java.lesson05.HitlProfileAgent",
)
@Agent(description = "Collect a person profile, falling back to a human form if needed")
class HitlProfileAgent {

    data class WelcomeNote(val message: String)

    @Action
    fun extractProfile(userInput: UserInput, context: OperationContext): PersonProfile? =
        context.ai().withDefaultLlm().createObjectIfPossible(
            "Extract name and interest from: ${userInput.content}",
        )

    @Action(cost = 100.0)
    fun askUserForProfile(userInput: UserInput): PersonProfile =
        fromForm("Please provide name and interest (input was: ${userInput.content})")

    @AchievesGoal(description = "User has been welcomed with a personalized note")
    @Action
    fun welcome(profile: PersonProfile, context: OperationContext): WelcomeNote =
        context.ai().withDefaultLlm().createObject(
            "Write a one-sentence welcome for ${profile.name} who likes ${profile.interest}.",
        )
}
