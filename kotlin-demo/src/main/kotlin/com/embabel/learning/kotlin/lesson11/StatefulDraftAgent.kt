package com.embabel.learning.kotlin.lesson11

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.annotation.State
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder

/**
 * Lesson 11 (Kotlin): `@State` loops.
 *
 * **Nuance:** keep state classes top-level (or non-inner). Avoid `inner` classes that capture
 * the outer agent instance — that breaks serialization / planning assumptions.
 */
@Lesson(
    value = LessonOrder.STATES,
    title = "@State machines and loops",
    guideRefs = ["4.19 Using States"],
    counterpart = "com.embabel.learning.java.lesson11.StatefulDraftAgent",
)
@Agent(description = "Iteratively refine a draft using explicit states")
class StatefulDraftAgent {

    sealed interface LoopOutcome

    @State
    data class Drafting(val topic: String, val draft: String, val iteration: Int) : LoopOutcome {
        @Action(clearBlackboard = true)
        fun refine(context: OperationContext): LoopOutcome {
            if (iteration >= 2) return Done(topic, draft)
            val improved = context.ai().withDefaultLlm().generateText(
                "Improve this draft (iteration ${iteration + 1}) about $topic:\n$draft",
            )
            return Drafting(topic, improved, iteration + 1)
        }
    }

    @State
    data class Done(val topic: String, val draft: String) : LoopOutcome {
        @AchievesGoal(description = "Draft refinement complete")
        @Action
        fun finish(): FinishedReport = FinishedReport(topic, draft)
    }

    data class FinishedReport(val topic: String, val finalDraft: String)

    @Action
    fun start(userInput: UserInput, context: OperationContext): Drafting {
        val draft = context.ai().withDefaultLlm().generateText(
            "Write a rough 2-sentence draft about: ${userInput.content}",
        )
        return Drafting(userInput.content, draft, 0)
    }
}
