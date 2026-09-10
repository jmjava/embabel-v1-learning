package com.embabel.learning.java.lesson13;

import com.embabel.agent.api.common.StuckHandlingResultCode;
import com.embabel.agent.core.AgentProcess;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SelfUnstickingAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "SelfUnstickingAgent#toFrog — requires Dog on blackboard"
            },
            watch = "Frog.name equals Dog.name; no LLM"
    )
    void toFrogIsPureTransformation() {
        var agent = new SelfUnstickingAgent();
        var frog = agent.toFrog(new SelfUnstickingAgent.Dog("Duke"));
        assertEquals("Duke", frog.name());
    }

    @Test
    @DebugGuide(
            breakpoints = {
                    "SelfUnstickingAgent#handleStuck — seeds Dog and REPLAN"
            },
            watch = "StuckHandlingResultCode.REPLAN; agentProcess objects after seed"
    )
    void handleStuckSeedsDogAndReplans() {
        var agent = new SelfUnstickingAgent();
        var process = mock(AgentProcess.class);
        var result = agent.handleStuck(process);
        assertEquals(StuckHandlingResultCode.REPLAN, result.getCode());
        verify(process).addObject(new SelfUnstickingAgent.Dog("Duke"));
    }
}
