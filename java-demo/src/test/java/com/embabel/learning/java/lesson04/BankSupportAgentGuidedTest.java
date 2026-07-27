package com.embabel.learning.java.lesson04;

import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.SupportInput;
import com.embabel.learning.common.domain.SupportOutput;
import com.embabel.learning.common.service.InMemoryCustomerRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankSupportAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "BankSupportAgent#supportCustomer — after repository lookup",
                    "BankCustomer#internalRiskNote — app-only method",
                    "withToolObject(customer) — selective tool exposure"
            },
            watch = "customer.balance / pendingAmount; SupportOutput fields"
    )
    void unknownCustomerShortCircuitsWithoutLlm() {
        var agent = new BankSupportAgent(new InMemoryCustomerRepository());
        var result = agent.supportCustomer(new SupportInput(999L, "Help"), FakeOperationContext.create());
        assertEquals("Customer not found with this id", result.advice());
        assertEquals(0, result.risk());
    }

    @Test
    @DebugGuide(
            breakpoints = {"BankSupportAgent#supportCustomer — withToolObject path"},
            watch = "FakePromptRunner tool objects contain BankCustomer tools"
    )
    void knownCustomerUsesToolObjectPath() {
        var agent = new BankSupportAgent(new InMemoryCustomerRepository());
        var ctx = FakeOperationContext.create();
        ctx.expectResponse(new SupportOutput("Ada, your settled balance looks healthy.", false, 2));
        var result = agent.supportCustomer(new SupportInput(1L, "What is my balance?"), ctx);
        assertFalse(result.blockCard());
        assertTrue(result.advice().contains("Ada"));
    }
}
