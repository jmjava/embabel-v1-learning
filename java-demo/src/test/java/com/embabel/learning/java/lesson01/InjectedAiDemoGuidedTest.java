package com.embabel.learning.java.lesson01;

import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.agent.test.unit.FakePromptRunner;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Guided debug walkthrough for Lesson 01.
 *
 * <p><b>How to step-debug:</b> Right-click this test → Debug. Breakpoints listed on
 * {@link #inventAnimal_promptContainsForestCue()}.
 */
class InjectedAiDemoGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "InjectedAiDemo#inventAnimal — entry",
                    "FakePromptRunner#creating / fromPrompt — inspect examples + prompt",
                    "InjectedAiDemoGuidedTest — assert prompt content"
            },
            watch = "promptRunner.getLlmInvocations(); expected MagicalAnimal response",
            tip = "IntelliJ: place breakpoints then Debug 'InjectedAiDemoGuidedTest'"
    )
    void inventAnimal_promptContainsForestCue() {
        var context = FakeOperationContext.create();
        FakePromptRunner promptRunner = context.getPromptRunner();
        context.expectResponse(new InjectedAiDemo.MagicalAnimal("Fluffox", "Magicox"));

        // Inject Fake AI via OperationContext.ai()
        var demo = new InjectedAiDemo(context.ai());
        var animal = demo.inventAnimal();

        assertEquals("Fluffox", animal.name());
        var prompt = promptRunner.getLlmInvocations().getFirst().getMessages().getFirst().getContent();
        assertTrue(prompt.toLowerCase().contains("forest"), prompt);
    }
}
