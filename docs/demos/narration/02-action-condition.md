# Action Condition

Sometimes two actions want the same input type, but only one should run.

Cookbook recipe: classify a flight as direct or with stops. The classify action
writes two named conditions onto the blackboard: directFlightRequested and
stopsAllowed.

Build-direct-flight lists the first condition in pre. Build-with-stops lists the
second. The planner will not even consider the wrong action.

Conditions should be cheap and side-effect free. The planner may evaluate them
more than once.

Lesson 06 in this repo is the same idea with research drafts: draftSatisfactory
versus draftUnsatisfactory, plus named bindings when two drafts share a type.
