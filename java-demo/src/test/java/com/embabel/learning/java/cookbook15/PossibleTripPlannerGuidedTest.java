package com.embabel.learning.java.cookbook15;

import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.TripBrief;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PossibleTripPlannerGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "PossibleTripPlanner#planIfPossible",
                    "PromptRunner#createObjectIfPossible"
            },
            watch = "userText sufficiency; returned TripBrief or null"
    )
    void sufficientPromptCreatesTripBrief() {
        var ctx = FakeOperationContext.create();
        ctx.expectResponse(new TripBrief("Paris", "Eurostar", "Friday to Sunday", "Louvre"));
        var planner = new PossibleTripPlanner(ctx.ai());
        var brief = planner.planIfPossible(
                "Plan a three-day itinerary from London to Paris for Friday to Sunday."
        );
        assertEquals("Paris", brief.destination());
        assertTrue(PossibleTripPlanner.looksSufficient(
                "Plan a three-day itinerary from London to Paris for Friday to Sunday."
        ));
    }

    @Test
    void vaguePromptIsNotSufficient() {
        assertFalse(PossibleTripPlanner.looksSufficient("Plan a trip."));
    }
}
