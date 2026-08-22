# Action Domain Type Chaining

Embabel does not run actions in source order. It plans from types.

A travel agent first classifies user input. The result is not a string. It is a
domain type: a flight request or an itinerary request.

The planner then looks for an action that consumes that type. Find-flight only
accepts a flight request. Build-itinerary only accepts an itinerary request.

Both paths produce a travel activity. The summarize action consumes that common
type and achieves the goal.

That is domain type chaining. Change the input, change the type, and the plan
rewrites itself. Lesson 16 in this repo is the teaching counterpart.
