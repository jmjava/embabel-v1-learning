package com.embabel.learning.common.domain;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Simple typed person used by GOAP and HITL lessons.
 *
 * @param name display name
 * @param interest topic of interest used to personalize later actions
 */
public record PersonProfile(
        @JsonPropertyDescription("Person's name")
        String name,
        @JsonPropertyDescription("Primary interest or hobby")
        String interest
) {
}
