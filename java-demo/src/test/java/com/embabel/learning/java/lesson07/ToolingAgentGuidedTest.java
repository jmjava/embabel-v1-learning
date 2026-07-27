package com.embabel.learning.java.lesson07;

import com.embabel.agent.api.annotation.LlmTool;
import com.embabel.agent.api.tool.Tool;
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
}
