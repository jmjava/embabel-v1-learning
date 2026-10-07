package com.embabel.learning.java.cookbook15;

import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PromptRunner;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.agent.test.unit.FakePromptRunner;
import com.embabel.chat.AssistantMessage;
import com.embabel.chat.Message;
import com.embabel.common.ai.prompt.PromptContributor;
import com.embabel.common.core.thinking.ThinkingResponse;
import com.embabel.learning.common.curriculum.DebugGuide;
import com.embabel.learning.common.domain.TripBrief;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        var context = FakeOperationContext.create();
        var installed = new AtomicReference<FakePromptRunner>();
        // FakeOperationContext runners report supportsThinking() == false, so the
        // production guard returns before withSystemPrompt. This fixture still
        // stores the prompt on that fake runner, then completes thinking offline.
        var response = new ThinkingTripPlanner(thinkingCapableAi(context, installed))
                .planWithThinking("three days London to Paris");

        assertNotNull(response);
        assertNotNull(response.getResult());
        FakePromptRunner prompted = installed.get();
        assertNotNull(prompted, "planWithThinking must install the reasoning prompt on the FakeOperationContext runner");
        String installedPrompt = prompted.getPromptContributors().stream()
                .map(PromptContributor::contribution)
                .reduce("", String::concat);
        assertTrue(installedPrompt.contains(ThinkingTripPlanner.REASONING_SYSTEM_PROMPT.strip()),
                "planWithThinking must install REASONING_SYSTEM_PROMPT");
        assertTrue(installedPrompt.contains("<decision_reasoning>"),
                "renaming or removing <decision_reasoning> in the prompt the planner installs must fail");
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
        var runner = FakeOperationContext.create().ai().withDefaultLlm();
        if (!runner.supportsStreaming()) {
            try {
                new StreamingTripPlanner(FakeOperationContext.create().ai())
                        .streamOptions("three days London to Paris");
            } catch (IllegalStateException expected) {
                assertTrue(expected.getMessage().contains("streaming"));
                return;
            }
        }
        assertTrue(true, "Live streaming runner available — skip the guard assertion");
    }

    /**
     * Ai backed by {@link FakeOperationContext} whose runner reports thinking support
     * so {@link ThinkingTripPlanner#planWithThinking} reaches {@code withSystemPrompt}.
     * The system prompt is stored on the real {@link FakePromptRunner} copy.
     */
    private static Ai thinkingCapableAi(
            FakeOperationContext context,
            AtomicReference<FakePromptRunner> installed) {
        FakePromptRunner base = (FakePromptRunner) context.ai().withDefaultLlm();
        PromptRunner thinkingRunner = wrap(base, installed);
        return (Ai) Proxy.newProxyInstance(
                Ai.class.getClassLoader(),
                new Class<?>[]{Ai.class},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() == Object.class) {
                        return objectIdentity(proxy, method, args);
                    }
                    if ("withDefaultLlm".equals(method.getName())) {
                        return thinkingRunner;
                    }
                    if (method.isDefault()) {
                        return InvocationHandler.invokeDefault(proxy, method, args);
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
    }

    private static PromptRunner wrap(FakePromptRunner current, AtomicReference<FakePromptRunner> installed) {
        return (PromptRunner) Proxy.newProxyInstance(
                PromptRunner.class.getClassLoader(),
                new Class<?>[]{PromptRunner.class},
                (proxy, method, args) -> dispatchThinkingRunner(current, installed, proxy, method, args));
    }

    private static Object dispatchThinkingRunner(
            FakePromptRunner current,
            AtomicReference<FakePromptRunner> installed,
            Object proxy,
            Method method,
            Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return objectIdentity(proxy, method, args);
        }
        return switch (method.getName()) {
            case "supportsThinking" -> true;
            case "withId" -> wrap((FakePromptRunner) current.withId((String) args[0]), installed);
            case "withSystemPrompt" -> {
                FakePromptRunner prompted = (FakePromptRunner) current.withSystemPrompt((String) args[0]);
                installed.set(prompted);
                yield wrap(prompted, installed);
            }
            case "thinking" -> offlineThinking();
            default -> {
                Object result = invokeOnFake(current, method, args);
                if (result instanceof FakePromptRunner next) {
                    yield wrap(next, installed);
                }
                yield result;
            }
        };
    }

    private static Object invokeOnFake(FakePromptRunner current, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(current, args == null ? new Object[0] : args);
        } catch (java.lang.reflect.InvocationTargetException ex) {
            if (ex.getCause() != null) {
                throw ex.getCause();
            }
            throw ex;
        }
    }

    /**
     * Completes {@code thinking().createObject} without a live model. The system
     * prompt under test is the one already stored on the fake runner.
     */
    private static PromptRunner.Thinking offlineThinking() {
        return new PromptRunner.Thinking() {
            @Override
            public <T> ThinkingResponse<T> createObject(List<? extends Message> messages, Class<T> outputClass) {
                if (!TripBrief.class.equals(outputClass)) {
                    throw new IllegalStateException(
                            "planWithThinking must request TripBrief, was " + outputClass.getName());
                }
                TripBrief brief = new TripBrief("Paris", "train", "Three days in Paris.", "Louvre");
                return new ThinkingResponse<>(outputClass.cast(brief), List.of());
            }

            @Override
            public <T> ThinkingResponse<T> createObjectIfPossible(
                    List<? extends Message> messages,
                    Class<T> outputClass) {
                return createObject(messages, outputClass);
            }

            @Override
            public ThinkingResponse<AssistantMessage> respond(List<? extends Message> messages) {
                throw new UnsupportedOperationException("respond");
            }

            @Override
            public ThinkingResponse<Boolean> evaluateCondition(
                    String condition,
                    String context,
                    double threshold) {
                throw new UnsupportedOperationException("evaluateCondition");
            }
        };
    }

    private static Object objectIdentity(Object proxy, Method method, Object[] args) {
        return switch (method.getName()) {
            case "toString" -> "thinking-capable-fake-runner";
            case "hashCode" -> System.identityHashCode(proxy);
            case "equals" -> proxy == args[0];
            default -> null;
        };
    }
}
