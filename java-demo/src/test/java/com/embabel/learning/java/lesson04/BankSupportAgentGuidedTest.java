package com.embabel.learning.java.lesson04;

import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.BankCustomer;
import com.embabel.learning.common.domain.SupportInput;
import com.embabel.learning.common.domain.SupportOutput;
import com.embabel.learning.common.service.InMemoryCustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.annotation.Tool;

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
        var ctx = FakeOperationContext.create();
        var result = agent.supportCustomer(new SupportInput(999L, "Help"), ctx);
        assertEquals("Customer not found with this id", result.advice());
        assertEquals(0, result.risk());
        assertTrue(ctx.getLlmInvocations().isEmpty(), "unknown id must not call the LLM");
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

        var invocation = ctx.getLlmInvocations().getFirst();
        var prompt = invocation.getMessages().getFirst().getContent();
        assertTrue(prompt.contains("Ada Lovelace"), prompt);
        assertTrue(prompt.contains("What is my balance?"), prompt);
        assertTrue(prompt.contains("NORMAL"), prompt);
        var tools = invocation.getInteraction().getTools();
        assertFalse(tools.isEmpty(), "withToolObject should expose BankCustomer tools");
        assertTrue(
                tools.stream().anyMatch(t -> t.getDefinition().getName().toLowerCase().contains("balance")),
                tools.toString()
        );
    }

    @Test
    void onlyBalanceIsExposedAsTool() throws Exception {
        assertNotNull(BankCustomer.class.getMethod("balance", boolean.class).getAnnotation(Tool.class));
        assertNull(BankCustomer.class.getMethod("internalRiskNote").getAnnotation(Tool.class));
    }

    @Test
    @DebugGuide(
            breakpoints = {
                    "BankCustomer#balance — includePending adds pendingAmount",
                    "BankCustomer#internalRiskNote — HIGH_PENDING when pending exceeds half the settled balance",
                    "BankSupportAgent#supportCustomer — Grace Hopper prompt"
            },
            watch = "settled balance stays 80; pending-inclusive balance is 220; prompt contains HIGH_PENDING"
    )
    void highPendingCustomerKeepsRiskAndPendingBalance() {
        var grace = new InMemoryCustomerRepository().findById(2L);
        assertNotNull(grace);
        assertEquals(80.0f, grace.balance(false), 0.0f);
        assertEquals(220.0f, grace.balance(true), 0.0f);
        assertEquals("HIGH_PENDING", grace.internalRiskNote());

        var atHalf = new BankCustomer(3L, "Boundary", 100.0f, 50.0f);
        assertEquals(150.0f, atHalf.balance(true), 0.0f);
        assertEquals("NORMAL", atHalf.internalRiskNote());

        var agent = new BankSupportAgent(new InMemoryCustomerRepository());
        var ctx = FakeOperationContext.create();
        ctx.expectResponse(new SupportOutput("Grace, pending activity is high.", false, 8));
        agent.supportCustomer(new SupportInput(2L, "Why is my balance low?"), ctx);

        var prompt = ctx.getLlmInvocations().getFirst().getMessages().getFirst().getContent();
        assertTrue(prompt.contains("Grace Hopper"), prompt);
        assertTrue(prompt.contains("HIGH_PENDING"), prompt);
        assertTrue(prompt.contains("Why is my balance low?"), prompt);
    }
}
