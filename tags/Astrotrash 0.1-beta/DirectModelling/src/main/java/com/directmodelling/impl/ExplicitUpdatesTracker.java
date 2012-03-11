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
package com.directmodelling.impl;

import java.util.HashSet;
import java.util.Set;

import com.directmodelling.api.Value;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Updates.Tracker;


/**
 * Very simple implementation of {@link Tracker}. Does not work for either Swing
 * or GWT! Essentially it is only useful for unit tests where you call
 * {@link ExplicitUpdatesTracker#runUpdates()} to explicitly update everything.
 * 
 */
public class ExplicitUpdatesTracker implements Tracker {
	private final Set<Receiver> receivers = new HashSet<Receiver>();
	private boolean somethingChanged;

	@Override
	public void aValueChanged(Value<?> v) {
		somethingChanged = true;
	}

	@Override
	public void registerForChanges(Receiver ru) {
		receivers.add(ru);
	}

	@Override
	public void unregister(Receiver ru) {
		receivers.remove(ru);
	}

	/** Explicitly update everything that was registered. */
	public void runUpdates() {
		if (somethingChanged)
			for (Receiver receiver : receivers) {
				receiver.valuesChanged();
			}
	}

}
