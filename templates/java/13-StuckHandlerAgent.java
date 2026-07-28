package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.StuckHandler;
import com.embabel.agent.api.common.StuckHandlerResult;
import com.embabel.agent.api.common.StuckHandlingResultCode;
import com.embabel.agent.core.AgentProcess;

/**
 * Starter: recover from STUCK by seeding blackboard + REPLAN.
 */
@Agent(description = "TODO: demo stuck recovery")
public class StuckHandlerAgent implements StuckHandler {

    public record Needed(String value) {
    }

    public record Out(String value) {
    }

    @AchievesGoal(description = "Transformed Needed → Out")
    @Action
    public Out finish(Needed needed) {
        return new Out(needed.value());
    }

    @Override
    public StuckHandlerResult handleStuck(AgentProcess agentProcess) {
        agentProcess.addObject(new Needed("seed"));
        return new StuckHandlerResult(
                "Seeded Needed and requesting replan",
                this,
                StuckHandlingResultCode.REPLAN,
                agentProcess
        );
    }
}
