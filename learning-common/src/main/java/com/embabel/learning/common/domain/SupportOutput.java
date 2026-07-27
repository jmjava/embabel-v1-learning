package com.embabel.learning.common.domain;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Structured support agent response — the {@code @AchievesGoal} return type.
 */
public record SupportOutput(
        @JsonPropertyDescription("Advice returned to the customer")
        String advice,
        @JsonPropertyDescription("Whether to block their card or not")
        boolean blockCard,
        @JsonPropertyDescription("Risk level of query from 0 to 10")
        int risk
) {
}
