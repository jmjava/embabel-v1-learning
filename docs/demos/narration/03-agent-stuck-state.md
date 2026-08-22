# Agent Stuck State

A process is stuck when no remaining action is eligible and the goal is unmet.

The cookbook travel agent classifies input as a travel inquiry or a non-travel
inquiry. Only a travel inquiry has a follow-up action. Ask about a dog, and the
process stops with status STUCK after classify.

That is not a crash. It is a first-class status code. You can attach a
StuckHandler, ask the user a follow-up, or fail closed.

Lesson 13 walks the recovery path. For videos, remember to dump the blackboard
and the action history. Those two views explain why the planner gave up.
