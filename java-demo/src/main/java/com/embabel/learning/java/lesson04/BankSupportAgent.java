package com.embabel.learning.java.lesson04;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.BankCustomer;
import com.embabel.learning.common.domain.SupportInput;
import com.embabel.learning.common.domain.SupportOutput;
import com.embabel.learning.common.service.InMemoryCustomerRepository;

/**
 * Lesson 04 (Java): bind a domain object as an LLM tool.
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>Repository lookup is application code; only the LLM step gets the tool</li>
 *   <li>{@link BankCustomer#internalRiskNote()} is intentionally not a tool</li>
 *   <li>Starting input is a typed record, not {@code UserInput} — goals can start from
 *       structured types when exported with {@code startingInputTypes}</li>
 *   <li>Uses {@link OperationContext} instead of {@code Ai} to show both styles</li>
 * </ul>
 */
@Lesson(
        value = LessonOrder.DOMAIN_TOOLS,
        title = "Domain objects as tools (DICE)",
        guideRefs = {"4.4 Domain Objects", "4.4.2 Selective Tool Exposure"},
        counterpart = "com.embabel.learning.kotlin.lesson04.BankSupportAgent"
)
@Agent(description = "Customer support agent that uses a domain customer as a tool")
public class BankSupportAgent {

    private final InMemoryCustomerRepository customers;

    public BankSupportAgent(InMemoryCustomerRepository customers) {
        this.customers = customers;
    }

    @AchievesGoal(description = "Help a bank customer with their query")
    @Action
    public SupportOutput supportCustomer(SupportInput input, OperationContext context) {
        BankCustomer customer = customers.findById(input.customerId());
        if (customer == null) {
            return new SupportOutput("Customer not found with this id", false, 0);
        }
        // Application-only method — LLM cannot call this unless annotated & exposed
        String note = customer.internalRiskNote();

        return context.ai()
                .withDefaultLlm()
                .withToolObject(customer)
                .createObject(
                        """
                                You are a bank support agent. Advise the customer and judge risk (0-10).
                                Use tools to check balances when needed.
                                Reply using the customer's name "%s".
                                Internal risk note (for you): %s
                                Query: [%s]
                                """.formatted(customer.name(), note, input.query()),
                        SupportOutput.class
                );
    }
}
