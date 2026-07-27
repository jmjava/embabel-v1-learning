package com.embabel.learning.kotlin.lesson15

import com.embabel.learning.common.curriculum.DebugGuide
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FactCheckerDslGuidedTest {

    @Test
    @DebugGuide(
        breakpoints = [
            "factCheckerDslAgent — DSL builder",
            "transformation blocks",
            "goal(satisfiedBy = FactCheckResult::class)",
        ],
        watch = "agent.name; goals; actions count",
    )
    fun dslAgentRegistersNameAndGoal() {
        val agent = factCheckerDslAgent()
        assertEquals("KotlinFactCheckerDsl", agent.name)
        assertTrue(agent.goals.isNotEmpty())
    }
}
