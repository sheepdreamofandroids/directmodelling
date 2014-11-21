package com.directmodelling.collections.operators;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.directmodelling.collections.List.ListReplace;

/**
 * Simple baseclass for implementing immutable
 * {@link com.directmodelling.collections.List}s. It throws an
 * {@link UnsupportedOperationException} for each method that tries to modify
 * it. Its contents can actually change, just not by calling mutators on this
 * instance.
 */
@SuppressWarnings("serial")
public abstract class AbstractImmutableList<S> extends
		com.directmodelling.collections.Delta.DeltaTracker<ListReplace<S>>
		implements List<S>, Serializable {

	@Override
	public void add(final int index, final S element) {
		throw immutable();
	}

	@Override
	public boolean add(final S e) {
		throw immutable();
	}

	@Override
	public boolean addAll(final Collection<? extends S> c) {
		throw immutable();
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends S> c) {
		throw immutable();
	}

	@Override
	public void clear() {
		throw immutable();
	}

	/**
	 * @return
	 */
	protected UnsupportedOperationException immutable() {
		return new UnsupportedOperationException(
				"Result of appending lists is immutable.");
	}

	@Override
	public S remove(final int index) {
		throw immutable();
	}

	@Override
	public boolean remove(final Object o) {
		throw immutable();
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		throw immutable();
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		throw immutable();
	}

	@Override
	public S set(final int index, final S element) {
		throw immutable();
	}

}
