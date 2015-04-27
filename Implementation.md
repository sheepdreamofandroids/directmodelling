# A possible implementation to support DM #

Property objects do not hold values. They are keys into hashtables. They do contain convenience methods for setting and retrieving the "current" value.

```
public interface Property<T> {
   T get();
   void set(T newValue);
}
```

What is "current" is defined by external factors like a transaction or indices. So we can write business logic that deals with properties of a car and by changing an index, we change which car we're talking about. This is what happens in a typical master / detail view.

Another example would be a dialog that allows you to change some details about a car rental contract. Instead of making a copy and afterwards copying back depending on whether the user OK'ed or cancelled, you can start a new transaction and then commit or rollback. Not a big deal? What if you're in a multi-user environment where somebody else might have changed something while you're in that dialog? The details would become very hairy quickly. For more info read up on [Software Transactional Memory](http://en.wikipedia.org/wiki/Software_transactional_memory).

Since property objects are real classes, they can implement more interfaces. One extremely useful one would be a HasStatus interface.
```
/** mixin interface for properties */
public interface HasStatus {
   Status getStatus();
}

/** possible statuses for properties */
public enum Status {
   /** your standard run-of-the-mill property that can be stored and retrieved */
   read_write,

   /** this property can only be read from, the get() method is overridden */
   read_only,

   /** read_write, but the current value is invalid with respect to other properties */
   invalid,

   /** given other properties' values, this one is irrelevant */
   irrelevant,

   /** the current value is outdated, wait for an update */
   pending
}
```
In practice, the status enum should be extended to full blown objects to also hold one or more message codes that explains why it has that status.

Also very useful would be a possibility to retrieve the [domain](http://en.wikipedia.org/wiki/Data_domain) of the property. Since this can take several forms, one of several interfaces should be used:
```
/** Simple enumerated values like 'left', 'middle', 'right' */
public interface HasEnumeratedDomain<T> {
   Iterable<T> getEnumeratedDomain();
}

/** Usually numbers within a range, for example 1..10 */
public interface HasIntervalDomain<T extends Comparable> {
   T getMinimum();
   T getMaximum();
}
```