package com.example.embabel.starter

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import org.springframework.ai.tool.annotation.Tool

/**
 * Starter: domain object as LLM tool (DICE).
 */
@Agent(description = "TODO: support agent using domain tools")
class DomainToolSupportAgent {

    data class Customer(
        val id: Long,
        val name: String,
        val balance: Float,
        val pending: Float,
    ) {
        @Tool(description = "Get balance; includePending=true adds pending amounts")
        fun balance(includePending: Boolean): Float =
            if (includePending) balance + pending else balance

        /** Not a tool — stays app-private. */
        fun internalNote(): String =
            if (pending > balance) "HIGH_PENDING" else "OK"
    }

    data class SupportInput(
        @param:JsonPropertyDescription("Customer id")
        val customerId: Long,
        @param:JsonPropertyDescription("Customer question")
        val query: String,
    )

    data class SupportOutput(val advice: String, val escalate: Boolean, val risk: Int)

    /** TODO: replace with real repository. */
    private fun find(id: Long): Customer =
        Customer(id, "Ada", 1000f, 50f)

    @AchievesGoal(description = "Customer supported")
    @Action
    fun support(input: SupportInput, context: OperationContext): SupportOutput {
        val customer = find(input.customerId)
        val note = customer.internalNote()
        return context.ai()
            .withDefaultLlm()
            .withToolObject(customer)
            .createObject(
                """
                Advise ${customer.name}. Use balance tools when needed.
                Internal note: $note
                Query: ${input.query}
                """.trimIndent(),
            )
    }
}
