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
	 * Trigger a limited set of updates. At this time 'limited' means '10'.
	 * Maybe this should be 'until 100ms have elapsed' or so.
	 * 
	 * @return whether more updates are needed
	 */
	protected boolean updateSome() {
		if (null == iterator) {
			iterator = RECEIVERS.iterator();
		}
		boolean hasNext;
		for (int i = 0; (hasNext = iterator.hasNext()) && i < 10; i++) {
			try {
				iterator.next().valuesChanged();
			} catch (final ConcurrentModificationException cme) {
				iterator = null;// can't keep using this one
				cme.printStackTrace();
				break;
			} catch (final Throwable e) {
				// TODO Need logging
				e.printStackTrace();
			}
		}
		if (!hasNext) {
			iterator = null;
			running = false;
		}
		return hasNext;
	}

	// Can't be static. Weird....
	private final Set<Updates.Receiver> RECEIVERS = new HashSet<Updates.Receiver>();

	// private boolean notifyStarted;

	/** Call this to notify the rest of the system of a changed value */
	public void aValueChanged(final Value<?> v) {
		if (!running) {
			running = true;
			schedule();
		}
	}

	protected abstract void schedule();

	/** Register a change listener */
	public void registerForChanges(final Updates.Receiver ru) {
		RECEIVERS.add(ru);
	}

	@Override
	public void unregister(final Receiver ru) {
		RECEIVERS.remove(ru);
	}

	public void runUpdates() {
	}

}