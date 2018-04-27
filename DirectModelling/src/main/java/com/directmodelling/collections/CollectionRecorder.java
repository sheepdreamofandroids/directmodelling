package com.directmodelling.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import com.directmodelling.collections.HasDeltas.Delta;
import com.directmodelling.collections.HasDeltas.DeltaTracker;


@SuppressWarnings("serial")
/**
 * Base for wrappers of a collection that track all changes that occur through this wrapper.
 * 
 * @author guus
 *
 * @param <T>
 * @param <C>
 * @param <D>
 */
public abstract class CollectionRecorder<Element, Coll extends java.util.Collection<Element>, Del extends Delta<Coll, Del>, Tracker extends CollectionRecorder<Element, Coll, Del, Tracker>>
		extends DeltaTracker<Del, Coll, Tracker>
		implements /* RCollection<T>, */Serializable,
		Collection<Element> {
	protected final Coll delegate;

	/**
	 * Create a CollectionRecorder with the given delegate for actual storage. The delegate can never be modified
	 * directly.
	 */
	public CollectionRecorder(final Coll delegate) {
		assert delegate != null; // fail fast
		this.delegate = delegate;
	}

	@Override
	public boolean contains(final Object arg0) {
		return delegate.contains(arg0);
	}

	@Override
	public boolean containsAll(final java.util.Collection<?> arg0) {
		return delegate.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Iterator<Element> iterator() {
		return delegate.iterator();// TODO intercept remove?
	}

	@Override
	public boolean removeAll(final java.util.Collection<?> arg0) {
		// TODO optimize
		boolean result = false;
		for (final Object object : arg0) {
			result |= remove(object);
		}
		return result;
	}

	@Override
	public boolean retainAll(final java.util.Collection<?> arg0) {
		// TODO optimize
		boolean result = false;
		for (final Element t : delegate) {
			if (!arg0.contains(t))
				result |= remove(t);
		}
		return result;
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public Object[] toArray() {
		return delegate.toArray();
	}

	@Override
	public <S> S[] toArray(final S[] arg0) {
		return delegate.toArray(arg0);
	}

	// @Override
	// public long when() {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// @Override
	// public Iterator<Delta<T>> deltas() {
	// return recorder.deltas();
	// }

}
