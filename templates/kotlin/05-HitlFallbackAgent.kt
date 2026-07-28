package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.common.createObjectIfPossible
import com.embabel.agent.core.hitl.fromForm
import com.embabel.agent.domain.io.UserInput

/**
 * Starter: cheap LLM extract, expensive HITL fallback.
 *
 * Kotlin uses `fromForm`; Java uses `WaitFor.formSubmission`.
 */
@Agent(description = "TODO: collect profile with HITL fallback")
class HitlFallbackAgent {

    data class Profile(val name: String, val interest: String)
    data class Welcome(val message: String)

    @Action
    fun extract(userInput: UserInput, context: OperationContext): Profile? =
        context.ai().withDefaultLlm().createObjectIfPossible(
            "Extract name and interest from: ${userInput.content}",
        )

    @Action(cost = 100.0)
    fun askUser(userInput: UserInput): Profile =
        fromForm("Please provide name and interest (input was: ${userInput.content})")

    @AchievesGoal(description = "User welcomed")
    @Action
    fun welcome(profile: Profile, context: OperationContext): Welcome =
        context.ai().withDefaultLlm().createObject(
            "One-sentence welcome for ${profile.name} who likes ${profile.interest}",
        )
}
