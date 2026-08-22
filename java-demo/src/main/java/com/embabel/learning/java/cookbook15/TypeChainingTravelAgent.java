package com.embabel.learning.java.cookbook15;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.CookbookChapter;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.TravelIntent;
import com.embabel.learning.common.domain.TripBrief;
import com.embabel.learning.common.domain.TripOption;

/**
 * Lesson 16: cookbook-style domain type chaining.
 * <p>
 * Classify {@link UserInput} into {@link TravelIntent}, then let GOAP pick
 * {@link #planFlight} or {@link #planItinerary} from the produced type.
 *
 * @see CookbookChapter#ACTION_DOMAIN_TYPE_CHAINING
 */
@Lesson(
        value = LessonOrder.COOKBOOK_TYPE_CHAINING,
        title = "Cookbook 1.5 — domain type chaining",
        guideRefs = {"Cookbook: Action Domain Type Chaining"},
        counterpart = "com.embabel.learning.kotlin.cookbook15.TypeChainingTravelAgent"
)
@Agent(description = "Classify a travel request, then plan a flight or itinerary")
public class TypeChainingTravelAgent {

    public sealed interface TravelRequest permits FlightRequest, ItineraryRequest {
        UserInput userInput();
    }

    public record FlightRequest(UserInput userInput) implements TravelRequest {
    }

    public record ItineraryRequest(UserInput userInput) implements TravelRequest {
    }

    public sealed interface TravelActivity permits FlightBrief, ItineraryBrief {
        TripBrief brief();
    }

    public record FlightBrief(TripBrief brief) implements TravelActivity {
    }

    public record ItineraryBrief(TripBrief brief) implements TravelActivity {
    }

    @Action
    public TravelRequest classify(UserInput userInput, Ai ai) {
        var text = userInput.getContent();
        TravelIntent intent;
        if (looksLikeFlight(text)) {
            intent = TravelIntent.FLIGHT;
        } else if (looksLikeItinerary(text)) {
            intent = TravelIntent.ITINERARY;
        } else {
            intent = ai.withDefaultLlm().createObject(
                    """
                            Classify this request as FLIGHT or ITINERARY.
                            Request: %s
                            """.formatted(text),
                    TravelIntent.class
            );
        }
        return switch (intent) {
            case FLIGHT -> new FlightRequest(userInput);
            case ITINERARY -> new ItineraryRequest(userInput);
            case UNKNOWN -> new ItineraryRequest(userInput);
        };
    }

    @Action
    public FlightBrief planFlight(FlightRequest request, Ai ai) {
        return new FlightBrief(ai.withDefaultLlm().createObject(
                """
                        Recommend one specific flight for: %s
                        Use airport codes in the highlight field.
                        """.formatted(request.userInput().getContent()),
                TripBrief.class
        ));
    }

    @Action
    public ItineraryBrief planItinerary(ItineraryRequest request, Ai ai) {
        return new ItineraryBrief(ai.withDefaultLlm().createObject(
                """
                        Build a concise day-plan itinerary for: %s
                        """.formatted(request.userInput().getContent()),
                TripBrief.class
        ));
    }

    @AchievesGoal(description = "A flight or itinerary recommendation is ready")
    @Action
    public TripOption summarize(TravelActivity activity) {
        var label = activity instanceof FlightBrief ? "flight" : "itinerary";
        return new TripOption(label, activity.brief().highlight() + " — " + activity.brief().itineraryDescription());
    }

    static boolean looksLikeFlight(String text) {
        var normalized = text.toLowerCase();
        return normalized.contains("flight") || normalized.contains("fly") || normalized.contains("airport");
    }

    static boolean looksLikeItinerary(String text) {
        var normalized = text.toLowerCase();
        return normalized.contains("itinerary") || normalized.contains("day in") || normalized.contains("museums");
    }
}
