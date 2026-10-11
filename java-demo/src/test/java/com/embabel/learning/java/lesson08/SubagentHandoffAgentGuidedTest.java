package com.embabel.learning.java.lesson08;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.ReviewedStory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubagentHandoffAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "SubagentHandoffAgent#compose",
                    "Subagent.ofClass(WriteAndReviewAgent.class).consuming(UserInput.class)",
                    "WriteAndReviewAgent#craftStory in child process"
            },
            watch = "parent PromptRunner tools list includes subagent tool"
    )
    void documentsSubagentGoalType() {
        assertEquals(ReviewedStory.class, new SubagentHandoffAgent().subagentGoalType());
    }

    @Test
    @DebugGuide(
            breakpoints = {
                    "SubagentHandoffAgent#compose",
                    "withTool(Subagent.ofClass(WriteAndReviewAgent.class))"
            },
            watch = "user request stays in the prompt; WriteAndReviewAgent tool is attached"
    )
    void composeKeepsUserRequestAndAttachesWriteAndReview() {
        var ctx = FakeOperationContext.create();
        ctx.expectResponse(new SubagentHandoffAgent.AnthologyBlurb(
                "A keeper bottles storms. Readers leave wanting the next tide."
        ));
        var request = "Write a blurb about a lighthouse keeper who collects storms.";

        var blurb = new SubagentHandoffAgent().compose(new UserInput(request), ctx);

        assertTrue(blurb.blurb().contains("storms"), blurb.blurb());
        var invocation = ctx.getLlmInvocations().getFirst();
        var prompt = invocation.getPrompt();
        assertTrue(prompt.contains(request), prompt);
        assertTrue(prompt.toLowerCase().contains("blurb"), prompt);

        var names = invocation.getInteraction().getTools().stream()
                .map(tool -> tool.getDefinition().getName())
                .toList();
        assertTrue(names.stream().anyMatch(name -> name.contains("WriteAndReview")), names.toString());
    }
}
