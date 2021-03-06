/*******************************************************************************
 * Copyright (c) 2010 Guus C. Bloemsma.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *******************************************************************************/
/**
 * 
 */
package com.directmodelling.impl;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Updates.Tracker;
import com.directmodelling.api.Value;

public abstract class SingleThreadedUpdateTracker implements Tracker {

	private Iterator<Updates.Receiver> iterator;
	private boolean running;

	/**
	 * Run a limited set of updates. At this time 'limited' means '10'. Maybe
	 * this should be 'until 100ms have elapsed' or so.
	 * 
	 * @return whether more updates are needed
	 */
	protected boolean updateSome() {
		if (null == iterator) {
			RECEIVERS.addAll(newReceivers);
			newReceivers.clear();
			iterator = RECEIVERS.iterator();
		}
		boolean hasNext;
		for (int i = 0; (hasNext = iterator.hasNext()) && i < 1000; i++) {
			try {
				iterator.next().valuesChanged();
			} catch (final ConcurrentModificationException cme) {
				iterator = null;// can't keep using this one
				// cme.printStackTrace();
				break;
			} catch (final Throwable e) {
				// TODO Need logging
				e.printStackTrace();
			}
		}
		if (!hasNext) {
			RECEIVERS.removeAll(removedReceivers);
			removedReceivers.clear();
			if (newReceivers.isEmpty()) {
				iterator = null;
				running = false;
			} else {
				RECEIVERS.addAll(newReceivers);
				// do the new receivers first before those that were done in
				// this batch
				iterator = newReceivers.iterator();
				// cannot clear() newReceivers because of the iterator
				newReceivers = new HashSet<Receiver>();
			}
		}
		return running;
	}

	// Can't be static. Weird....
	private final Set<Updates.Receiver> RECEIVERS = new HashSet<Updates.Receiver>();

	// receivers added during update batch
	private Set<Updates.Receiver> newReceivers = new HashSet<Updates.Receiver>();

	// receivers removed during update batch
	private final Set<Updates.Receiver> removedReceivers = new HashSet<Updates.Receiver>();

	// private boolean notifyStarted;

	/** Call this to notify the rest of the system of a changed value */
	@Override
	public void aValueChanged(final Value<?> v) {
		if (!running) {
			running = true;
			schedule();
		}
	}

	protected abstract void schedule();

	/** Register a change listener */
	@Override
	public void registerForChanges(final Updates.Receiver ru) {
		if (running) {
			if (RECEIVERS.contains(ru))
				removedReceivers.remove(ru);
			else
				newReceivers.add(ru);
		} else
			RECEIVERS.add(ru);
	}

	@Override
	public void unregister(final Receiver ru) {
		if (running) {
			if (RECEIVERS.contains(ru))
				removedReceivers.add(ru);
			else
				newReceivers.remove(ru);
		} else
			RECEIVERS.remove(ru);
	}

	@Override
	public void runUpdates() {
		running = true;
		while (updateSome())
			;
	}

}