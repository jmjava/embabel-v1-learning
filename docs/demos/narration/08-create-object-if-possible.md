# Create Object If Possible

createObject always wants an instance. If the prompt is thin, the model will
guess.

createObjectIfPossible is the 1.5 nullable path. Give it a destination and
dates, and you get an ItineraryRequest. Say "plan a trip" with no details, and
you get null.

That null is the feature. Your app can ask a follow-up instead of shipping a
hallucinated plan.

Lesson 18 wraps the call in PossibleTripPlanner and keeps a deterministic
sufficiency check for the guided test. Use the API for live models; use the
check to explain the rule in videos.
