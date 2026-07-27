package com.embabel.learning.java.lesson03;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.PersonProfile;
import com.embabel.learning.common.service.InterestService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalizedBriefAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "PersonalizedBriefAgent#extractPerson",
                    "PersonalizedBriefAgent#summarizeInterest — no LLM here",
                    "PersonalizedBriefAgent#writeBrief"
            },
            watch = "nullability of createObjectIfPossible; InterestSummary from Spring service"
    )
    void mixedCodeAndLlmActions() {
        var agent = new PersonalizedBriefAgent(new InterestService());
        var ctx = FakeOperationContext.create();
        ctx.expectResponse(new PersonProfile("Ada", "computing"));

        var person = agent.extractPerson(new UserInput("Ada loves computing"), ctx.ai());
        assertEquals("Ada", person.name());

        var summary = agent.summarizeInterest(person);
        assertTrue(summary.summary().contains("computing"));

        var briefCtx = FakeOperationContext.create();
        briefCtx.expectResponse(new PersonalizedBriefAgent.PersonalizedBrief("Ada", "Ada pioneers computing."));
        var brief = agent.writeBrief(person, summary, briefCtx.ai());
        assertEquals("Ada", brief.name());
    }
}
