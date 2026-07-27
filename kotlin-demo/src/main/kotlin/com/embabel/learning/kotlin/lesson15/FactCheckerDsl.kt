package com.embabel.learning.kotlin.lesson15

import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.dsl.agent
import com.embabel.agent.core.Agent
import com.embabel.agent.core.CoreToolGroups
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Lesson 15 (Kotlin-only): DSL agent definition.
 *
 * Annotation agents are great in both languages; the Kotlin DSL shines for compact
 * aggregate/transform flows registered as Spring `@Bean`s.
 */

data class FactualAssertions(val assertions: List<String>)
data class FactCheckResult(val checked: List<String>, val summary: String)

fun factCheckerDslAgent(): Agent = agent(
    name = "KotlinFactCheckerDsl",
    description = "Extract and check factual assertions from user content",
) {
    transformation<UserInput, FactualAssertions> { context ->
        context.ai().withDefaultLlm()
            .withToolGroup(CoreToolGroups.WEB)
            .createObject(
                """
                Extract up to 3 factual assertions from:
                ${context.input.content}
                """.trimIndent(),
            )
    }

    transformation<FactualAssertions, FactCheckResult> { context ->
        context.ai().withDefaultLlm().createObject(
            """
            Briefly verify these assertions and summarize:
            ${context.input.assertions.joinToString("\n- ", prefix = "- ")}
            """.trimIndent(),
        )
    }

    goal(
        name = "factCheckingDone",
        description = "Content was fact checked",
        satisfiedBy = FactCheckResult::class,
    )
}

@Lesson(
    value = LessonOrder.KOTLIN_DSL,
    title = "Kotlin DSL agents",
    guideRefs = ["4.7 DSL"],
    counterpart = "",
)
@Configuration
class FactCheckerDslConfig {
    @Bean
    fun kotlinFactCheckerDsl(): Agent = factCheckerDslAgent()
}
