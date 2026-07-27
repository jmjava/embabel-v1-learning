package com.embabel.learning.java.lesson02;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Export;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.prompt.persona.Persona;
import com.embabel.agent.prompt.persona.RoleGoalBackstory;
import com.embabel.common.ai.model.LlmOptions;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.ReviewedStory;
import com.embabel.learning.common.domain.Story;
import org.springframework.beans.factory.annotation.Value;

/**
 * Lesson 02 (Java): canonical two-step GOAP agent.
 *
 * <h2>Type-driven plan</h2>
 * <ol>
 *   <li>{@link #craftStory} needs {@link UserInput}, produces {@link Story}</li>
 *   <li>{@link #reviewStory} needs {@link UserInput}+{@link Story}, produces {@link ReviewedStory}</li>
 *   <li>{@link AchievesGoal} on {@link #reviewStory} marks process completion</li>
 * </ol>
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>Action order in source does <em>not</em> dictate execution order — types do</li>
 *   <li>{@code withTemperature(0.7)} for creative writing; review uses {@code withAutoLlm()}</li>
 *   <li>{@link Export}{@code (remote=true)} exposes the goal for shell/MCP/A2A</li>
 *   <li>Java injects {@link Ai}; Kotlin examples often take {@code OperationContext}</li>
 * </ul>
 *
 * <h2>Debug breakpoints (guided test)</h2>
 * See {@code WriteAndReviewAgentGuidedTest}.
 */
@Lesson(
        value = LessonOrder.FIRST_AGENT,
        title = "First @Agent — Write and Review",
        guideRefs = {"2.5 Writing Your First Agent", "4.6 Annotation model"},
        counterpart = "com.embabel.learning.kotlin.lesson02.WriteAndReviewAgent"
)
@Agent(description = "Generate a short story from user input and critically review it")
public class WriteAndReviewAgent {

    static final RoleGoalBackstory WRITER = new RoleGoalBackstory(
            "Creative Storyteller",
            "Write engaging imaginative stories",
            "Former circus novelist with a PhD in folklore");

    static final Persona REVIEWER = new Persona(
            "Media Book Review",
            "New York Times Book Reviewer",
            "Professional and insightful",
            "Help guide readers toward good stories"
    );

    private final int storyWordCount;
    private final int reviewWordCount;

    public WriteAndReviewAgent(
            @Value("${storyWordCount:80}") int storyWordCount,
            @Value("${reviewWordCount:60}") int reviewWordCount) {
        this.storyWordCount = storyWordCount;
        this.reviewWordCount = reviewWordCount;
    }

    /**
     * First actionable step once {@link UserInput} is on the blackboard.
     */
    @Action
    public Story craftStory(UserInput userInput, Ai ai) {
        return ai
                .withLlm(LlmOptions.withAutoLlm().withTemperature(0.7))
                .withPromptContributor(WRITER)
                .creating(Story.class)
                .fromPrompt("""
                        Craft a short story in %d words or less.
                        Be engaging and imaginative.
                        Use the user input as inspiration.
                        
                        # User input
                        %s
                        """.formatted(storyWordCount, userInput.getContent()).trim());
    }

    /**
     * Goal action: available only after a {@link Story} exists.
     */
    @AchievesGoal(
            description = "The story has been crafted and reviewed",
            export = @Export(remote = true, name = "javaWriteAndReviewStory")
    )
    @Action
    public ReviewedStory reviewStory(UserInput userInput, Story story, Ai ai) {
        var review = ai
                .withAutoLlm()
                .withPromptContributor(REVIEWER)
                .generateText("""
                        Review this story in %d words or less.
                        Comment on engagement, imagination, and fit to the user input.
                        
                        # Story
                        %s
                        
                        # User input
                        %s
                        """.formatted(reviewWordCount, story.text(), userInput.getContent()).trim());
        return new ReviewedStory(story, review, 0.85);
    }
}
