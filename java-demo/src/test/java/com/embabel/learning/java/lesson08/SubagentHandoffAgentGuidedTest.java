package com.embabel.learning.java.lesson08;

import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.ReviewedStory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
