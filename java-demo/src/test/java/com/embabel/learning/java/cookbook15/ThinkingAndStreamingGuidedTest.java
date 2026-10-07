package com.embabel.learning.java.cookbook15;

import com.embabel.agent.api.common.PromptRunner;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.agent.test.unit.FakePromptRunner;
import com.embabel.common.ai.prompt.PromptContributor;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        var context = FakeOperationContext.create();
        var installed = new AtomicReference<FakePromptRunner>();
        // FakeOperationContext runners report supportsThinking() == false, so the
        // production guard returns before withSystemPrompt. This fixture still
        // stores the prompt on that fake runner, then completes thinking offline.
        var response = new ThinkingTripPlanner(ThinkingStreamingFixtures.thinkingCapableAi(context, installed))
                .planWithThinking("three days London to Paris");

        assertNotNull(response);
        assertNotNull(response.getResult());
        FakePromptRunner prompted = installed.get();
        assertNotNull(prompted, "planWithThinking must install the reasoning prompt on the FakeOperationContext runner");
        String installedPrompt = prompted.getPromptContributors().stream()
                .map(PromptContributor::contribution)
                .reduce("", String::concat);
        assertTrue(installedPrompt.contains(ThinkingTripPlanner.REASONING_SYSTEM_PROMPT.strip()),
                "planWithThinking must install REASONING_SYSTEM_PROMPT");
        assertTrue(installedPrompt.contains("<decision_reasoning>"),
                "renaming or removing <decision_reasoning> in the prompt the planner installs must fail");
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
        var denied = assertThrows(IllegalStateException.class, () ->
                new StreamingTripPlanner(FakeOperationContext.create().ai())
                        .streamOptions("three days London to Paris"));
        assertTrue(denied.getMessage().contains("streaming"));

        PromptRunner claiming = ThinkingStreamingFixtures.claimingRunnerThatDoesNotStream();
        assertTrue(claiming.supportsStreaming(),
                "fixture must claim streaming so a swallowed boolean guard cannot pass");
        var swallowed = assertThrows(UnsupportedOperationException.class, () ->
                new StreamingTripPlanner(ThinkingStreamingFixtures.aiReturning(claiming))
                        .streamOptions("three days London to Paris"));
        assertTrue(swallowed.getMessage().toLowerCase(Locale.ROOT).contains("streaming"),
                "a runner that claims supportsStreaming() but does not stream must fail the guard");
    }
}
