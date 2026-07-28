package com.example.embabel.starter;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.LlmTool;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.tool.Tool;
import com.embabel.agent.api.tool.ToolCallContext;
import com.embabel.agent.core.CoreToolGroups;
import com.embabel.agent.domain.io.UserInput;

import java.time.Instant;

/**
 * Starter: tool group + custom @LlmTool.
 */
@Agent(description = "TODO: answer questions using tools")
public class ToolingAgent {

    public record Answer(String text, String diagnostics) {
    }

    public static class ClockTools {
        @LlmTool(name = "server_time", description = "Current server time ISO-8601", returnDirect = false)
        public String serverTime(
                @LlmTool.Param(description = "Reason", required = false) String reason,
                ToolCallContext context) {
            return Instant.now() + " reason=" + reason;
        }
    }

    @AchievesGoal(description = "Answered with tools")
    @Action
    public Answer answer(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm()
                .withId("tooling-answer")
                .withToolGroup(CoreToolGroups.WEB)
                .withTool(Tool.fromInstance(new ClockTools()).getFirst())
                .createObject(
                        """
                                First call server_time. Then answer using web tools if needed.
                                Question: %s
                                """.formatted(userInput.getContent()),
                        Answer.class
                );
    }
}
