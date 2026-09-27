package com.embabel.learning.java.lesson06;

import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.RequireNameMatch;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.Critique;
import com.embabel.learning.common.domain.ResearchDraft;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalResearchAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "ConditionalResearchAgent#draftOk",
                    "ConditionalResearchAgent#draftBad",
                    "ConditionalResearchAgent#rewriteCarefully — only when DRAFT_BAD"
            },
            watch = "critique.accepted(); condition names DRAFT_OK / DRAFT_BAD"
    )
    void conditionsAreSideEffectFreePredicates() {
        var agent = new ConditionalResearchAgent();
        assertTrue(agent.draftOk(new Critique(true, "good")));
        assertFalse(agent.draftBad(new Critique(true, "good")));
        assertTrue(agent.draftBad(new Critique(false, "vague")));
    }

    @Test
    @DebugGuide(
            breakpoints = {
                    "ConditionalResearchAgent#draftFast — outputBinding fastDraft",
                    "ConditionalResearchAgent#critique — @RequireNameMatch",
                    "ConditionalResearchAgent#rewriteCarefully — carefulDraft + canRerun"
            },
            watch = "two ResearchDraft values: outputBinding + @RequireNameMatch, else latest wins"
    )
    void namedBindingsSelectAmongSameTypeDrafts() throws Exception {
        Action draftFast = ConditionalResearchAgent.class
                .getDeclaredMethod("draftFast", UserInput.class, Ai.class)
                .getAnnotation(Action.class);
        assertEquals("fastDraft", draftFast.outputBinding());

        Parameter critiqueDraft = ConditionalResearchAgent.class
                .getDeclaredMethod("critique", ResearchDraft.class, Ai.class)
                .getParameters()[0];
        assertNotNull(critiqueDraft.getAnnotation(RequireNameMatch.class));

        var rewrite = ConditionalResearchAgent.class.getDeclaredMethod(
                "rewriteCarefully", UserInput.class, ResearchDraft.class, Critique.class, Ai.class);
        Action rewriteAction = rewrite.getAnnotation(Action.class);
        assertEquals("carefulDraft", rewriteAction.outputBinding());
        assertTrue(rewriteAction.canRerun());
        assertNotNull(rewrite.getParameters()[1].getAnnotation(RequireNameMatch.class));
    }

    @Test
    @DebugGuide(
            breakpoints = {"ConditionalResearchAgent#rewriteCarefully"},
            watch = "topic, previous draft, and critique feedback stay in the rewrite prompt"
    )
    void rewriteCarefullyKeepsTopicDraftAndFeedback() {
        var ctx = FakeOperationContext.create();
        ctx.expectResponse(new ResearchDraft("tides", "The moon raises the tides.", "careful"));
        var topic = "Why do tides follow the moon?";
        var previous = "Tides are caused by wind.";
        var feedback = "Name the moon, not the wind.";

        var rewritten = new ConditionalResearchAgent().rewriteCarefully(
                new UserInput(topic),
                new ResearchDraft(topic, previous, "fast"),
                new Critique(false, feedback),
                ctx.ai());

        assertEquals("The moon raises the tides.", rewritten.content());
        var prompt = ctx.getLlmInvocations().getFirst().getPrompt();
        assertTrue(prompt.contains(topic), prompt);
        assertTrue(prompt.contains(previous), prompt);
        assertTrue(prompt.contains(feedback), prompt);
    }
}
