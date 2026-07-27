package com.embabel.learning.kotlin.lesson01

import com.embabel.agent.test.unit.FakeOperationContext
import com.embabel.learning.common.curriculum.DebugGuide
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class InjectedAiDemoGuidedTest {

    @Test
    @DebugGuide(
        breakpoints = [
            "InjectedAiDemo.inventAnimal",
            "FakePromptRunner — inspect examples",
        ],
        watch = "prompt contains forest; MagicalAnimal response",
    )
    fun inventAnimal_promptContainsForestCue() {
        val context = FakeOperationContext.create()
        context.expectResponse(InjectedAiDemo.MagicalAnimal("Fluffox", "Magicox"))
        val demo = InjectedAiDemo(context.ai())
        demo.inventAnimal()
        val prompt = context.promptRunner.llmInvocations.first().messages.first().content
        assertTrue(prompt.lowercase().contains("forest"), prompt)
    }
}
