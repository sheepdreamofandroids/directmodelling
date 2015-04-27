# How DM #

When people describe the behaviour of a wizard or form, they often talk about the fields as if they were entities with their own identity and behaviour. Eg:
  * “The zip-code should have 5 digits.”
  * “The notification date shows an error message.”
  * “For a one-way flight, the return date is not shown.”

This is an indication that the underlying mental model is one where those fields have state and behaviour. In code it would mean that properties get behaviour. Unfortunately, JavaBean conventions does not allow that

Of course you could extend the naming convention to add more possibilities. For example for a property named “prop” you could have methods:
  * getProp()
  * setProp(newValue)
  * visibleProp()
  * isValidProp()
and so on. This would be a valid implementation strategy for adding the kind of behaviour that our mental model seems to have. Unfortunately it doesn’t easily allow reuse of properties.

Another possibility is to use a separate Property object per property. This would have methods like:
  * get()
  * set()
  * isVisible()
  * isValid()
The technical term for this is <a href='http://en.wikipedia.org/wiki/Reification_%28computer_science%29'>reification</a>; representing an abstract concept using something concrete (an object). This makes it very easy to create and reuse something like an email property class. It also makes it very easy to refer to that particular property.

In DM I chose the latter option. I will start a separate page to list all the reasons but one of the most important ones is that I wanted this to work on GWT and there you have no reflection to support a naming convention.