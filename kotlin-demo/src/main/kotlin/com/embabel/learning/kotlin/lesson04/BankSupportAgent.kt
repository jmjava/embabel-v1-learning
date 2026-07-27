package com.embabel.learning.kotlin.lesson04

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.SupportInput
import com.embabel.learning.common.domain.SupportOutput
import com.embabel.learning.common.service.InMemoryCustomerRepository

/**
 * Lesson 04 (Kotlin): domain object tools via `withToolObject`.
 */
@Lesson(
    value = LessonOrder.DOMAIN_TOOLS,
    title = "Domain objects as tools (DICE)",
    guideRefs = ["4.4 Domain Objects"],
    counterpart = "com.embabel.learning.java.lesson04.BankSupportAgent",
)
@Agent(description = "Customer support agent that uses a domain customer as a tool")
class BankSupportAgent(
    private val customers: InMemoryCustomerRepository,
) {

    @AchievesGoal(description = "Help a bank customer with their query")
    @Action
    fun supportCustomer(input: SupportInput, context: OperationContext): SupportOutput {
        val customer = customers.findById(input.customerId)
            ?: return SupportOutput("Customer not found with this id", false, 0)
        val note = customer.internalRiskNote()
        return context.ai()
            .withDefaultLlm()
            .withToolObject(customer)
            .createObject(
                """
                You are a bank support agent. Advise the customer and judge risk (0-10).
                Use tools to check balances when needed.
                Reply using the customer's name "${customer.name}".
                Internal risk note (for you): $note
                Query: [${input.query}]
                """.trimIndent(),
            )
    }
}
