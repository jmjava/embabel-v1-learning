# Repeat Until Acceptable

Some work is a loop: draft, score, revise.

Embabel ships RepeatUntilAcceptableBuilder. You name the return type, set a max
iteration count, and set a score threshold.

The repeating lambda produces a candidate. The evaluator returns TextFeedback
with a score and a comment. If the score is below the threshold, the loop
feeds that feedback into the next attempt.

The cookbook uses a travel itinerary and a deterministic scorer. Lesson 09 uses
a story writer and a reviewer model. Both end as a subprocess on ActionContext,
not as a raw Ai call.
