package com.embabel.learning.java.lesson10;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.EmbabelComponent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PlannerType;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;

/**
 * Lesson 10a (Java): Supervisor planner — LLM selects typed actions as tools.
 *
 * <h2>When to use</h2>
 * Flexible multi-step composition where GOAP's cheapest-path determinism is too rigid.
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>Non-deterministic and costs extra LLM calls for orchestration</li>
 *   <li>Still type-informed: actions become available as preconditions are satisfied</li>
 *   <li>Action {@code description} text matters — the supervisor LLM reads it</li>
 * </ul>
 */
@Lesson(
        value = LessonOrder.PLANNERS,
        title = "Supervisor planner kitchen",
        guideRefs = {"4.20 Choosing a Planner", "4.20.3 Supervisor"},
        counterpart = "com.embabel.learning.kotlin.lesson10.SupervisorKitchenAgent"
)
@Agent(
        description = "Kitchen supervisor that chooses cook, takes order, prepares meal",
        planner = PlannerType.SUPERVISOR
)
public class SupervisorKitchenAgent {

    public record Cook(String name, int age) {
    }

    public record Order(String dish, int quantity) {
    }

    public record Meal(String dish, int quantity, String orderedBy, String cookedBy) {
    }

    @Action(description = "Choose which cook should prepare the meal")
    public Cook chooseCook(UserInput userInput, Ai ai) {
        return ai.withAutoLlm().createObject(
                """
                        From the user input, choose a cook (name + age).
                        User input: %s
                        """.formatted(userInput.getContent()),
                Cook.class
        );
    }

    @Action(description = "Take the food order from the user")
    public Order takeOrder(UserInput userInput, Ai ai) {
        return ai.withAutoLlm().createObject(
                """
                        From the user input, take a food order (dish + quantity).
                        User input: %s
                        """.formatted(userInput.getContent()),
                Order.class
        );
    }

    @AchievesGoal(description = "Cook the meal according to the order")
    @Action(description = "Prepare the final meal from cook + order")
    public Meal prepareMeal(Cook cook, Order order, UserInput userInput, Ai ai) {
        return ai.withAutoLlm().createObject(
                """
                        Prepare a meal.
                        Cook: %s age %d
                        Order: %d x %s
                        Customer text: %s
                        """.formatted(cook.name(), cook.age(), order.quantity(), order.dish(),
                        userInput.getContent()),
                Meal.class
        );
    }
}
