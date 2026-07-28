package com.example.embabel.starter;

import com.embabel.agent.api.invocation.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.ProcessOptions;
import com.embabel.agent.core.Verbosity;
import com.embabel.agent.domain.io.UserInput;
import org.springframework.stereotype.Service;

/**
 * Starter: call agents from application code.
 * TODO: change Result type to your @AchievesGoal return type.
 */
@Service
public class InvocationService {

    private final AgentPlatform agentPlatform;

    public InvocationService(AgentPlatform agentPlatform) {
        this.agentPlatform = agentPlatform;
    }

    public GoapWriteReviewAgent.Reviewed run(String prompt) {
        var options = ProcessOptions.DEFAULT.withVerbosity(
                Verbosity.DEFAULT.withShowPrompts(true)
        );
        AgentInvocation<GoapWriteReviewAgent.Reviewed> invocation = AgentInvocation
                .builder(agentPlatform)
                .options(options)
                .build(GoapWriteReviewAgent.Reviewed.class);
        return invocation.invoke(new UserInput(prompt));
    }
}
