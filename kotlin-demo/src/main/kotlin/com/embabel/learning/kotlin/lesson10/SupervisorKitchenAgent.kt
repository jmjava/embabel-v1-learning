package com.embabel.learning.kotlin.lesson10

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.PlannerType
import com.embabel.agent.api.common.createObject
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder

/**
 * Lesson 10a (Kotlin): Supervisor planner.
 */
@Lesson(
    value = LessonOrder.PLANNERS,
    title = "Supervisor planner kitchen",
    guideRefs = ["4.20.3 Supervisor"],
    counterpart = "com.embabel.learning.java.lesson10.SupervisorKitchenAgent",
)
@Agent(
    description = "Kitchen supervisor that chooses cook, takes order, prepares meal",
    planner = PlannerType.SUPERVISOR,
)
class SupervisorKitchenAgent {

    data class Cook(val name: String, val age: Int)
    data class Order(val dish: String, val quantity: Int)
    data class Meal(val dish: String, val quantity: Int, val orderedBy: String, val cookedBy: String)

    @Action(description = "Choose which cook should prepare the meal")
    fun chooseCook(userInput: UserInput, context: OperationContext): Cook =
        context.ai().withAutoLlm().createObject(
            "From the user input, choose a cook (name + age). User input: ${userInput.content}",
        )

    @Action(description = "Take the food order from the user")
    fun takeOrder(userInput: UserInput, context: OperationContext): Order =
        context.ai().withAutoLlm().createObject(
            "From the user input, take a food order (dish + quantity). User input: ${userInput.content}",
        )

    @AchievesGoal(description = "Cook the meal according to the order")
    @Action(description = "Prepare the final meal from cook + order")
    fun prepareMeal(cook: Cook, order: Order, userInput: UserInput, context: OperationContext): Meal =
        context.ai().withAutoLlm().createObject(
            """
            Prepare a meal.
            Cook: ${cook.name} age ${cook.age}
            Order: ${order.quantity} x ${order.dish}
            Customer text: ${userInput.content}
            """.trimIndent(),
        )
}
