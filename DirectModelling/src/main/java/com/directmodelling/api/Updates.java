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
package com.directmodelling.api;


/**
 * Handle dependencies. Register to receive notifications whenever any data
 * changes. This means you might get more notifications than you're actually
 * interested in. Handle it.
 */
public abstract class Updates {
	/** IMplemented by receivers of notifications. */
	public interface Receiver {
		/**
		 * One or more values, changed. These are not necessarily ones that you
		 * are interested in.
		 */
		void valuesChanged();
	}

	/** Dependency trackers should implement this. */
	public interface Tracker {
		// @Inject
		// public static final Context<Tracker> it =
		// Context.It.create(Tracker.class);
		/** Called when a {@link Value} changes. */
		void aValueChanged(Value<?> v);

		/** Add a {@link Receiver} to a set that is notified when data changes. */
		void registerForChanges(Receiver ru);

		/** Remove the {@link Receiver} again. */
		void unregister(Receiver ru);

		/** Explicitly update everything that was registered. */
		public void runUpdates();
	}

	// static proxy methods for the current tracker
	public static final void aValueChanged(final Value<?> v) {
		// Tracker.it.it()
		Updates.tracker.aValueChanged(v);
	}

	public static final void registerForChanges(final Receiver ru) {
		// Tracker.it.it()
		Updates.tracker.registerForChanges(ru);
	}

	public static final void unregister(final Receiver ru) {
		Updates.tracker.unregister(ru);
	}

	public static Tracker tracker;

}
