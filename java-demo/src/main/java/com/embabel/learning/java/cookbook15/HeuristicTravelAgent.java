package com.embabel.learning.java.cookbook15;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.PlannerType;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.CookbookChapter;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.TripOption;

/**
 * Lesson 17: GOAP action {@code cost} as a planner heuristic.
 * <p>
 * Both actions achieve the same goal type. The cheaper one wins when either
 * is eligible — this is the cookbook "action heuristics" recipe.
 *
 * @see CookbookChapter#ACTION_HEURISTICS
 */
@Lesson(
        value = LessonOrder.COOKBOOK_HEURISTICS,
        title = "Cookbook 1.5 — action cost heuristics",
        guideRefs = {"Cookbook: Action Heuristics"},
        counterpart = "com.embabel.learning.kotlin.cookbook15.HeuristicTravelAgent"
)
@Agent(description = "Show how action cost steers the GOAP planner", planner = PlannerType.GOAP)
public class HeuristicTravelAgent {

    public static final String CHEAPEST = "Cheapest travel recommendation selected.";
    public static final String PREMIUM = "Premium travel recommendation selected.";

    @AchievesGoal(description = "The user has received a travel recommendation")
    @Action(description = "Build the cheapest recommendation", cost = 0.1)
    public TripOption buildCheapestRecommendation(UserInput userInput) {
        return new TripOption("cheapest", CHEAPEST);
    }

    @AchievesGoal(description = "The user has received a travel recommendation")
    @Action(description = "Build the premium recommendation", cost = 0.9)
    public TripOption buildPremiumRecommendation(UserInput userInput) {
        return new TripOption("premium", PREMIUM);
    }
}
