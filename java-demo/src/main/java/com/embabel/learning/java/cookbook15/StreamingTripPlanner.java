package com.embabel.learning.java.cookbook15;

import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PromptRunner;
import com.embabel.agent.api.streaming.StreamingPromptRunnerBuilder;
import com.embabel.learning.common.curriculum.CookbookChapter;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import com.embabel.learning.common.domain.TripOption;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Lesson 20: Embabel 1.5 typed object streaming.
 * <p>
 * {@link StreamingPromptRunnerBuilder} turns a prompt into a Flux of
 * {@link TripOption} records as the model emits each JSON object.
 *
 * @see CookbookChapter#STREAMING
 */
@Lesson(
        value = LessonOrder.COOKBOOK_STREAMING,
        title = "Cookbook 1.5 — streaming objects",
        guideRefs = {"Cookbook: Streaming"},
        counterpart = "com.embabel.learning.kotlin.cookbook15.StreamingTripPlanner"
)
@Component
public class StreamingTripPlanner {

    private final Ai ai;

    public StreamingTripPlanner(Ai ai) {
        this.ai = ai;
    }

    public List<TripOption> streamOptions(String userText) {
        PromptRunner runner = ai.withDefaultLlm().withId("cookbook15-streaming");
        if (!runner.supportsStreaming()) {
            throw new IllegalStateException(
                    "Current PromptRunner does not support streaming(); use a 1.5 model provider");
        }
        List<TripOption> options = new CopyOnWriteArrayList<>();
        new StreamingPromptRunnerBuilder(runner)
                .streaming()
                .withPrompt("""
                        Return exactly three distinct itinerary options for:
                        %s
                        Emit each option as a separate JSON object with label and text.
                        """.formatted(userText))
                .createObjectStream(TripOption.class)
                .timeout(Duration.ofSeconds(120))
                .doOnNext(options::add)
                .blockLast(Duration.ofSeconds(120));
        return List.copyOf(options);
    }
}
