# Action Heuristics

When two actions achieve the same goal, GOAP needs a tie-break.

Embabel uses action cost. In the cookbook recipe, the cheapest recommendation
has cost 0.1. The premium recommendation has cost 0.9. Same input type, same
goal type. The planner picks the cheaper path.

Cost is a heuristic, not a dollar invoice. Use it for preferred defaults,
fallback quality, or expensive tool calls you want to avoid.

Lesson 17 is a teaching agent with those two actions and no LLM. Step the
guided test, then read the @Action cost attributes.
