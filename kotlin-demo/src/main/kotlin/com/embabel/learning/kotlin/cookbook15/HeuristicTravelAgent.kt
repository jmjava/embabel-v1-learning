package com.embabel.learning.kotlin.cookbook15

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.PlannerType
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.TripOption

/**
 * Lesson 17 (Kotlin): GOAP action cost as a planner heuristic.
 *
 * Counterpart: `com.embabel.learning.java.cookbook15.HeuristicTravelAgent`
 */
@Lesson(
    value = LessonOrder.COOKBOOK_HEURISTICS,
    title = "Cookbook 1.5 — action cost heuristics",
    guideRefs = ["Cookbook: Action Heuristics"],
    counterpart = "com.embabel.learning.java.cookbook15.HeuristicTravelAgent",
)
@Agent(description = "Show how action cost steers the GOAP planner", planner = PlannerType.GOAP)
class HeuristicTravelAgent {

    @AchievesGoal(description = "The user has received a travel recommendation")
    @Action(description = "Build the cheapest recommendation", cost = 0.1)
    fun buildCheapestRecommendation(userInput: UserInput): TripOption =
        TripOption("cheapest", CHEAPEST)

    @AchievesGoal(description = "The user has received a travel recommendation")
    @Action(description = "Build the premium recommendation", cost = 0.9)
    fun buildPremiumRecommendation(userInput: UserInput): TripOption =
        TripOption("premium", PREMIUM)

    companion object {
        const val CHEAPEST = "Cheapest travel recommendation selected."
        const val PREMIUM = "Premium travel recommendation selected."
    }
}
