package com.embabel.learning.java.lesson14;

import com.embabel.agent.api.invocation.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.ProcessOptions;
import com.embabel.agent.core.Verbosity;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.ReviewedStory;
import org.springframework.stereotype.Service;

/**
 * Lesson 14 (Java): call agents from application code (controllers, shell, jobs).
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>{@link AgentInvocation} selects agents by goal return type</li>
 *   <li>{@link ProcessOptions} verbosity is inherited by subagents</li>
 *   <li>Use {@code withToolCallContext} for auth/tenant metadata visible to tools</li>
 * </ul>
 */
@Lesson(
        value = LessonOrder.INVOCATION,
        title = "AgentInvocation and ProcessOptions",
        guideRefs = {"4.18 Invoking Embabel Agents", "4.16 ProcessOptions"},
        counterpart = "com.embabel.learning.kotlin.lesson14.InvocationDemoService"
)
@Service
public class InvocationDemoService {

    private final AgentPlatform agentPlatform;

    public InvocationDemoService(AgentPlatform agentPlatform) {
        this.agentPlatform = agentPlatform;
    }

    public ReviewedStory writeAndReview(String prompt) {
        var options = ProcessOptions.DEFAULT.withVerbosity(
                Verbosity.DEFAULT.withShowPrompts(true)
        );
        AgentInvocation<ReviewedStory> invocation = AgentInvocation
                .builder(agentPlatform)
                .options(options)
                .build(ReviewedStory.class);
        return invocation.invoke(new UserInput(prompt));
    }
}
