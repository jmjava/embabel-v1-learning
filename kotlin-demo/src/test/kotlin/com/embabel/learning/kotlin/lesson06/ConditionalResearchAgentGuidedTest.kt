package com.embabel.learning.kotlin.lesson06

import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.RequireNameMatch
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.DebugGuide
import com.embabel.learning.common.domain.Critique
import com.embabel.learning.common.domain.ResearchDraft
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
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

    @Test
    @DebugGuide(
        breakpoints = [
            "ConditionalResearchAgent.draftFast — outputBinding fastDraft",
            "ConditionalResearchAgent.critique — @RequireNameMatch",
            "ConditionalResearchAgent.rewriteCarefully — carefulDraft + canRerun",
        ],
        watch = "two ResearchDraft values: outputBinding + @RequireNameMatch, else latest wins",
    )
    fun namedBindingsSelectAmongSameTypeDrafts() {
        val draftFast = ConditionalResearchAgent::class.java
            .getDeclaredMethod("draftFast", UserInput::class.java, OperationContext::class.java)
            .getAnnotation(Action::class.java)
        assertEquals("fastDraft", draftFast.outputBinding)

        val critiqueDraft = ConditionalResearchAgent::class.java
            .getDeclaredMethod("critique", ResearchDraft::class.java, OperationContext::class.java)
            .parameters[0]
        assertNotNull(critiqueDraft.getAnnotation(RequireNameMatch::class.java))

        val rewrite = ConditionalResearchAgent::class.java.getDeclaredMethod(
            "rewriteCarefully",
            UserInput::class.java,
            ResearchDraft::class.java,
            Critique::class.java,
            OperationContext::class.java,
        )
        val rewriteAction = rewrite.getAnnotation(Action::class.java)
        assertEquals("carefulDraft", rewriteAction.outputBinding)
        assertTrue(rewriteAction.canRerun)
        assertNotNull(rewrite.parameters[1].getAnnotation(RequireNameMatch::class.java))
    }
}
