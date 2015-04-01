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

import java.util.HashSet;
import java.util.Set;

import com.directmodelling.api.Context;
import com.directmodelling.api.ID;
import com.directmodelling.api.Updates;
import com.directmodelling.impl.Command;
import com.directmodelling.impl.SingleAssignContext;
import com.directmodelling.stm.Version;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.synchronization.RemoteFunction;
import com.directmodelling.synchronization.RemoteFunction.Impl.AsyncFunction;
import com.directmodelling.synchronization.RemoteFunction.Impl.ResultCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/** Responsible for synchronizing model data. */
public class Synchronizer {
	public static final SingleAssignContext<Synchronizer> it = Context.SESSION
			.it().create();

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private Version baseValues;
	private TransactionImpl changes;

	public Synchronizer(Version baseValues, TransactionImpl changes) {
		this.baseValues = baseValues;
		this.changes = changes;
		;
	}

	private final Command commit = new Command() {
		@Override
		public void run() {
			sync();
		}
	};

	private boolean syncing = false;
	private final Set<ID> requestedValues = new HashSet<ID>();
	private final Set<ID> triggeredCommands = new HashSet<ID>();

	public void requestValue(final RemoteFunction<?, ?> request) {
		requestedValues.add(request.id());
		sync();
	}

	public void sync() {
		if (!syncing) {
			syncing = true;
			new Timer() {
				@Override
				public void run() {
					final TransactionImpl t = new TransactionImpl();
					changes.moveTo(t);
					changes.reset();
					greetingService.update(t, requestedValues,
							new AsyncCallback<TransactionImpl>() {

								@Override
								public void onFailure(final Throwable e) {
									syncing = false;
									// show error?
									GWT.log("", e);
									sync(); // try again
									// TODO limit number of tries?
								}

								@Override
								public void onSuccess(
										final TransactionImpl serverChanges) {
									syncing = false;
									GWT.log("Committing " + changes);
									t.commitTo(baseValues);
									serverChanges.commitTo(baseValues);
									if (!changes.getWrites().isEmpty())
										sync();
								}
							});
					requestedValues.clear();
				}
			}.schedule(100);// small delay to coalesce requests
		}
	}

	public void poll() {
		greetingService.longPoll(new AsyncCallback<TransactionImpl>() {

			@Override
			public void onFailure(Throwable caught) {
				poll();
				GWT.log("Polling failed", caught);
			}

			@Override
			public void onSuccess(TransactionImpl result) {
				poll();
				GWT.log("Polled " + result);
				// TODO check compatibility of result with changes
				result.commitTo(changes);
				// changes.reset();
				apply();
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

	public native void apply() /*-{
		$wnd.angularScope.$apply();
	}-*/;

	private final AsyncFunction<Object, Object> asyncFunction = new AsyncFunction<Object, Object>() {

		@Override
		public void apply(RemoteFunction<Object, Object> requester, Object i,
				ResultCallback<Object, Object> callback) {
			requestValue(requester);
		}

	};

	public <I, O> AsyncFunction<I, O> asFunction() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final AsyncFunction<I, O> result = (AsyncFunction) asyncFunction;
		return result;
	}
}
