package com.directmodelling.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import com.directmodelling.collections.Delta.DeltaTracker;

@SuppressWarnings("serial")
public abstract class CollectionRecorder<T, C extends java.util.Collection<T>, D extends Delta>
		extends DeltaTracker<D> implements /* RCollection<T>, */Serializable,
		Collection<T> {
	protected final C delegate;

	/** Create a CollectionRecorder with the given delegate for actual storage. */
	public CollectionRecorder(final C delegate) {
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
	public Iterator<T> iterator() {
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
		for (final T t : delegate) {
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
