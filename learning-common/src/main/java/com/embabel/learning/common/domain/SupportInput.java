package com.embabel.learning.common.domain;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Typed starting input for the bank support agent (not free-form {@code UserInput}).
 */
public record SupportInput(
        @JsonPropertyDescription("Customer ID")
        Long customerId,
        @JsonPropertyDescription("Query from the customer")
        String query
) {
}
