package com.embabel.learning.java.lesson06;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Condition;
import com.embabel.agent.api.annotation.RequireNameMatch;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.Critique;
import com.embabel.learning.common.domain.FinalReport;
import com.embabel.learning.common.domain.ResearchDraft;

/**
 * Lesson 06 (Java): conditions + named bindings when multiple values share a type.
 *
 * <h2>Flow</h2>
 * <ol>
 *   <li>Draft with a "fast" model binding {@code fastDraft}</li>
 *   <li>Critique the draft</li>
 *   <li>If unsatisfactory, rewrite with {@code carefulDraft} (can rerun)</li>
 *   <li>Publish {@link FinalReport} when critique is accepted</li>
 * </ol>
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>{@link Condition} methods should be side-effect free (may be called often)</li>
 *   <li>Without {@link RequireNameMatch}, the blackboard's latest {@link ResearchDraft} wins</li>
 *   <li>{@code canRerun = true} allows the rewrite action to execute multiple times</li>
 * </ul>
 */
@Lesson(
        value = LessonOrder.CONDITIONS,
        title = "Conditions and named bindings",
        guideRefs = {"4.6.4 The @Condition annotation", "4.6.6 Binding by name"},
        counterpart = "com.embabel.learning.kotlin.lesson06.ConditionalResearchAgent"
)
@Agent(description = "Research a topic, critique it, and rewrite until acceptable")
public class ConditionalResearchAgent {

    public static final String DRAFT_OK = "draftSatisfactory";
    public static final String DRAFT_BAD = "draftUnsatisfactory";

    @Action(outputBinding = "fastDraft")
    public ResearchDraft draftFast(UserInput userInput, Ai ai) {
        return ai.withDefaultLlm().createObject(
                """
                        Write a brief research draft (3 sentences) about: %s
                        """.formatted(userInput.getContent()),
                ResearchDraft.class
        );
    }

    @Action
    public Critique critique(@RequireNameMatch ResearchDraft fastDraft, Ai ai) {
        return ai.withDefaultLlm().createObject(
                """
                        Critique this draft. Set accepted=true only if it is specific and useful.
                        Draft: %s
                        """.formatted(fastDraft.content()),
                Critique.class
        );
    }

    @Condition(name = DRAFT_OK)
    public boolean draftOk(Critique critique) {
        return critique.accepted();
    }

    @Condition(name = DRAFT_BAD)
    public boolean draftBad(Critique critique) {
        return !critique.accepted();
    }

    @Action(
            pre = {DRAFT_BAD},
            post = {DRAFT_OK},
            canRerun = true,
            outputBinding = "carefulDraft"
    )
    public ResearchDraft rewriteCarefully(
            UserInput userInput,
            @RequireNameMatch ResearchDraft fastDraft,
            Critique critique,
            Ai ai) {
        return ai.withDefaultLlm().createObject(
                """
                        Rewrite the draft carefully for topic [%s].
                        Previous draft: %s
                        Feedback: %s
                        """.formatted(userInput.getContent(), fastDraft.content(), critique.feedback()),
                ResearchDraft.class
        );
    }

    @AchievesGoal(description = "Publish an accepted research report")
    @Action(pre = {DRAFT_OK})
    public FinalReport publish(Critique critique, Ai ai, UserInput userInput) {
        // Prefer careful draft if present; Fake/unit tests can call methods directly.
        return ai.withDefaultLlm().createObject(
                """
                        Create a final report titled from the user input.
                        User input: %s
                        Critique feedback: %s
                        """.formatted(userInput.getContent(), critique.feedback()),
                FinalReport.class
        );
    }
}
