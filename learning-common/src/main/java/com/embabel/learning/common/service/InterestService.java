package com.embabel.learning.common.service;

import org.springframework.stereotype.Service;

/**
 * Ordinary Spring service used inside GOAP actions without any LLM call.
 * <p>
 * <b>Nuance:</b> Not every {@code @Action} needs an LLM. Mixing deterministic code
 * with prompted steps is a core Embabel strength.
 */
@Service
public class InterestService {

    public String summarizeInterest(String interest) {
        if (interest == null || interest.isBlank()) {
            return "general curiosity";
        }
        return "deep interest in " + interest.trim().toLowerCase();
    }
}
