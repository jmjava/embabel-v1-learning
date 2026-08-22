# Object Creation

You do not need an @Agent to use Embabel.

Inject Ai. Call withLlm or withDefaultLlm. Then creating(YourRecord.class),
optionally add withExample and withValidation, and finish with fromPrompt.

The model must return JSON that matches the record. Bean Validation on fields
rejects bad shapes.

Lesson 01 invents a MagicalAnimal whose species must contain the letters o x.
The cookbook builds a trip summary the same way. This is the 1.5 object
creation recipe, and it is the fastest way to add typed AI to a Spring app.
