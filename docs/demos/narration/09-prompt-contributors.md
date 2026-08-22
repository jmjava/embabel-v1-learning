# Prompt Contributors

fromPrompt is one string. fromMessages is a conversation.

The cookbook builds a travel plan from a system message plus two user
messages. The PromptRunner still ends at creating(TravelPlan.class). The extra
messages are prompt contributors: they shape tone and constraints without
stuffing everything into one template.

Personas and RoleGoalBackstory work the same way on write-and-review agents.
Lesson 02 already uses them. Lesson 21 shows the 1.5 fromMessages form next to
tool inspectors so you can see both Part 2 recipes in one class.
