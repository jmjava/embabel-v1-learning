package com.embabel.learning.common.domain;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Terminal goal type for the Write-and-Review GOAP agent.
 *
 * @param story  original story
 * @param review reviewer critique
 * @param score  optional quality score 0.0–1.0
 */
public record ReviewedStory(
        Story story,
        @JsonPropertyDescription("Critical review of the story")
        String review,
        @JsonPropertyDescription("Quality score from 0.0 to 1.0")
        double score
) {
}
