package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.springframework.ai.tool.annotation.Tool;

/**
 * Starter: domain object as LLM tool (DICE).
 */
@Agent(description = "TODO: support agent using domain tools")
public class DomainToolSupportAgent {

    public record Customer(Long id, String name, float balance, float pending) {
        @Tool(description = "Get balance; includePending=true adds pending amounts")
        public float balance(boolean includePending) {
            return includePending ? balance + pending : balance;
        }

        /** Not a tool — stays app-private. */
        public String internalNote() {
            return pending > balance ? "HIGH_PENDING" : "OK";
        }
    }

    public record SupportInput(
            @JsonPropertyDescription("Customer id") Long customerId,
            @JsonPropertyDescription("Customer question") String query
    ) {
    }

    public record SupportOutput(String advice, boolean escalate, int risk) {
    }

    /** TODO: replace with real repository. */
    private Customer find(Long id) {
        return new Customer(id, "Ada", 1000f, 50f);
    }

    @AchievesGoal(description = "Customer supported")
    @Action
    public SupportOutput support(SupportInput input, OperationContext context) {
        var customer = find(input.customerId());
        var note = customer.internalNote();
        return context.ai()
                .withDefaultLlm()
                .withToolObject(customer)
                .createObject(
                        """
                                Advise %s. Use balance tools when needed.
                                Internal note: %s
                                Query: %s
                                """.formatted(customer.name(), note, input.query()),
                        SupportOutput.class
                );
    }
}
