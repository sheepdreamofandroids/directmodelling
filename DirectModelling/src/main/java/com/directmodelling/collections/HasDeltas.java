package com.directmodelling.collections;

import java.io.Serializable;

// TODO what about delta's after deserialization?
public interface HasDeltas<Del extends HasDeltas.IDelta<Collection>, Collection> {
	Del getLastDelta();

	/**
	 * One atomic change on a container that implements {@link HasDeltas}. Produced by {@link CollectionRecorder}s. Used
	 * for creating lazy operators on Containers.
	 * <P>
	 * Deltas contain a link to NEWER deltas. Initially it is null but as soon as the container is changed it will point
	 * to the corresponding delta. This means you can track changes by getting the last delta from the container and
	 * check it's next field. When it becomes non-null, get that next delta, process it and start monitoring that next
	 * field. Ignore the contents of the first Delta since it contains changes that led to the current situation.
	 */
	public interface IDelta<Collection> {
		public IDelta<Collection> getNext();
	}

	public static abstract class Delta<Collection, SELF extends Delta<Collection, SELF>>
			implements IDelta<Collection> {

		private final DeltaTracker<SELF, ?, ?> tracker;

		/** This constructor will add this delta into the chain */
		public Delta(final DeltaTracker<SELF, ?, ?> tracker) {
			this.tracker = tracker;
			// if (this.container.lastDelta != null)
			// this.container.lastDelta.next = this;
			// this.container.lastDelta = this;
			@SuppressWarnings({ "unchecked" })
			final SELF self = (SELF) this;
			tracker.add(self);
			// SELF lastDelta = tracker.getLastDelta();
			// if (lastDelta != null)
			// ((Delta) tracker.lastDelta).next = this;
			// ((DeltaTracker) tracker).lastDelta = this;
		}

		volatile SELF next = null;

		@Override
		public SELF getNext() {
			if (next == null)
				tracker.getLastDelta();
			return next;
		}

		public void setNext(SELF next) {
			this.next = next;
		}

		/** Apply the delta to the collection */
		protected abstract void apply(Collection c);
	}

	@SuppressWarnings("serial")
	public static abstract class DeltaTracker<Del extends Delta<Collection, Del>, Collection, SELF extends DeltaTracker<Del, Collection, SELF>>
			implements HasDeltas<Del, Collection>, Serializable {

		transient Del lastDelta = createSentinel();

		/**
		 * @return the last delta on this collection which resulted in the current status. May calculate delta's lazily
		 *         and therefore, as a side effect, change the 'next' field on a previous delta. The very first delta on
		 *         a container is a kind of reverse sentinel which may be of a different type. Only use it for
		 *         {@link Delta#getNext()} polling.
		 */
		@Override
		public Del getLastDelta() {
			return lastDelta;
		}

		void add(Del newest) {
			if (lastDelta != null)
				lastDelta.setNext(newest);
			lastDelta = newest;
		}

		protected abstract Del createSentinel();

	}

}
