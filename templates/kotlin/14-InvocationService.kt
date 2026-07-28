package com.example.embabel.starter

import com.embabel.agent.api.invocation.AgentInvocation
import com.embabel.agent.core.AgentPlatform
import com.embabel.agent.core.ProcessOptions
import com.embabel.agent.core.Verbosity
import com.embabel.agent.domain.io.UserInput
import org.springframework.stereotype.Service

/**
 * Starter: call agents from application code.
 * TODO: change Result type to your @AchievesGoal return type.
 */
@Service
class InvocationService(
    private val agentPlatform: AgentPlatform,
) {

    fun run(prompt: String): GoapWriteReviewAgent.Reviewed {
        val options = ProcessOptions.DEFAULT.withVerbosity(
            Verbosity.DEFAULT.withShowPrompts(true),
        )
        val invocation: AgentInvocation<GoapWriteReviewAgent.Reviewed> = AgentInvocation
            .builder(agentPlatform)
            .options(options)
            .build(GoapWriteReviewAgent.Reviewed::class.java)
        return invocation.invoke(UserInput(prompt))
    }
}
