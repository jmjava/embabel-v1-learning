package com.example.embabel.starter

import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.EmbabelComponent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.createObject
import com.embabel.agent.domain.io.UserInput

/**
 * Starter: reusable actions that are not a full agent.
 * Useful with Utility/Supervisor scopes and shared libraries.
 */
@EmbabelComponent
class EmbabelComponentLibrary {

    data class ExtractedTopic(val topic: String)

    @Action(description = "Extract a concise topic from user input")
    fun extractTopic(userInput: UserInput, context: OperationContext): ExtractedTopic =
        context.ai().withAutoLlm().createObject(
            "Extract a short topic from: ${userInput.content}",
        )
}
