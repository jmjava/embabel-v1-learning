package com.embabel.learning.java.cookbook15;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HeuristicTravelAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "HeuristicTravelAgent#buildCheapestRecommendation",
                    "@Action cost = 0.1 vs cost = 0.9"
            },
            watch = "action cost; selected label"
    )
    void cheapestAndPremiumActionsAreDistinct() {
        var agent = new HeuristicTravelAgent();
        var input = new UserInput("Choose a travel recommendation");
        var cheap = agent.buildCheapestRecommendation(input);
        var premium = agent.buildPremiumRecommendation(input);
        assertEquals("cheapest", cheap.label());
        assertEquals(HeuristicTravelAgent.CHEAPEST, cheap.text());
        assertEquals("premium", premium.label());
        assertTrue(premium.text().contains("Premium"));
    }
}
