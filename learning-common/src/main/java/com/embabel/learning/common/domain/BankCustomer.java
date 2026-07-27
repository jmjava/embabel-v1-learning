package com.embabel.learning.common.domain;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.springframework.ai.tool.annotation.Tool;

/**
 * Domain object that exposes selective behavior as LLM tools.
 * <p>
 * <b>Nuance:</b> {@link Tool} methods are <em>not</em> automatically available to every
 * LLM call. An action must pass this instance via {@code withToolObject(customer)}.
 * That is the heart of Embabel's Domain Integrated Context Engineering (DICE).
 *
 * @param id            customer id
 * @param name          customer name
 * @param balance       settled balance
 * @param pendingAmount pending transactions
 */
public record BankCustomer(
        Long id,
        String name,
        float balance,
        float pendingAmount
) {

    /**
     * Tool the model may call while answering a support query.
     *
     * @param includePending whether to include pending amounts
     * @return computed balance
     */
    @Tool(description = "Find the balance of a customer; set includePending=true to add pending amounts")
    public float balance(boolean includePending) {
        return includePending ? balance + pendingAmount : balance;
    }

    /**
     * Intentionally NOT annotated with {@link Tool} — demonstrates selective exposure.
     * Domain methods without {@code @Tool} stay private to application code.
     */
    public String internalRiskNote() {
        return pendingAmount > balance * 0.5f ? "HIGH_PENDING" : "NORMAL";
    }
}
