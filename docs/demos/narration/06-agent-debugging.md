# Agent Debugging

When a plan surprises you, look at three things.

First, action history: which methods actually ran, in which order.

Second, the blackboard: which types and named bindings are present.

Third, process status: COMPLETED, STUCK, WAITING, or FAILED.

This repo adds a fourth view: @DebugGuide on guided tests. Each test lists
breakpoints and watch expressions so you can step the FakeOperationContext
path without a live model.

Official cookbook tests log history and blackboard.infoString. Do the same in
your own recipes. Planning bugs are almost always a missing type or a false
condition.
