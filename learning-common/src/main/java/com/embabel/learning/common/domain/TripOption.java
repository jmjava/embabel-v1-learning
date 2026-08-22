package com.embabel.learning.common.domain;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * One streamed or heuristic travel option.
 */
public record TripOption(
        @JsonPropertyDescription("Short label such as cheapest or scenic")
        String label,
        @JsonPropertyDescription("One or two sentences describing the option")
        String text
) {
}
