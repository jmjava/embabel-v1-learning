package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.api.tool.Subagent;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.LlmOptions;

/**
 * Starter: call another agent as a tool.
 * TODO: replace GoapWriteReviewAgent with your specialist agent class.
 */
@Agent(description = "TODO: orchestrate specialists via subagents")
public class SubagentOrchestrator {

    public record Summary(String text) {
    }

    @AchievesGoal(description = "Summary via subagent")
    @Action
    public Summary compose(UserInput userInput, OperationContext context) {
        return context.promptRunner()
                .withLlm(LlmOptions.withAutoLlm())
                .withTool(Subagent.ofClass(GoapWriteReviewAgent.class).consuming(UserInput.class))
                .createObject(
                        """
                                Use the write/review subagent, then summarize in 2 sentences.
                                Request: %s
                                """.formatted(userInput.getContent()),
                        Summary.class
                );
    }
}
