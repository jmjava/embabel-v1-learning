package com.example.embabel.starter;

import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.EmbabelComponent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;

/**
 * Starter: reusable actions that are not a full agent.
 * Useful with Utility/Supervisor scopes and shared libraries.
 */
@EmbabelComponent
public class EmbabelComponentLibrary {

    public record ExtractedTopic(String topic) {
    }

    @Action(description = "Extract a concise topic from user input")
    public ExtractedTopic extractTopic(UserInput userInput, Ai ai) {
        return ai.withAutoLlm().createObject(
                "Extract a short topic from: " + userInput.getContent(),
                ExtractedTopic.class
        );
    }
}
