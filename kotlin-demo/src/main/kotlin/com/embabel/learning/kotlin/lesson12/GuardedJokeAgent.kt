package com.embabel.learning.kotlin.lesson12

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
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder

/**
 * Lesson 12 (Kotlin): guardrails on PromptRunner.
 */
@Lesson(
    value = LessonOrder.GUARDRAILS,
    title = "Guardrails",
    guideRefs = ["4.31 Working with Guardrails"],
    counterpart = "com.embabel.learning.java.lesson12.GuardedJokeAgent",
)
@Agent(description = "Tell a joke after validating the user topic")
class GuardedJokeAgent {

    data class Joke(val text: String)

    class NoPasswordTopics : UserInputGuardRail {
        override val name: String = "NoPasswordTopics"
        override val description: String = "Rejects prompts that ask about passwords"

        override fun validate(input: String, blackboard: Blackboard): ValidationResult {
            if (input.lowercase().contains("password")) {
                return ValidationResult(
                    isValid = false,
                    errors = listOf(
                        ValidationError(
                            "password-topic",
                            "Topics about passwords are not allowed",
                            ValidationSeverity.CRITICAL,
                        ),
                    ),
                )
            }
            return ValidationResult.VALID
        }
    }

    @AchievesGoal(description = "A safe joke was produced")
    @Action
    fun joke(userInput: UserInput, context: OperationContext): Joke =
        context.ai()
            .withDefaultLlm()
            .withGuardRails(NoPasswordTopics())
            .createObject("Tell a short clean joke about: ${userInput.content}")
}
