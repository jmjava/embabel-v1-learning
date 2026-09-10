package com.embabel.learning.java.lesson10;

import com.embabel.agent.api.common.PlannerType;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlannerTypeLessonTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "SupervisorKitchenAgent class annotation planner=SUPERVISOR",
                    "UtilityTriageComponent class annotation planner=UTILITY"
            },
            watch = "PlannerType enum: GOAP, UTILITY, HYBRID, SUPERVISOR"
    )
    void agentsDeclareNonDefaultPlanners() throws Exception {
        assertEquals(PlannerType.SUPERVISOR,
                SupervisorKitchenAgent.class.getAnnotation(Agent.class).planner());
        assertEquals(PlannerType.UTILITY,
                UtilityTriageComponent.class.getAnnotation(Agent.class).planner());

        assertArrayEquals(
                new PlannerType[]{
                        PlannerType.GOAP,
                        PlannerType.UTILITY,
                        PlannerType.HYBRID,
                        PlannerType.SUPERVISOR
                },
                PlannerType.values());
        assertTrue(PlannerType.GOAP.getNeedsGoals());
        assertFalse(PlannerType.UTILITY.getNeedsGoals(), "Utility may wander without a goal");
        assertTrue(PlannerType.HYBRID.getNeedsGoals(), "Hybrid still needs a real goal");
        assertTrue(PlannerType.SUPERVISOR.getNeedsGoals());
        assertEquals(PlannerType.GOAP, Agent.class.getMethod("planner").getDefaultValue());
    }
}
