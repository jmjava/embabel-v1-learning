package com.embabel.learning.kotlin.cookbook15

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.Ai
import com.embabel.agent.domain.io.UserInput
import com.embabel.learning.common.curriculum.Lesson
import com.embabel.learning.common.curriculum.LessonOrder
import com.embabel.learning.common.domain.TravelIntent
import com.embabel.learning.common.domain.TripBrief
import com.embabel.learning.common.domain.TripOption

/**
 * Lesson 16 (Kotlin): cookbook-style domain type chaining.
 *
 * Counterpart: `com.embabel.learning.java.cookbook15.TypeChainingTravelAgent`
 */
@Lesson(
    value = LessonOrder.COOKBOOK_TYPE_CHAINING,
    title = "Cookbook 1.5 — domain type chaining",
    guideRefs = ["Cookbook: Action Domain Type Chaining"],
    counterpart = "com.embabel.learning.java.cookbook15.TypeChainingTravelAgent",
)
@Agent(description = "Classify a travel request, then plan a flight or itinerary")
class TypeChainingTravelAgent {

    sealed interface TravelRequest {
        val userInput: UserInput
    }

    data class FlightRequest(override val userInput: UserInput) : TravelRequest
    data class ItineraryRequest(override val userInput: UserInput) : TravelRequest

    sealed interface TravelActivity {
        val brief: TripBrief
    }

    data class FlightBrief(override val brief: TripBrief) : TravelActivity
    data class ItineraryBrief(override val brief: TripBrief) : TravelActivity

    @Action
    fun classify(userInput: UserInput, ai: Ai): TravelRequest {
        val text = userInput.content
        val intent = when {
            looksLikeFlight(text) -> TravelIntent.FLIGHT
            looksLikeItinerary(text) -> TravelIntent.ITINERARY
            else -> ai.withDefaultLlm().createObject(
                "Classify this request as FLIGHT or ITINERARY.\nRequest: $text",
                TravelIntent::class.java,
            )
        }
        return when (intent) {
            TravelIntent.FLIGHT -> FlightRequest(userInput)
            TravelIntent.ITINERARY, TravelIntent.UNKNOWN -> ItineraryRequest(userInput)
        }
    }

    @Action
    fun planFlight(request: FlightRequest, ai: Ai): FlightBrief =
        FlightBrief(
            ai.withDefaultLlm().createObject(
                "Recommend one specific flight for: ${request.userInput.content}",
                TripBrief::class.java,
            ),
        )

    @Action
    fun planItinerary(request: ItineraryRequest, ai: Ai): ItineraryBrief =
        ItineraryBrief(
            ai.withDefaultLlm().createObject(
                "Build a concise day-plan itinerary for: ${request.userInput.content}",
                TripBrief::class.java,
            ),
        )

    @AchievesGoal(description = "A flight or itinerary recommendation is ready")
    @Action
    fun summarize(activity: TravelActivity): TripOption {
        val label = if (activity is FlightBrief) "flight" else "itinerary"
        return TripOption(label, "${activity.brief.highlight()} — ${activity.brief.itineraryDescription()}")
    }

    companion object {
        fun looksLikeFlight(text: String): Boolean {
            val normalized = text.lowercase()
            return "flight" in normalized || "fly" in normalized || "airport" in normalized
        }

        fun looksLikeItinerary(text: String): Boolean {
            val normalized = text.lowercase()
            return "itinerary" in normalized || "day in" in normalized || "museums" in normalized
        }
    }
}
