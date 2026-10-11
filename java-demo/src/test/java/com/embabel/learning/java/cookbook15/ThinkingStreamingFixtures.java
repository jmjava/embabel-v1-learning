package com.embabel.learning.java.cookbook15;

import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PromptRunner;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.agent.test.unit.FakePromptRunner;
import com.embabel.chat.AssistantMessage;
import com.embabel.chat.Message;
import com.embabel.common.core.thinking.ThinkingResponse;
import com.embabel.learning.common.domain.TripBrief;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Offline runners for the cookbook 1.5 thinking and streaming guided tests.
 * Proxy dispatch lives here so the guided test file stays under the hotspot
 * complexity ceiling.
 */
final class ThinkingStreamingFixtures {

    private ThinkingStreamingFixtures() {
    }

    /**
     * Reports {@code supportsStreaming() == true} and then refuses to stream.
     * Returning success from {@code streamOptions} would mean the guard was swallowed.
     */
    static PromptRunner claimingRunnerThatDoesNotStream() {
        return (PromptRunner) Proxy.newProxyInstance(
                PromptRunner.class.getClassLoader(),
                new Class<?>[]{PromptRunner.class},
                (proxy, method, args) -> dispatchClaimingRunner(proxy, method, args));
    }

    static Ai aiReturning(PromptRunner runner) {
        return (Ai) Proxy.newProxyInstance(
                Ai.class.getClassLoader(),
                new Class<?>[]{Ai.class},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() == Object.class) {
                        return claimingObjectIdentity(proxy, method, args);
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

    /**
     * Ai backed by {@link FakeOperationContext} whose runner reports thinking support
     * so {@link ThinkingTripPlanner#planWithThinking} reaches {@code withSystemPrompt}.
     * The system prompt is stored on the real {@link FakePromptRunner} copy.
     */
    static Ai thinkingCapableAi(
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

    private static Object dispatchClaimingRunner(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return claimingObjectIdentity(proxy, method, args);
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

    private static Object claimingObjectIdentity(Object proxy, Method method, Object[] args) {
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
