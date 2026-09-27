package com.embabel.learning.java.lesson07;

import com.embabel.agent.api.annotation.LlmTool;
import com.embabel.agent.api.tool.Tool;
import com.embabel.agent.core.CoreToolGroups;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToolingAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "ToolingAgent.ClockTools#serverTime",
                    "Tool.fromInstance(...)",
                    "ToolingAgent#answer — withToolGroup(WEB) + withTool(...)"
            },
            watch = "tool name server_time; ToolCallContext invisible to model schema"
    )
    void customToolIsDiscoverableFromInstance() {
        var tools = Tool.fromInstance(new ToolingAgent.ClockTools());
        assertFalse(tools.isEmpty());
        assertEquals("server_time", tools.getFirst().getDefinition().getName());

        LlmTool ann = ToolingAgent.ClockTools.class
                .getDeclaredMethods()[0]
                .getAnnotation(LlmTool.class);
        // method order not guaranteed — scan
        boolean found = false;
        for (var m : ToolingAgent.ClockTools.class.getDeclaredMethods()) {
            if (m.getAnnotation(LlmTool.class) != null) {
                assertEquals("server_time", m.getAnnotation(LlmTool.class).name());
                found = true;
            }
        }
        assertTrue(found || ann != null);
    }

    @Test
    @DebugGuide(
            breakpoints = {"ToolingAgent#answer"},
            watch = "question and server_time in the prompt; web group plus server_time tool"
    )
    void answerPromptKeepsQuestionAndAttachesServerTime() {
        var ctx = FakeOperationContext.create();
        ctx.expectResponse(new ToolingAgent.ToolingAnswer("Tides follow the moon.", "server_time"));
        var question = "Why do tides follow the moon?";

        var answer = new ToolingAgent().answer(new UserInput(question), ctx.ai());

        assertEquals("Tides follow the moon.", answer.answer());
        var invocation = ctx.getLlmInvocations().getFirst();
        var prompt = invocation.getPrompt();
        assertTrue(prompt.contains(question), prompt);
        assertTrue(prompt.contains("server_time"), prompt);

        var groups = invocation.getInteraction().getToolGroups();
        assertTrue(
                groups.stream().anyMatch(group -> CoreToolGroups.WEB.equals(group.getRole())),
                groups.toString()
        );
        var names = invocation.getInteraction().getTools().stream()
                .map(tool -> tool.getDefinition().getName())
                .toList();
        assertTrue(names.contains("server_time"), names.toString());
    }
}
