package com.example.embabel.starter;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Starter: FakeOperationContext unit test (no live LLM).
 * Copy into src/test/java and point at your agent.
 */
class GuidedUnitTest {

    @Test
    void write_promptContainsCue() {
        var context = FakeOperationContext.create();
        context.expectResponse(new GoapWriteReviewAgent.Draft("Once upon a time..."));

        var agent = new GoapWriteReviewAgent();
        agent.write(new UserInput("Tell me about a knight"), context.ai());

        var prompt = context.getPromptRunner().getLlmInvocations()
                .getFirst().getMessages().getFirst().getContent();
        assertTrue(prompt.contains("knight"), prompt);
    }
}
