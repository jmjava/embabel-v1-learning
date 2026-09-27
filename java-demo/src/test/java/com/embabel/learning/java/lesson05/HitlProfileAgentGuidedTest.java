package com.embabel.learning.java.lesson05;

import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.core.hitl.AwaitableResponseException;
import com.embabel.agent.core.hitl.FormBindingRequest;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.PersonProfile;
import com.embabel.ux.form.Form;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HitlProfileAgentGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "HitlProfileAgent#extractProfile — cheap path",
                    "HitlProfileAgent#askUserForProfile — WaitFor.formSubmission",
                    "process WAITING status after HITL action"
            },
            watch = "Action.cost() == 100.0 on HITL fallback"
    )
    void hitlFallbackIsMarkedExpensive() throws Exception {
        Action action = HitlProfileAgent.class
                .getDeclaredMethod("askUserForProfile", UserInput.class)
                .getAnnotation(Action.class);
        assertEquals(100.0, action.cost(), 0.0);
    }

    @Test
    @DebugGuide(
            breakpoints = {"HitlProfileAgent#askUserForProfile"},
            watch = "form title keeps the original user input"
    )
    void askUserForProfileKeepsTheUserInput() {
        var text = "Ada likes computing but the extractor missed it";
        var thrown = assertThrows(
                AwaitableResponseException.class,
                () -> new HitlProfileAgent().askUserForProfile(new UserInput(text))
        );
        assertInstanceOf(FormBindingRequest.class, thrown.getAwaitable());
        var request = (FormBindingRequest<?>) thrown.getAwaitable();
        assertEquals(PersonProfile.class, request.getOutputClass());
        var title = ((Form) request.getPayload()).getTitle();
        assertTrue(title.contains("Please provide name and interest"), title);
        assertTrue(title.contains("input was:"), title);
        assertTrue(title.contains(text), title);
    }
}
