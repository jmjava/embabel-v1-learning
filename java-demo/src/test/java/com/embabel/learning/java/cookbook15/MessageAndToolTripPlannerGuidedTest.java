package com.embabel.learning.java.cookbook15;

import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.agent.test.unit.FakePromptRunner;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.TripBrief;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageAndToolTripPlannerGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "MessageAndToolTripPlanner#planFromMessages",
                    "creating(TripBrief.class).fromMessages"
            },
            watch = "system + user messages; created TripBrief"
    )
    void fromMessagesPromptContainsDestination() {
        var ctx = FakeOperationContext.create();
        ctx.expectResponse(new TripBrief("Paris", "Eurostar", "Three days", "Eiffel Tower"));
        var planner = new MessageAndToolTripPlanner(ctx.ai());
        var brief = planner.planFromMessages(
                "a three-day trip from London to Paris",
                "Include Eurostar, the Eiffel Tower, and a Seine walk."
        );
        assertEquals("Paris", brief.destination());
        FakePromptRunner runner = ctx.getPromptRunner();
        var prompt = runner.getLlmInvocations().getFirst().getMessages().getFirst().getContent();
        assertTrue(prompt.toLowerCase().contains("paris") || prompt.toLowerCase().contains("travel"), prompt);
    }

    @Test
    void lookupToolsAreDeterministic() {
        var tools = new MessageAndToolTripPlanner.TravelLookupTools();
        assertTrue(tools.getWeather("Paris").contains("Paris"));
        assertTrue(tools.getTopAttractions("Paris").contains("museum"));
    }

    @Test
    @DebugGuide(
            breakpoints = {
                    "MessageAndToolTripPlanner#planWithTools",
                    "withToolObject(TravelLookupTools)"
            },
            watch = "weather and attractions tools on the prompt runner"
    )
    void planWithToolsAttachesWeatherAndAttractions() {
        var ctx = FakeOperationContext.create();
        ctx.expectResponse(new TripBrief("Paris", "train", "3 days", "Louvre"));
        var brief = new MessageAndToolTripPlanner(ctx.ai()).planWithTools("Paris");
        assertEquals("Paris", brief.destination());

        var invocation = ctx.getLlmInvocations().getFirst();
        var prompt = invocation.getPrompt();
        assertTrue(prompt.contains("Paris"), prompt);
        assertTrue(prompt.toLowerCase().contains("weather"), prompt);
        assertTrue(prompt.toLowerCase().contains("attractions"), prompt);

        var names = invocation.getInteraction().getTools().stream()
                .map(tool -> tool.getDefinition().getName().toLowerCase())
                .toList();
        assertTrue(names.stream().anyMatch(name -> name.contains("weather")), names.toString());
        assertTrue(names.stream().anyMatch(name -> name.contains("attraction")), names.toString());
    }
}
