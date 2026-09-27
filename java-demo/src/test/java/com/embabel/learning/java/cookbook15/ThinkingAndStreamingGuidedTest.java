package com.embabel.learning.java.cookbook15;

import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PromptRunner;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.learning.common.curriculum.DebugGuide;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThinkingAndStreamingGuidedTest {

    @Test
    @DebugGuide(
            breakpoints = {
                    "ThinkingTripPlanner#planWithThinking",
                    "PromptRunner#supportsThinking",
                    "PromptRunner#thinking"
            },
            watch = "supportsThinking(); ThinkingResponse.getThinkingContent()"
    )
    void thinkingPromptAsksForDecisionReasoning() {
        assertTrue(ThinkingTripPlanner.REASONING_SYSTEM_PROMPT.contains("<decision_reasoning>"));
        var runner = FakeOperationContext.create().ai().withDefaultLlm();
        // Fake runners used in guided tests typically do not advertise live thinking.
        // The production planner guards on supportsThinking() before calling thinking().
        assertFalse(runner.supportsThinking() && !ThinkingTripPlanner.REASONING_SYSTEM_PROMPT.contains("trade-offs"));
    }

    @Test
    @DebugGuide(
            breakpoints = {
                    "StreamingTripPlanner#streamOptions",
                    "StreamingPromptRunnerBuilder#createObjectStream"
            },
            watch = "supportsStreaming(); collected TripOption list"
    )
    void streamingPlannerGuardsWhenRunnerCannotStream() {
        var denied = assertThrows(IllegalStateException.class, () ->
                new StreamingTripPlanner(FakeOperationContext.create().ai())
                        .streamOptions("three days London to Paris"));
        assertTrue(denied.getMessage().contains("streaming"));

        PromptRunner claiming = claimingRunnerThatDoesNotStream();
        assertTrue(claiming.supportsStreaming(),
                "fixture must claim streaming so a swallowed boolean guard cannot pass");
        var swallowed = assertThrows(UnsupportedOperationException.class, () ->
                new StreamingTripPlanner(aiReturning(claiming))
                        .streamOptions("three days London to Paris"));
        assertTrue(swallowed.getMessage().toLowerCase(Locale.ROOT).contains("streaming"),
                "a runner that claims supportsStreaming() but does not stream must fail the guard");
    }

    /**
     * Reports {@code supportsStreaming() == true} and then refuses to stream.
     * Returning success from {@code streamOptions} would mean the guard was swallowed.
     */
    private static PromptRunner claimingRunnerThatDoesNotStream() {
        return (PromptRunner) Proxy.newProxyInstance(
                PromptRunner.class.getClassLoader(),
                new Class<?>[]{PromptRunner.class},
                (proxy, method, args) -> dispatchClaimingRunner(proxy, method, args));
    }

    private static Object dispatchClaimingRunner(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return objectIdentity(proxy, method, args);
        }
        return switch (method.getName()) {
            case "supportsStreaming" -> true;
            case "withId", "withInteractionId", "withLlm" -> proxy;
            case "streaming", "stream" -> throw new UnsupportedOperationException(
                    "runner claims supportsStreaming() but does not stream");
            default -> method.isDefault()
                    ? InvocationHandler.invokeDefault(proxy, method, args)
                    : defaultValue(method.getReturnType());
        };
    }

    private static Ai aiReturning(PromptRunner runner) {
        return (Ai) Proxy.newProxyInstance(
                Ai.class.getClassLoader(),
                new Class<?>[]{Ai.class},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() == Object.class) {
                        return objectIdentity(proxy, method, args);
                    }
                    if ("withLlm".equals(method.getName()) || "withDefaultLlm".equals(method.getName())) {
                        return runner;
                    }
                    if (method.isDefault()) {
                        return InvocationHandler.invokeDefault(proxy, method, args);
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
    }

    private static Object objectIdentity(Object proxy, Method method, Object[] args) {
        return switch (method.getName()) {
            case "toString" -> "claiming-non-streaming-runner";
            case "hashCode" -> System.identityHashCode(proxy);
            case "equals" -> proxy == args[0];
            default -> null;
        };
    }

    private static Object defaultValue(Class<?> type) {
        if (type == boolean.class) {
            return false;
        }
        if (type == int.class) {
            return 0;
        }
        if (type == long.class) {
            return 0L;
        }
        return null;
    }
}
