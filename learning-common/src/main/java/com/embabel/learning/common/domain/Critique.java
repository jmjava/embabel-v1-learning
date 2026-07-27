package com.embabel.learning.common.domain;

/**
 * Critique of a research draft; drives {@code @Condition} methods.
 *
 * @param accepted whether the draft meets quality bar
 * @param feedback reviewer notes
 */
public record Critique(boolean accepted, String feedback) {
}
