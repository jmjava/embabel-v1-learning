package com.embabel.learning.kotlin.lesson06

import com.embabel.learning.common.curriculum.DebugGuide
import com.embabel.learning.common.domain.Critique
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConditionalResearchAgentGuidedTest {

    @Test
    @DebugGuide(
        breakpoints = ["ConditionalResearchAgent.draftOk", "ConditionalResearchAgent.draftBad"],
        watch = "accepted flag flips condition names used in pre/post",
    )
    fun conditionsMirrorJavaSemantics() {
        val agent = ConditionalResearchAgent()
        assertTrue(agent.draftOk(Critique(true, "good")))
        assertFalse(agent.draftBad(Critique(true, "good")))
        assertTrue(agent.draftBad(Critique(false, "vague")))
    }
}
