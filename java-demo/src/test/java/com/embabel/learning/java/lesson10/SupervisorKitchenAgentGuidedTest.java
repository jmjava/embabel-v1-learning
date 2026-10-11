package com.embabel.learning.java.lesson10;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SupervisorKitchenAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {"SupervisorKitchenAgent#takeOrder"},
            watch = "customer dish request stays in the order prompt"
    )
    void takeOrderPromptKeepsTheUserInput() {
        var ctx = FakeOperationContext.create();
        var request = "Two bowls of spicy miso ramen";
        ctx.expectResponse(new SupervisorKitchenAgent.Order("spicy miso ramen", 2));

        var order = new SupervisorKitchenAgent().takeOrder(new UserInput(request), ctx.ai());

        assertEquals("spicy miso ramen", order.dish());
        assertEquals(2, order.quantity());
        var prompt = ctx.getLlmInvocations().getFirst().getPrompt();
        assertTrue(prompt.contains("food order"), prompt);
        assertTrue(prompt.contains(request), prompt);
    }
}
