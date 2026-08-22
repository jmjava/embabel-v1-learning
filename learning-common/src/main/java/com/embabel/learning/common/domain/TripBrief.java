package com.embabel.learning.common.domain;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Compact structured trip used by Embabel 1.5 cookbook lessons
 * ({@code creating}, {@code createObjectIfPossible}, thinking, streaming).
 */
public record TripBrief(
        @JsonPropertyDescription("City or region the traveler is going to")
        String destination,
        @JsonPropertyDescription("How the traveler gets there")
        String transport,
        @JsonPropertyDescription("One-paragraph itinerary")
        String itineraryDescription,
        @JsonPropertyDescription("Single highlight the traveler should not miss")
        String highlight
) {
}
