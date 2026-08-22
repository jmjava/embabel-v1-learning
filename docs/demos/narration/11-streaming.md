# Streaming

createObject waits for the whole answer. Streaming emits typed objects as they
arrive.

Check supportsStreaming. Then wrap the PromptRunner in
StreamingPromptRunnerBuilder, give it a prompt, and call createObjectStream
with your record type.

The cookbook asks for three itineraries and collects them with doOnNext. Set a
timeout. blockLast when you want a list; subscribe when you want a UI.

Lesson 20 is StreamingTripPlanner. Fake runners usually cannot stream, so the
planner throws a clear IllegalStateException. That guard is part of the lesson.
