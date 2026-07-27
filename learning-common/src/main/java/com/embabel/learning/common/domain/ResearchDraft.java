package com.embabel.learning.common.domain;

/**
 * Intermediate research artifact used by conditions / binding lessons.
 *
 * @param topic   research topic
 * @param content draft text
 * @param model   which model/path produced it (for named binding demos)
 */
public record ResearchDraft(String topic, String content, String model) {
}
