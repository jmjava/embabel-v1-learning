package com.embabel.learning.java.cookbook15;

import com.embabel.agent.api.common.Ai;
import com.embabel.learning.common.curriculum.CookbookChapter;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.TripBrief;
import org.springframework.stereotype.Component;

/**
 * Lesson 18: {@code createObjectIfPossible} — typed extraction that may return null.
 * <p>
 * Use this when the user message might not contain enough fields. A full
 * {@code createObject} / {@code creating().fromPrompt()} call would force a
 * hallucinated object; the nullable path lets the app ask a follow-up instead.
 *
 * @see CookbookChapter#CREATE_OBJECT_IF_POSSIBLE
 */
@Lesson(
        value = LessonOrder.COOKBOOK_CREATE_IF_POSSIBLE,
        title = "Cookbook 1.5 — createObjectIfPossible",
        guideRefs = {"Cookbook: Create Object If Possible"},
        counterpart = "com.embabel.learning.kotlin.cookbook15.PossibleTripPlanner"
)
@Component
public class PossibleTripPlanner {

    private final Ai ai;

    public PossibleTripPlanner(Ai ai) {
        this.ai = ai;
    }

    /**
     * Returns a {@link TripBrief} only when the prompt has destination + dates/duration.
     */
    public TripBrief planIfPossible(String userText) {
        return ai.withDefaultLlm()
                .withId("cookbook15-create-if-possible")
                .createObjectIfPossible(
                        """
                                Extract a trip brief only if the text names a destination
                                and a duration or dates. Otherwise return nothing.
                                Text: %s
                                """.formatted(userText),
                        TripBrief.class
                );
    }

    /**
     * Deterministic gate used by guided tests and Memory OS video scripts.
     */
    public static boolean looksSufficient(String userText) {
        var normalized = userText.toLowerCase();
        var hasPlace = normalized.contains("paris")
                || normalized.contains("london")
                || normalized.contains("rome")
                || normalized.contains("tokyo");
        var hasWhen = normalized.contains("day")
                || normalized.contains("weekend")
                || normalized.contains("friday")
                || normalized.contains("sunday");
        return hasPlace && hasWhen;
    }
}
