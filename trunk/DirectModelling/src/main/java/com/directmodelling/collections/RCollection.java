package com.directmodelling.collections;

import com.directmodelling.collections.Delta.DeltaTracker;

public interface RCollection<S> {
	public DeltaTracker.Delta<Delta> getLastDelta();

	// long when();

	/** last delta first */
	// Iterator<RCollection.Delta<S>> deltas();

	// final class DeltaRecorder<T> {
	// final Stack<Delta<T>> q = new Stack<RCollection.Delta<T>>();
	//
	// public void add(Delta<T> d) {
	// q.add(d);
	// }
	//
	// public Iterator<Delta<T>> deltas() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// public Delta<T> lastDelta() {
	// return q.lastElement();
	// }
	// }

}