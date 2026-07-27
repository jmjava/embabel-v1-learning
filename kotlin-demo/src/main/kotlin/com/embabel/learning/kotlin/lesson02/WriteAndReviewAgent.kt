package com.embabel.learning.kotlin.lesson02

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.annotation.Export
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.create
import com.embabel.agent.domain.io.UserInput
import com.embabel.agent.prompt.persona.Persona
import com.embabel.agent.prompt.persona.RoleGoalBackstory
import com.embabel.common.ai.model.LlmOptions
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.ReviewedStory
import com.embabel.learning.common.domain.Story
import org.springframework.beans.factory.annotation.Value

val StoryTeller = RoleGoalBackstory(
    role = "Creative Storyteller",
    goal = "Write engaging imaginative stories",
    backstory = "Former circus novelist with a PhD in folklore",
)

val Reviewer = Persona(
    name = "Media Book Review",
    persona = "New York Times Book Reviewer",
    voice = "Professional and insightful",
    objective = "Help guide readers toward good stories",
)

/**
 * Lesson 02 (Kotlin): first `@Agent` with GOAP type flow.
 *
 * **Nuance vs Java:** Kotlin actions typically take [OperationContext] and use
 * reified `create<T>()` / `createObject<T>()` extensions instead of `Ai` + `Class`.
 */
@Lesson(
    value = LessonOrder.FIRST_AGENT,
    title = "First @Agent — Write and Review",
    guideRefs = ["2.5 Writing Your First Agent", "4.6 Annotation model"],
    counterpart = "com.embabel.learning.java.lesson02.WriteAndReviewAgent",
)
@Agent(description = "Generate a short story from user input and critically review it")
class WriteAndReviewAgent(
    @param:Value("\${storyWordCount:80}") private val storyWordCount: Int,
    @param:Value("\${reviewWordCount:60}") private val reviewWordCount: Int,
) {

    @Action
    fun craftStory(userInput: UserInput, context: OperationContext): Story =
        context.ai()
            .withLlm(LlmOptions.withAutoLlm().withTemperature(0.7))
            .withPromptContributor(StoryTeller)
            .create(
                """
                Craft a short story in $storyWordCount words or less.
                Be engaging and imaginative.
                Use the user input as inspiration.

                # User input
                ${userInput.content}
                """.trimIndent(),
            )

    @AchievesGoal(
        description = "The story has been crafted and reviewed",
        export = Export(remote = true, name = "kotlinWriteAndReviewStory"),
    )
    @Action
    fun reviewStory(userInput: UserInput, story: Story, context: OperationContext): ReviewedStory {
        val review = context.ai()
            .withAutoLlm()
            .withPromptContributor(Reviewer)
            .generateText(
                """
                Review this story in $reviewWordCount words or less.
                Comment on engagement, imagination, and fit to the user input.

                # Story
                ${story.text}

                # User input
                ${userInput.content}
                """.trimIndent(),
            )
        return ReviewedStory(story, review, 0.85)
    }
}
