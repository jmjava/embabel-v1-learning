package com.embabel.learning.java.lesson13;

import com.embabel.agent.api.common.StuckHandlingResultCode;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelfUnstickingAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "SelfUnstickingAgent#toFrog — requires Dog on blackboard",
                    "SelfUnstickingAgent#handleStuck — seeds Dog and REPLAN"
            },
            watch = "StuckHandlingResultCode.REPLAN; agentProcess objects after seed"
    )
    void toFrogIsPureTransformation() {
        var agent = new SelfUnstickingAgent();
        var frog = agent.toFrog(new SelfUnstickingAgent.Dog("Duke"));
        assertEquals("Duke", frog.name());
        assertEquals(StuckHandlingResultCode.REPLAN, StuckHandlingResultCode.REPLAN);
    }
}
