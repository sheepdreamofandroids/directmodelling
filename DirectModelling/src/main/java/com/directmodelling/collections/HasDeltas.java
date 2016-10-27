package com.directmodelling.collections;

import java.io.Serializable;

// TODO what about delta's after deserialization?
public interface HasDeltas<D extends HasDeltas.Delta<Container>, Container> {
	D getLastDelta();

	/**
	 * One atomic change on a container that implements {@link HasDeltas}. Produced by {@link CollectionRecorder}s. Used
	 * for creating lazy operators on Containers.
	 * <P>
	 * Deltas contain a link to NEWER deltas. Initially it is null but as soon as the container is changed it will point
	 * to the corresponding delta. This means you can track changes by getting the last delta from the container and
	 * check it's next field. When it becomes non-null, get that next delta, process it and start monitoring that next
	 * field. Ignore the contents of the first Delta since it contains changes that led to the current situation.
	 */

	@SuppressWarnings("serial")
	public static abstract class DeltaTracker<D extends Delta<Container>, Container>
			implements HasDeltas<D, Container>, Serializable {

		transient D lastDelta = createSentinel();

		/**
		 * @return the last delta on this collection which resulted in the current status. May calculate delta's lazily
		 *         and therefore, as a side effect, change the 'next' field on a previous delta. The very first delta on
		 *         a container is a kind of reverse sentinel which may be of a different type. Only use it for
		 *         {@link Delta#getNext()} polling.
		 */
		@Override
		public D getLastDelta() {
			return lastDelta;
		}

		protected abstract D createSentinel();

	}

	public abstract class Delta<Container> {

		private final DeltaTracker<Delta<Container>, Container> container;

		/** This constructor will add this delta into the chain */
		public Delta(final DeltaTracker<Delta<Container>, Container> container) {
			this.container = container;
			// if (this.container.lastDelta != null)
			// this.container.lastDelta.next = this;
			// this.container.lastDelta = this;
			if (container.lastDelta != null)
				container.lastDelta.next = this;
			container.lastDelta = this;
		}

		volatile Delta<Container> next = null;

		public Delta<Container> getNext() {
			if (next == null)
				container.getLastDelta();
			return next;
		}

		/** Apply the delta to the collection */
		abstract void apply(Container c);
	}

}
