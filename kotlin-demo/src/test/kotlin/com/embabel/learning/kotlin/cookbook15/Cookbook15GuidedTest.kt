package com.embabel.learning.kotlin.cookbook15

import com.embabel.agent.domain.io.UserInput
import com.embabel.agent.test.unit.FakeOperationContext
import com.embabel.learning.common.curriculum.DebugGuide
import com.embabel.learning.common.domain.TripBrief
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Cookbook15GuidedTest {

    @Test
    @DebugGuide(
        breakpoints = ["TypeChainingTravelAgent#classify", "TypeChainingTravelAgent#planFlight"],
        watch = "TravelRequest type",
    )
    fun `flight cue classifies without an LLM`() {
        val agent = TypeChainingTravelAgent()
        val request = agent.classify(
            UserInput("Find me a flight from New York to London"),
            FakeOperationContext.create().ai(),
        )
        assertTrue(request is TypeChainingTravelAgent.FlightRequest)
    }

    @Test
    fun `createObjectIfPossible returns the fake brief`() {
        val ctx = FakeOperationContext.create()
        ctx.expectResponse(TripBrief("Paris", "Eurostar", "Weekend", "Louvre"))
        val brief = PossibleTripPlanner(ctx.ai()).planIfPossible(
            "Plan a weekend in Paris from Friday to Sunday.",
        )
        assertEquals("Paris", brief?.destination)
        assertTrue(PossibleTripPlanner.looksSufficient("Plan a weekend in Paris from Friday to Sunday."))
    }

    @Test
    fun `heuristics expose cheapest and premium options`() {
        val agent = HeuristicTravelAgent()
        val input = UserInput("Choose a travel recommendation")
        assertEquals("cheapest", agent.buildCheapestRecommendation(input).label())
        assertEquals("premium", agent.buildPremiumRecommendation(input).label())
    }
}
