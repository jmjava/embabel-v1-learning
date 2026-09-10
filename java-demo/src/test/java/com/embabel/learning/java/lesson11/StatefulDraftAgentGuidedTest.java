package com.embabel.learning.java.lesson11;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.State;
import com.embabel.agent.api.common.Ai;
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
}
