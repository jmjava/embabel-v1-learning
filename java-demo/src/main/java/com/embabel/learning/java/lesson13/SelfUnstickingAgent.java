package com.embabel.learning.java.lesson13;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.StuckHandler;
import com.embabel.agent.api.common.StuckHandlerResult;
import com.embabel.agent.api.common.StuckHandlingResultCode;
import com.embabel.agent.core.AgentProcess;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;

/**
 * Lesson 13 (Java): recover when the planner cannot find a path.
 *
 * <p>Goal action needs a {@link Dog}, but nothing produces one initially.
 * {@link #handleStuck} injects a Dog and asks for {@code REPLAN}.
 *
 * <h2>Nuance</h2>
 * Use stuck handlers for genuine recovery/seed data — not to paper over bad models.
 * Prefer fixing missing actions/types when the domain is incomplete.
 */
@Lesson(
        value = LessonOrder.STUCK,
        title = "Stuck recovery",
        guideRefs = {"4.6.12 Implementing the StuckHandler interface"},
        counterpart = "com.embabel.learning.kotlin.lesson13.SelfUnstickingAgent"
)
@Agent(description = "Demo agent that unsticks itself by adding a missing Dog")
public class SelfUnstickingAgent implements StuckHandler {

    public record Dog(String name) {
    }

    public record Frog(String name) {
    }

    @AchievesGoal(description = "Transform dog into frog")
    @Action
    public Frog toFrog(Dog dog) {
        return new Frog(dog.name());
    }

    @Override
    public StuckHandlerResult handleStuck(AgentProcess agentProcess) {
        agentProcess.addObject(new Dog("Duke"));
        return new StuckHandlerResult(
                "Seeded Dog('Duke') and requesting replan",
                this,
                StuckHandlingResultCode.REPLAN,
                agentProcess
        );
    }
}
