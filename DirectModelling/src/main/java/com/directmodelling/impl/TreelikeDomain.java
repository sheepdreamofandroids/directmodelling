package com.directmodelling.impl;

//TODO move to api

import com.directmodelling.api.Value;
import com.google.common.collect.ImmutableList;

/**
 * Describes a finite set of values in a tree-like form that is completely known
 * at runtime.
 */
public interface TreelikeDomain<T> {
	// TODO Come up with a better name?
	/**
	 * All branches under the current node.
	 * 
	 * @return all values that this {@link Value} can have in their natural
	 *         order. DO NOT MODIFY!
	 * @see EnumVar
	 */
	ImmutableList<T> branches();

	/** Why should there be only one root? */
	ImmutableList<T> roots();

	/** In case you need to render the whole hierarchy. */
	T parentOf(T t);

	/** In case you need to render the whole hierarchy. */
	ImmutableList<T> branchesOf(T t);
}