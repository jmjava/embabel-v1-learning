package com.example.embabel.starter;

import com.embabel.agent.api.annotation.*;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;

/**
 * Starter: conditions + named bindings + rerun rewrite.
 */
@Agent(description = "TODO: research with critique gate")
public class ConditionalResearchAgent {

    public static final String DRAFT_OK = "draftOk";
    public static final String DRAFT_BAD = "draftBad";

    public record Draft(String content) {
    }

    public record Critique(boolean accepted, String feedback) {
    }

    public record Report(String body) {
    }

    @Action(outputBinding = "fastDraft")
    public Draft draft(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm().createObject(
                "Brief draft about: " + userInput.getContent(),
                Draft.class
        );
    }

    @Action
    public Critique critique(@RequireNameMatch Draft fastDraft, Ai ai) {
        return ai.withDefaultLlm().createObject(
                "Critique; accepted=true only if specific. Draft: " + fastDraft.content(),
                Critique.class
        );
    }

    @Condition(name = DRAFT_OK)
    public boolean ok(Critique critique) {
        return critique.accepted();
    }

    @Condition(name = DRAFT_BAD)
    public boolean bad(Critique critique) {
        return !critique.accepted();
    }

    @Action(pre = {DRAFT_BAD}, post = {DRAFT_OK}, canRerun = true, outputBinding = "carefulDraft")
    public Draft rewrite(
            UserInput userInput,
            @RequireNameMatch Draft fastDraft,
            Critique critique,
            Ai ai) {
        return ai.withDefaultLlm().createObject(
                """
                        Rewrite carefully.
                        Topic: %s
                        Previous: %s
                        Feedback: %s
                        """.formatted(userInput.getContent(), fastDraft.content(), critique.feedback()),
                Draft.class
        );
    }

    @AchievesGoal(description = "Report published")
    @Action(pre = {DRAFT_OK})
    public Report publish(Critique critique, UserInput userInput, Ai ai) {
        return ai.withDefaultLlm().createObject(
                "Final report for '%s' using feedback: %s"
                        .formatted(userInput.getContent(), critique.feedback()),
                Report.class
        );
    }
}
