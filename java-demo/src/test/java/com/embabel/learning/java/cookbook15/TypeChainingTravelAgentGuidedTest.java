package com.embabel.learning.java.cookbook15;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.TripBrief;
import com.embabel.learning.common.domain.TripOption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TypeChainingTravelAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "TypeChainingTravelAgent#classify",
                    "TypeChainingTravelAgent#planFlight",
                    "TypeChainingTravelAgent#summarize"
            },
            watch = "TravelRequest type; TripBrief.highlight"
    )
    void flightCueChainsToFlightRequestWithoutLlm() {
        var agent = new TypeChainingTravelAgent();
        var input = new UserInput("Find me a flight from New York to London");
        var ctx = FakeOperationContext.create();

        var request = agent.classify(input, ctx.ai());
        assertInstanceOf(TypeChainingTravelAgent.FlightRequest.class, request);

        ctx.expectResponse(new TripBrief("London", "BA178", "JFK to LHR overnight", "LHR"));
        var brief = agent.planFlight((TypeChainingTravelAgent.FlightRequest) request, ctx.ai());
        TripOption option = agent.summarize(brief);
        assertEquals("flight", option.label());
        assertTrue(option.text().contains("LHR"));
    }

    @Test
    void itineraryCueChainsToItineraryRequest() {
        assertTrue(TypeChainingTravelAgent.looksLikeItinerary("Plan a day in London around museums"));
        var agent = new TypeChainingTravelAgent();
        var request = agent.classify(
                new UserInput("Plan a day in London around museums"),
                FakeOperationContext.create().ai()
        );
        assertInstanceOf(TypeChainingTravelAgent.ItineraryRequest.class, request);
    }
}
