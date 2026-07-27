package com.embabel.learning.kotlin.lesson12

import com.embabel.agent.core.support.InMemoryBlackboard
import com.embabel.common.core.validation.ValidationSeverity
import com.embabel.learning.common.curriculum.DebugGuide
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GuardedJokeAgentGuidedTest {

    @Test
    @DebugGuide(
        breakpoints = ["GuardedJokeAgent.NoPasswordTopics.validate"],
        watch = "isValid=false and CRITICAL severity for password topics",
    )
    fun passwordTopicIsRejected() {
        val rail = GuardedJokeAgent.NoPasswordTopics()
        val result = rail.validate("Tell me a password joke", InMemoryBlackboard())
        assertFalse(result.isValid)
        assertEquals(ValidationSeverity.CRITICAL, result.getHighestSeverity())
    }

    @Test
    fun safeTopicIsValid() {
        val rail = GuardedJokeAgent.NoPasswordTopics()
        val result = rail.validate("Tell me a cat joke", InMemoryBlackboard())
        assertTrue(result.isValid)
    }
}
