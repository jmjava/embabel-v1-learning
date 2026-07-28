package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.State;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;

/**
 * Starter: @State machine loop with clearBlackboard on refine.
 */
@Agent(description = "TODO: iterative draft refinement via states")
public class StatefulLoopAgent {

    public sealed interface LoopOutcome {
    }

    @State
    public record Drafting(String topic, String draft, int iteration) implements LoopOutcome {
        @Action(clearBlackboard = true)
        public LoopOutcome refine(Ai ai) {
            if (iteration >= 2) {
                return new Done(topic, draft);
            }
            var improved = ai.withDefaultLlm().generateText(
                    "Improve draft (iteration %d) about %s:%n%s".formatted(iteration + 1, topic, draft)
            );
            return new Drafting(topic, improved, iteration + 1);
        }
    }

    @State
    public record Done(String topic, String draft) implements LoopOutcome {
        @AchievesGoal(description = "Draft finished")
        @Action
        public Report finish() {
            return new Report(topic, draft);
        }
    }

    public record Report(String topic, String finalDraft) {
    }

    @Action
    public Drafting start(UserInput userInput, Ai ai) {
        var draft = ai.withDefaultLlm().generateText(
                "Rough 2-sentence draft about: " + userInput.getContent()
        );
        return new Drafting(userInput.getContent(), draft, 0);
    }
}
