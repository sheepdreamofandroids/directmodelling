# Direct Modelling

GUI code is usually tightly bound to a particular GUI toookit or environment. Typical examples are Android, AngularJS or QT. Even when the actual visual layout is separated from the presentation logic, that logic is still littered with toolkit specific artefacts like event handlers, controller interfaces or widget types.

There is simply no way of writing presentation logic that will work in both Angular and QT (even if they were both written in the same language).

In this project I'm experimenting with a way of approaching the problem the other way around: build the logic first and then connect it to a specific toolkit. This also makes GUI testing a breeze.
