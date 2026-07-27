package com.embabel.learning.kotlin.lesson02

import com.embabel.agent.domain.io.UserInput
import com.embabel.agent.test.unit.FakeOperationContext
import com.embabel.agent.test.unit.FakePromptRunner
import com.embabel.learning.common.curriculum.DebugGuide
import com.embabel.learning.common.domain.Story
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Guided Kotlin debug for Write/Review.
 *
 * Compare with Java: this test passes [com.embabel.agent.api.common.OperationContext]
 * into actions, not [com.embabel.agent.api.common.Ai].
 */
class WriteAndReviewAgentGuidedTest {

    @Test
    @DebugGuide(
        breakpoints = [
            "WriteAndReviewAgent.craftStory",
            "temperature 0.7 line",
            "WriteAndReviewAgent.reviewStory",
        ],
        watch = "prompt content; temperature; ReviewedStory.score",
    )
    fun craftThenReview_promptsContainUserCue() {
        val agent = WriteAndReviewAgent(80, 60)
        val input = UserInput("Tell me about a brave knight")

        val craftCtx = FakeOperationContext.create()
        craftCtx.expectResponse(Story("Once upon a time Sir Galahad..."))
        agent.craftStory(input, craftCtx)

        val promptRunner = craftCtx.promptRunner as FakePromptRunner
        val craftPrompt = promptRunner.llmInvocations.first().messages.first().content
        assertTrue(craftPrompt.contains("knight"), craftPrompt)
        assertEquals(0.7, promptRunner.llmInvocations.first().interaction.llm.temperature!!, 0.01)

        val reviewCtx = FakeOperationContext.create()
        reviewCtx.expectResponse("A thrilling tale of bravery!")
        val reviewed = agent.reviewStory(input, Story("Once..."), reviewCtx)
        assertTrue(reviewed.review.contains("bravery"))
    }
}
