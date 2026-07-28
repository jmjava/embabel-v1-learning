package com.example.embabel.starter

import com.embabel.agent.domain.io.UserInput
import com.embabel.agent.test.unit.FakeOperationContext
import com.embabel.agent.test.unit.FakePromptRunner
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Starter: FakeOperationContext unit test (no live LLM).
 * Copy into src/test/kotlin and point at your agent.
 *
 * Kotlin nuance: pass OperationContext into actions (not Ai).
 */
class GuidedUnitTest {

    @Test
    fun write_promptContainsCue() {
        val context = FakeOperationContext.create()
        context.expectResponse(GoapWriteReviewAgent.Draft("Once upon a time..."))

        val agent = GoapWriteReviewAgent()
        agent.write(UserInput("Tell me about a knight"), context)

        val promptRunner = context.promptRunner as FakePromptRunner
        val prompt = promptRunner.llmInvocations.first().messages.first().content
        assertTrue(prompt.contains("knight"), prompt)
    }
}
