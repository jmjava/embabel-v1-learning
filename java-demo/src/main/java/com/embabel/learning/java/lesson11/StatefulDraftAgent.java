package com.embabel.learning.java.lesson11;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.State;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;

/**
 * Lesson 11 (Java): {@link State}-scoped looping workflow.
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>Java nested records are implicitly static — good state carriers</li>
 *   <li>Returning a new state type transitions; previous state objects are hidden</li>
 *   <li>Use {@code clearBlackboard = true} on loop actions, not on the goal action</li>
 *   <li>Non-state blackboard objects can still pass through transitions</li>
 * </ul>
 */
@Lesson(
        value = LessonOrder.STATES,
        title = "@State machines and loops",
        guideRefs = {"4.19 Using States"},
        counterpart = "com.embabel.learning.kotlin.lesson11.StatefulDraftAgent"
)
@Agent(description = "Iteratively refine a draft using explicit states")
public class StatefulDraftAgent {

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
                    """
                            Improve this draft (iteration %d) about %s:
                            %s
                            """.formatted(iteration + 1, topic, draft)
            );
            return new Drafting(topic, improved, iteration + 1);
        }
    }

    @State
    public record Done(String topic, String draft) implements LoopOutcome {
        @AchievesGoal(description = "Draft refinement complete")
        @Action
        public FinishedReport finish() {
            return new FinishedReport(topic, draft);
        }
    }

    public record FinishedReport(String topic, String finalDraft) {
    }

    @Action
    public Drafting start(UserInput userInput, Ai ai) {
        var draft = ai.withDefaultLlm().generateText(
                "Write a rough 2-sentence draft about: " + userInput.getContent()
        );
        return new Drafting(userInput.getContent(), draft, 0);
    }
}
