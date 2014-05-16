package com.directmodelling.impl;

//TODO move to api

import com.directmodelling.api.Value;

import java.util.List;

/**
 * Describes a finite set of values that is completely known at runtime
 */
public interface EnumerableDomain<T> {
    /**
     * I don't like this name too much but it's the most descriptive I can come
     * up with right now. <br>
     * TODO This list can change over time. Should it be a ListVar? How about
     * enable/disabled per item?
     *
     * @return all values that this {@link Value} can have in their natural
     * ImmutableList<T>O NOT MODIFY!
     * @see EnumVar
     */
    List<T> allPotentialValues();

}