package com.example.embabel.starter

import com.embabel.agent.api.common.createObject
import com.embabel.agent.api.dsl.agent
import com.embabel.agent.core.Agent
import com.embabel.agent.core.CoreToolGroups
import com.embabel.agent.domain.io.UserInput
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Starter (Kotlin-only): DSL agent + Spring @Bean registration.
 *
 * Compact aggregate/transform flows; annotation agents remain first-class in both languages.
 */
data class ExtractedFacts(val facts: List<String>)
data class CheckedFacts(val summary: String)

fun starterDslAgent(): Agent = agent(
    name = "StarterDslAgent",
    description = "TODO: describe this DSL agent",
) {
    transformation<UserInput, ExtractedFacts> { context ->
        context.ai().withDefaultLlm()
            .withToolGroup(CoreToolGroups.WEB)
            .createObject(
                """
                Extract up to 3 factual assertions from:
                ${context.input.content}
                """.trimIndent(),
            )
    }

    transformation<ExtractedFacts, CheckedFacts> { context ->
        context.ai().withDefaultLlm().createObject(
            """
            Briefly verify these assertions and summarize:
            ${context.input.facts.joinToString("\n- ", prefix = "- ")}
            """.trimIndent(),
        )
    }

    goal(
        name = "dslGoalDone",
        description = "TODO: goal description",
        satisfiedBy = CheckedFacts::class,
    )
}

@Configuration
class StarterDslConfig {
    @Bean
    fun starterDsl(): Agent = starterDslAgent()
}
