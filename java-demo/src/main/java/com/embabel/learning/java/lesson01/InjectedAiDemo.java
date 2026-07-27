package com.embabel.learning.java.lesson01;

import com.embabel.agent.api.common.Ai;
import com.embabel.learning.common.curriculum.Lesson;
import com.embabel.learning.common.curriculum.LessonOrder;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

/**
 * Lesson 01 (Java): use Embabel's {@link Ai} as a Spring bean.
 *
 * <h2>What to notice</h2>
 * <ul>
 *   <li>{@code creating(Class).withExample(...).fromPrompt(...)} for structured output</li>
 *   <li>Bean Validation on record fields constrains the generated object</li>
 *   <li>No {@code @Agent} / planner involved — just typed LLM help inside an app</li>
 * </ul>
 *
 * <h2>Nuances</h2>
 * <ul>
 *   <li>{@code withId("...")} names the interaction for logging/observability</li>
 *   <li>Examples teach both positive and negative cases to the model</li>
 *   <li>Counterpart Kotlin uses {@code @field:Pattern} use-site target on data classes</li>
 * </ul>
 *
 * @see com.embabel.learning.java.lesson02.WriteAndReviewAgent next lesson
 */
@Lesson(
        value = LessonOrder.INJECTED_AI,
        title = "Injected Ai without an Agent",
        guideRefs = {"2.4 Adding a Little AI to Your Application"},
        counterpart = "com.embabel.learning.kotlin.lesson01.InjectedAiDemo"
)
@Component
public class InjectedAiDemo {

    private final Ai ai;

    public InjectedAiDemo(Ai ai) {
        this.ai = ai;
    }

    /**
     * Invented animal constrained so species contains {@code ox}.
     */
    public record MagicalAnimal(
            String name,
            @Pattern(regexp = ".*ox.*", message = "Species must contain 'ox'")
            String species
    ) {
    }

    /**
     * Calls the LLM for a typed object with few-shot examples.
     *
     * <p><b>Breakpoint:</b> the {@code fromPrompt} line — inspect prompt builder state.
     */
    public MagicalAnimal inventAnimal() {
        return ai
                .withDefaultLlm()
                .withId("lesson01-invent-animal")
                .creating(MagicalAnimal.class)
                .withExample("good", new MagicalAnimal("Fluffox", "Magicox"))
                .withExample("bad: fails validation", new MagicalAnimal("Sparky", "Dragon"))
                .fromPrompt("""
                        You woke up in a magical forest.
                        Invent a fictional animal with a name and species.
                        """);
    }
}
