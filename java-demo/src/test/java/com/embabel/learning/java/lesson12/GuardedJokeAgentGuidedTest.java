package com.embabel.learning.java.lesson12;

import com.embabel.agent.core.support.InMemoryBlackboard;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
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

    @Test
    @DebugGuide(
            breakpoints = {"GuardedJokeAgent#joke"},
            watch = "user topic stays in the joke prompt"
    )
    void jokePromptKeepsTheTopic() {
        var ctx = FakeOperationContext.create();
        var topic = "a cat who files taxes";
        ctx.expectResponse(new GuardedJokeAgent.Joke("The cat itemized its naps."));

        var joke = new GuardedJokeAgent().joke(new UserInput(topic), ctx.ai());

        assertEquals("The cat itemized its naps.", joke.text());
        var prompt = ctx.getLlmInvocations().getFirst().getPrompt();
        assertTrue(prompt.contains("Tell a short clean joke about: "), prompt);
        assertTrue(prompt.contains(topic), prompt);
    }
}
