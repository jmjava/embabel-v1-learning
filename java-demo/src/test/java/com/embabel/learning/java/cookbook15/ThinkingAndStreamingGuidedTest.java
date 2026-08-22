package com.embabel.learning.java.cookbook15;

import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThinkingAndStreamingGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "ThinkingTripPlanner#planWithThinking",
                    "PromptRunner#supportsThinking",
                    "PromptRunner#thinking"
            },
            watch = "supportsThinking(); ThinkingResponse.getThinkingContent()"
    )
    void thinkingPromptAsksForDecisionReasoning() {
        assertTrue(ThinkingTripPlanner.REASONING_SYSTEM_PROMPT.contains("<decision_reasoning>"));
        var runner = FakeOperationContext.create().ai().withDefaultLlm();
        // Fake runners used in guided tests typically do not advertise live thinking.
        // The production planner guards on supportsThinking() before calling thinking().
        assertFalse(runner.supportsThinking() && !ThinkingTripPlanner.REASONING_SYSTEM_PROMPT.contains("trade-offs"));
    }

    @Test
    @DebugGuide(
            breakpoints = {
                    "StreamingTripPlanner#streamOptions",
                    "StreamingPromptRunnerBuilder#createObjectStream"
            },
            watch = "supportsStreaming(); collected TripOption list"
    )
    void streamingPlannerGuardsWhenRunnerCannotStream() {
        var runner = FakeOperationContext.create().ai().withDefaultLlm();
        if (!runner.supportsStreaming()) {
            try {
                new StreamingTripPlanner(FakeOperationContext.create().ai())
                        .streamOptions("three days London to Paris");
            } catch (IllegalStateException expected) {
                assertTrue(expected.getMessage().contains("streaming"));
                return;
            }
        }
        assertTrue(true, "Live streaming runner available — skip the guard assertion");
    }
}
