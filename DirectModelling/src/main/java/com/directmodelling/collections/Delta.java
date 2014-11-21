package com.directmodelling.collections;

import java.io.Serializable;

import com.directmodelling.collections.Delta.HasDeltas;

// TODO what about delta's after deserialization?
/**
 * One atomic change on a container that implements {@link HasDeltas}. Produced
 * by {@link CollectionRecorder}s. Used for creating lazy operators on
 * Containers.
 * <P>
 * Deltas contain a link to NEWER deltas. Initially it is null but as soon as
 * the container is changed it will point to the corresponding delta. This means
 * you can track changes by getting the last delta from the container and check
 * it's next field. When it becomes non-null, get that next delta, process it
 * and start monitoring that next field. Ignore the contents of the first Delta
 * since it contains changes that led to the current situation.
 */
public class Delta<T extends HasDeltas<? extends Delta>> {

	public interface HasDeltas<D extends Delta> {
		D getLastDelta();
	}

	@SuppressWarnings("serial")
	public static class DeltaTracker<D extends Delta> implements HasDeltas<D>,
			Serializable {
		// calls private constructor which doesn't try to use lastDelta
		transient D lastDelta = (D) new Delta(this, 0);

		/**
		 * @return the last delta on this collection which resulted in the
		 *         current status. May calculate delta's lazily and therefore,
		 *         as a side effect, change the 'next' field on a previous
		 *         delta. The very first delta on a container is a kind of
		 *         reverse sentinel which may be of a different type. Only use
		 *         it for {@link Delta#getNext()} polling.
		 */
		@Override
		public D getLastDelta() {
			return lastDelta;
		}

	}

	// private final DeltaTracker container;

	public Delta(final T container) {
		// this.container = (DeltaTracker) container;
		// if (this.container.lastDelta != null)
		// this.container.lastDelta.next = this;
		// this.container.lastDelta = this;
		final DeltaTracker c = (DeltaTracker) container;
		if (c.lastDelta != null)
			c.lastDelta.next = this;
		c.lastDelta = this;
	}

	private Delta(final T container, final int dummy) {
		// this.container = (DeltaTracker) container;
	}

	private volatile Delta<T> next = null;

	public Delta<T> getNext() {
		// if (next == null)
		// container.getLastDelta();
		return next;
	}
}
