package com.directmodelling.collections.operators;

import java.io.Serializable;
import java.util.Collection;

import com.directmodelling.collections.HasDeltas.Delta;
import com.directmodelling.collections.HasDeltas.DeltaTracker;

/**
 * Simple baseclass for implementing readonly {@link com.directmodelling.collections.List}s. It throws an
 * {@link UnsupportedOperationException} for each method that tries to modify it. Its contents can actually change, just
 * not by calling mutators on this instance.
 */
@SuppressWarnings("serial")
public abstract class AbstractReadonlyCollection<Element, Tracker extends AbstractReadonlyCollection<Element, Tracker, Coll, Deltah>, Coll, Deltah extends Delta<Coll, Deltah>>
		extends DeltaTracker<Deltah, Coll, Tracker> implements Collection<Element>, Serializable {

	@Override
	public boolean add(final Element e) {
		throw immutable();
	}

	@Override
	public boolean addAll(final Collection<? extends Element> c) {
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
		return new UnsupportedOperationException("Result of appending lists is immutable.");
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

}
