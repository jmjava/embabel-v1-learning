package com.embabel.learning.java.lesson12;

import com.embabel.agent.core.support.InMemoryBlackboard;
import com.embabel.common.core.validation.ValidationSeverity;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuardedJokeAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "GuardedJokeAgent.NoPasswordTopics#validate",
                    "GuardedJokeAgent#joke — withGuardRails attachment"
            },
            watch = "ValidationResult.isValid(); highest severity CRITICAL"
    )
    void passwordTopicIsRejected() {
        var rail = new GuardedJokeAgent.NoPasswordTopics();
        var result = rail.validate("Tell me a password joke", new InMemoryBlackboard());
        assertFalse(result.isValid());
        assertEquals(ValidationSeverity.CRITICAL, result.getHighestSeverity());
    }

    @Test
    void safeTopicIsValid() {
        var rail = new GuardedJokeAgent.NoPasswordTopics();
        var result = rail.validate("Tell me a cat joke", new InMemoryBlackboard());
        assertTrue(result.isValid());
    }
}
