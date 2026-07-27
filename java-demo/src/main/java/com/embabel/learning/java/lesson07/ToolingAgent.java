package com.embabel.learning.java.lesson07;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.LlmTool;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.tool.Tool;
import com.embabel.agent.api.tool.ToolCallContext;
import com.embabel.agent.core.CoreToolGroups;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;

import java.time.Instant;
import java.util.Map;

/**
 * Lesson 07 (Java): mix framework tool groups with custom {@link LlmTool}s.
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>Tool groups like {@link CoreToolGroups#WEB} are capability bundles</li>
 *   <li>{@link ToolCallContext} parameters are invisible to the LLM schema</li>
 *   <li>Prompt text should explicitly tell the model to call diagnostic tools first</li>
 *   <li>Tools are attached per {@code PromptRunner}, not globally to the agent</li>
 * </ul>
 */
@Lesson(
        value = LessonOrder.TOOLS,
        title = "Tool groups and custom tools",
        guideRefs = {"4.9 Tools", "4.9.1 In Process Tools", "4.9.3 Tool Groups"},
        counterpart = "com.embabel.learning.kotlin.lesson07.ToolingAgent"
)
@Agent(description = "Answer a research question using tools and a diagnostic custom tool")
public class ToolingAgent {

    public record ToolingAnswer(String answer, String diagnostics) {
    }

    /**
     * Custom tool instance methods. Prefer focused, safe operations.
     */
    public static class ClockTools {

        @LlmTool(
                name = "server_time",
                description = "Returns the current server time in ISO-8601 format",
                returnDirect = false
        )
        public String serverTime(
                @LlmTool.Param(description = "Optional reason for the call", required = false)
                String reason,
                ToolCallContext context
        ) {
            // context is out-of-band metadata (auth/tenant/etc.), not model-visible
            Map<String, ?> meta = context.toMap();
            return Instant.now() + " reason=" + reason + " metaKeys=" + meta.keySet();
        }
    }

    @AchievesGoal(description = "Produce an answer that used tools")
    @Action
    public ToolingAnswer answer(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm()
                .withId("lesson07-tooling")
                .withToolGroup(CoreToolGroups.WEB)
                .withTool(Tool.fromInstance(new ClockTools()).getFirst())
                .createObject(
                        """
                                IMPORTANT: First call server_time to record when you started.
                                Then answer the user question, using web tools if needed.
                                
                                Question: %s
                                
                                Return diagnostics summarizing which tools you used.
                                """.formatted(userInput.getContent()),
                        ToolingAnswer.class
                );
    }
}
