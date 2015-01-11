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
package com.directmodelling.demo.gwt.client;

import com.directmodelling.api.ID;
import com.directmodelling.api.Updates;
import com.directmodelling.gwt.GWTUpdateTracker;
import com.directmodelling.impl.Command;
import com.directmodelling.impl.SimpleIDGenerator;
import com.directmodelling.stm.Storage.Util;
import com.directmodelling.stm.Version;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.stm.impl.VersionImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/** Bits and pieces I don't have a good place for yet. */
public class System {
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	final VersionImpl baseValues = new VersionImpl();// .storage;
	{
		Util.current.init(baseValues);
		Updates.tracker = new GWTUpdateTracker();
		ID.generator.init(new SimpleIDGenerator());
	}
	final TransactionImpl changes = new TransactionImpl(baseValues);

	private final Command commit = new Command() {
		@Override
		public void run() {
			greetingService.update(changes, new AsyncCallback<Void>() {

				@Override
				public void onFailure(final Throwable e) {
					// show error?
					changes.reset();
					GWT.log("", e);
				}

				@Override
				public void onSuccess(final Void arg0) {
					GWT.log("Committing " + changes);
					changes.commitTo(baseValues);
					changes.reset();
				}
			});
		}
	};

	public Command commit() {
		return commit;
	}

	private final Command restore = new Command() {
		@Override
		public void run() {
			changes.reset();
			Updates.tracker.runUpdates();
		}
	};

	public Command restore() {
		return restore;
	}

	public void doneInitializing() {
		Util.current.init(changes);
		Updates.tracker.runUpdates();
	}

	public void initializeValues(final Version storage) {
		baseValues.initializeValues(storage);
	}

}
