# Tool Call

Give the model tools, then let it call them before it writes the object.

Mark methods with @LlmTool. Pass the object to withToolObject. Add
ToolCallLoggingInspector if you want each call in the logs.

The cookbook travel tooling returns weather and attractions. The PromptRunner
creates a TravelPlan that should mention both.

Lesson 07 already teaches tool groups. Lesson 21 adds the 1.5 inspector path
and keeps the lookup tools deterministic so the guided test does not need a
network. When you run the shell, watch the inspector lines before the final
TripBrief appears.
