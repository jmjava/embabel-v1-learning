# Thinking

Some models can return a rationale plus a result.

In Embabel 1.5, ask the PromptRunner if it supportsThinking. If yes, call
thinking(), then createObject. You get a ThinkingResponse: the typed result,
the thinking content, and an optional exception.

The cookbook asks the model to put bullets inside decision_reasoning tags:
time, risk, trade-offs. A half-day New York to Sydney trip should fail with a
ThinkingException that says the request is impossible.

Lesson 19 is ThinkingTripPlanner. Guided tests stay offline; the live shell is
where you watch the rationale stream in.
