package com.embabel.learning.java.lesson06;

import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.Critique;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalResearchAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "ConditionalResearchAgent#draftOk",
                    "ConditionalResearchAgent#draftBad",
                    "ConditionalResearchAgent#rewriteCarefully — only when DRAFT_BAD"
            },
            watch = "critique.accepted(); condition names DRAFT_OK / DRAFT_BAD"
    )
    void conditionsAreSideEffectFreePredicates() {
        var agent = new ConditionalResearchAgent();
        assertTrue(agent.draftOk(new Critique(true, "good")));
        assertFalse(agent.draftBad(new Critique(true, "good")));
        assertTrue(agent.draftBad(new Critique(false, "vague")));
    }
}
