package com.embabel.learning.java.lesson05;

import com.embabel.agent.api.annotation.Action;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HitlProfileAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "HitlProfileAgent#extractProfile — cheap path",
                    "HitlProfileAgent#askUserForProfile — WaitFor.formSubmission",
                    "process WAITING status after HITL action"
            },
            watch = "Action.cost() == 100.0 on HITL fallback"
    )
    void hitlFallbackIsMarkedExpensive() throws Exception {
        Action action = HitlProfileAgent.class
                .getDeclaredMethod("askUserForProfile", com.embabel.agent.domain.io.UserInput.class)
                .getAnnotation(Action.class);
        assertEquals(100.0, action.cost(), 0.0);
    }
}
