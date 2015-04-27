# Why separate presentation logic and business logic #

The states and content of widgets is driven by both business concerns and presentation concerns. DM helps you to separate the two resulting in simpler code.


## Wizard example ##

A typical wizard consists of a list of questions broken into pages. Conventionally each page has to be filled in correctly before the user is allowed to continue to the next page. Often this is presented with a "next" button that is either hidden or disabled until all questions on this page have been answered correctly.

In typical MVC implementations with an anemic domain model, the logic for the next button would be sprinkled throughout event handlers for the widgets that form the domain. Each widget that is invalid would have to disable the "next" button. It gets even harder to know when to enable it again. That can only be done by checking all questions on the page whenever any of them changes.

Checking the model _values_ directly to determine this condition is also not a good idea. That code would know which questions are in which page. This knowledge is already present in the code that built the page in the first place. Also, by checking the _values_, the knowledge of what is valid would be duplicated.

A much nicer approach is to use the list of questions on the current page directly and check them all for validity. When no question is invalid, the user is allowed to go to the next page.

Note that this logic is completely in the presentation layer and does not touch business logic in the model at all. This is good because the model should not care whether it is displayed in a wizard or in one huge page.

The wizard (presentation) logic is simpler because it is completely generic and can be considered and tested without any knowledge of the business logic or domain model. The business logic is simpler because it can be tested without any knowledge of the presentation.