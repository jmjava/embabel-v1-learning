package com.embabel.learning.java.lesson11;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.State;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DebugGuide(
            breakpoints = {
                    "Drafting#refine — clearBlackboard=true",
                    "Done#finish — goal, no clearBlackboard"
            },
            watch = "loop action clears blackboard; goal action does not"
    )
    void loopActionClearsBlackboardGoalDoesNot() throws Exception {
        Action refine = StatefulDraftAgent.Drafting.class
                .getDeclaredMethod("refine", Ai.class)
                .getAnnotation(Action.class);
        assertTrue(refine.clearBlackboard(), "looping @State actions clear the blackboard");

        var finish = StatefulDraftAgent.Done.class.getDeclaredMethod("finish");
        Action finishAction = finish.getAnnotation(Action.class);
        assertFalse(finishAction.clearBlackboard(), "goal action must not casually clear the blackboard");
        assertNotNull(finish.getAnnotation(AchievesGoal.class));
    }

    @Test
    @DebugGuide(
            breakpoints = {"StatefulDraftAgent#start"},
            watch = "user topic stays in the rough-draft prompt and on the Drafting state"
    )
    void startPromptKeepsTheTopic() {
        var ctx = FakeOperationContext.create();
        var topic = "Why do tides follow the moon?";
        ctx.expectResponse("The moon pulls the sea. Tides rise and fall.");

        var drafting = new StatefulDraftAgent().start(new UserInput(topic), ctx.ai());

        assertEquals(topic, drafting.topic());
        assertEquals("The moon pulls the sea. Tides rise and fall.", drafting.draft());
        assertEquals(0, drafting.iteration());
        var prompt = ctx.getLlmInvocations().getFirst().getPrompt();
        assertTrue(prompt.contains("Write a rough 2-sentence draft about:"), prompt);
        assertTrue(prompt.contains(topic), prompt);
    }
}
