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
package com.directmodelling.demo.angular.client;

import com.directmodelling.api.Context;
import com.directmodelling.api.Context.Factory;
import com.directmodelling.api.Updates;
import com.directmodelling.gwt.GWTContext;
import com.directmodelling.gwt.GWTUpdateTracker;
import com.directmodelling.impl.Command;
import com.directmodelling.impl.SingleAssignContext;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Storage.Util;
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
		Util.current = new GWTContext<Storage>(baseValues);
		Updates.tracker = new GWTUpdateTracker();
		Context.perUser.init(new Factory() {
			@Override
			public <T> Context<T> create(final T initialValue) {
				return new SingleAssignContext<T>();
			}
		});
		it = Context.perUser.it().create(this);
	}
	final TransactionImpl changes = new TransactionImpl(baseValues);

	private final Command commit = new Command() {
		@Override
		public void run() {
			sync();
		}
	};

	private void sync() {
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
		Util.current = new GWTContext<Storage>(changes);
		Updates.tracker.runUpdates();
	}

	public void initializeValues(final VersionImpl storage) {
		baseValues.initializeValues(storage);
	}

	public static Context<System> it;
}
