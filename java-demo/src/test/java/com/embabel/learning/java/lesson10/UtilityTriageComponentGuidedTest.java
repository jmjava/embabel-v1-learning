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
            breakpoints = {"UtilityTriageComponent#route"},
            watch = "urgency, rationale, message, and queue names in the route prompt"
    )
    void routePromptKeepsScoreMessageAndQueues() {
        var agent = new UtilityTriageComponent();
        var ctx = FakeOperationContext.create();
        var message = "Card charged twice for the annual plan";
        var urgency = new UtilityTriageComponent.UrgencyScore(0.9, "duplicate billing");
        ctx.expectResponse(new UtilityTriageComponent.TriageDecision("BILLING", "duplicate charge"));

        var decision = agent.route(new UserInput(message), urgency, ctx.ai());

        assertEquals("BILLING", decision.queue());
        var prompt = ctx.getLlmInvocations().getFirst().getMessages().getFirst().getContent();
        assertTrue(prompt.contains("0.9"), prompt);
        assertTrue(prompt.contains("duplicate billing"), prompt);
        assertTrue(prompt.contains(message), prompt);
        assertTrue(prompt.contains("BILLING"), prompt);
        assertTrue(prompt.contains("TECH"), prompt);
        assertTrue(prompt.contains("VIP"), prompt);
        assertTrue(prompt.contains("GENERAL"), prompt);
    }
}
