package com.embabel.learning.java.lesson11;

import com.embabel.agent.api.annotation.State;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatefulDraftAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "StatefulDraftAgent#start",
                    "Drafting#refine — clearBlackboard=true",
                    "Done#finish — goal"
            },
            watch = "iteration counter; transition Drafting -> Done"
    )
    void stateTypesAreAnnotated() {
        assertNotNull(StatefulDraftAgent.Drafting.class.getAnnotation(State.class));
        assertNotNull(StatefulDraftAgent.Done.class.getAnnotation(State.class));
        assertTrue(StatefulDraftAgent.LoopOutcome.class.isSealed());
    }
}
