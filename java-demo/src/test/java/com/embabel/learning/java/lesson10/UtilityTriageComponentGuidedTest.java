package com.embabel.learning.java.lesson10;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilityTriageComponentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {"UtilityTriageComponent#score"},
            watch = "support message stays in the urgency prompt"
    )
    void scorePromptKeepsTheSupportMessage() {
        var agent = new UtilityTriageComponent();
        var ctx = FakeOperationContext.create();
        var message = "Card charged twice for the annual plan";
        ctx.expectResponse(new UtilityTriageComponent.UrgencyScore(0.9, "duplicate billing"));

        var urgency = agent.score(new UserInput(message), ctx.ai());

        assertEquals(0.9, urgency.score(), 0.001);
        assertEquals("duplicate billing", urgency.rationale());
        var prompt = ctx.getLlmInvocations().getFirst().getMessages().getFirst().getContent();
        assertTrue(prompt.contains("Score urgency 0.0-1.0"), prompt);
        assertTrue(prompt.contains(message), prompt);
    }
}
