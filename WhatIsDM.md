# Direct Modelling #

The axiom of DM is that the code of your model should reflect the [mental model](http://www.nngroup.com/articles/mental-models/) of the user. This may seem obvious or even implied by the word “model”, yet in practice it is often forgotten or simply ignored.

![http://lh4.googleusercontent.com/V9vXWqJvAqf4nW9KKM4peRuPjdZC9uvG0YlDdgNBUIWWReMIsZbSq7fDJcDWyCdFBpblEhIWitxA6o6tz-OSb4MB8HKRUvca9EF-tGOeD6sT0Y2Btg?.png](http://lh4.googleusercontent.com/V9vXWqJvAqf4nW9KKM4peRuPjdZC9uvG0YlDdgNBUIWWReMIsZbSq7fDJcDWyCdFBpblEhIWitxA6o6tz-OSb4MB8HKRUvca9EF-tGOeD6sT0Y2Btg?.png)

Typical models in the java world consist of classes containing private fields with corresponding dumb getters and setters. Usually they are stripped of any and all logic, which is “conveniently” placed in controllers or similar containers of “glue” code. This is not in the spirit of a “model” though. It is not in the spirit of Object Orientation either.

Object Orientation differentiates itself from other methodologies by emphasizing Objects with encapsulated state AND behaviour. A JavaBean that simply stores some fields does not have any interesting behaviour to reuse.

DM defines a standardized API for models. JavaBeans does the same but DM goes further. JavaBeans allows you to access the value of properties in a standardized way. DM also allows standardized access to metadata of properties such as validity, writeability, availability, valid values etc.

## Advantages of DM ##

Assuming that your idea of how the app should work is nearly perfect, this should not change a lot anymore. If it is not, you have no business coding yet! This implies that changes to the model should be small and rare. Therefore it is very useful and practical to write tests for this model.

One or several presentations layered on top of the model might change more often. Given tested, reusable components, they should work correctly as long as the model is correct since the binding is trivial and there is very little glue code needed.

DM will enforce a strict separation of your model and your presentation while providing a rich, technology neutral interface for binding the two together.

In the end, DM should make your code easier to maintain with less bugs.

## Disadvantages of DM ##

DM may be hard to get initially for those who are used to start with the GUI technology. Projects are characterized as a "Spring MVC project" or a "GWT project". The GUI technology defines the project, not the domain.

Given that terminology, it is natural to adapt to the structure that the GUI technology provides: in Spring MVC all logic tends to go into the controller, in GWT all logic goes into the presenter. These are just examples, the same goes for nearly all technologies.

Swing is a bit different: it provides models for all of its components. You can extend these models to create your domain model but that again means mixing swing concerns into your logic. Or you can somehow synchronize the swing models with your own.

When developing using DM for the first time, it can be hard not to be able to interact directly with your GUI. It is also uncanny to have the widgets "pull" data and state out of your model instead of the model "pushing" it to the widgets. It feels like you lost control. In a way it is the same "flip" your mind has to make for dependency injection or functional programming.

## What DM is NOT ##

  * DM is not a new technology that allows you to do anything you couldn’t do before.
  * DM does not allow you to create new software faster while you still have to create all your widgets. Once you have those or you simply use standard widgets, writing the logic becomes a lot easier because there is no interference of GUI concerns.