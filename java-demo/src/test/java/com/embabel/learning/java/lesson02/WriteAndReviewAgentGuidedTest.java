package com.embabel.learning.java.lesson02;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.agent.test.unit.FakePromptRunner;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.Story;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Guided unit-test debug for the Write/Review GOAP agent.
 *
 * <h2>Stepping plan</h2>
 * <ol>
 *   <li>Breakpoint on {@code craftStory} — observe UserInput on the way in</li>
 *   <li>Step into {@code ai.withLlm(...)} — temperature 0.7 for creativity</li>
 *   <li>After return, inspect FakePromptRunner invocations</li>
 *   <li>Repeat for {@code reviewStory}</li>
 * </ol>
 */
class WriteAndReviewAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "WriteAndReviewAgent#craftStory",
                    "LlmOptions.withAutoLlm().withTemperature(0.7)",
                    "FakePromptRunner invocation list after craftStory",
                    "WriteAndReviewAgent#reviewStory"
            },
            watch = "prompt text; temperature; story.text(); review string"
    )
    void craftThenReview_promptsContainUserCue() {
        var agent = new WriteAndReviewAgent(80, 60);
        var input = new UserInput("Tell me about a brave knight");

        var craftCtx = FakeOperationContext.create();
        craftCtx.expectResponse(new Story("Once upon a time Sir Galahad..."));
        var story = agent.craftStory(input, craftCtx.ai());
        assertTrue(story.text().contains("Galahad"));

        FakePromptRunner craftRunner = craftCtx.getPromptRunner();
        var craftPrompt = craftRunner.getLlmInvocations().getFirst().getMessages().getFirst().getContent();
        assertTrue(craftPrompt.contains("knight"), craftPrompt);
        assertEquals(0.7, craftRunner.getLlmInvocations().getFirst().getInteraction().getLlm().getTemperature(), 0.01);

        var reviewCtx = FakeOperationContext.create();
        reviewCtx.expectResponse("A thrilling tale of bravery!");
        var reviewed = agent.reviewStory(input, story, reviewCtx.ai());
        assertTrue(reviewed.review().contains("bravery"));
        assertEquals(0.85, reviewed.score(), 0.01);
        var reviewPrompt = reviewCtx.getLlmInvocations().getFirst().getMessages().getFirst().getContent();
        assertTrue(reviewPrompt.toLowerCase().contains("review"), reviewPrompt);
        assertTrue(reviewPrompt.contains("knight"), reviewPrompt);
    }
}
