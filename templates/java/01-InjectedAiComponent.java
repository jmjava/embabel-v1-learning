package com.example.embabel.starter;

import com.embabel.agent.api.common.Ai;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

/**
 * Starter: use Embabel Ai without an @Agent.
 * Copy → rename → inject into shell/controllers.
 */
@Component
public class InjectedAiComponent {

    private final Ai ai;

    public InjectedAiComponent(Ai ai) {
        this.ai = ai;
    }

    public record InventedThing(
            String name,
            @Pattern(regexp = ".+", message = "kind required")
            String kind
    ) {
    }

    public InventedThing invent(String topic) {
        return ai.withDefaultLlm()
                .withId("invent-thing")
                .creating(InventedThing.class)
                .withExample("good", new InventedThing("Fluffox", "creature"))
                .fromPrompt("""
                        Invent a fictional thing related to: %s
                        """.formatted(topic));
    }
}
