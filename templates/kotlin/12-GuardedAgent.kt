package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.validation.guardrails.UserInputGuardRail
import com.embabel.agent.core.Blackboard
import com.embabel.agent.domain.io.UserInput
import com.embabel.common.core.validation.ValidationError
import com.embabel.common.core.validation.ValidationResult
import com.embabel.common.core.validation.ValidationSeverity

/**
 * Starter: attach a CRITICAL user-input guardrail.
 */
@Agent(description = "TODO: safe generation with guardrails")
class GuardedAgent {

    data class Joke(val text: String)

    class NoPasswordTopics : UserInputGuardRail {
        override val name: String = "NoPasswordTopics"
        override val description: String = "Rejects password topics"

        override fun validate(input: String, blackboard: Blackboard): ValidationResult {
            if (input.lowercase().contains("password")) {
                return ValidationResult(
                    isValid = false,
                    errors = listOf(
                        ValidationError(
                            "password-topic",
                            "Password topics are not allowed",
                            ValidationSeverity.CRITICAL,
                        ),
                    ),
                )
            }
            return ValidationResult.VALID
        }
    }

    @AchievesGoal(description = "Safe joke produced")
    @Action
    fun joke(userInput: UserInput, context: OperationContext): Joke =
        context.ai()
            .withDefaultLlm()
            .withGuardRails(NoPasswordTopics())
            .createObject("Short clean joke about: ${userInput.content}")
}
