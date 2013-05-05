package com.directmodelling.impl;

import java.util.List;

import com.directmodelling.api.Value;

public interface EnumerableDomain<T> {
	/**
	 * I don't like this name too much but it's the most descriptive I can come
	 * up with right now.
	 * 
	 * @return a list containing all values that this {@link Value} can have in
	 *         their natural order. DO NOT MODIFY!
	 * @see EnumVar
	 */
	List<T> allPotentialValues();

}