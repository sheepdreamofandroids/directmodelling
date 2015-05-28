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

import com.directmodelling.api.ID;
import com.directmodelling.api.Updates.Tracker;
import com.directmodelling.demo.angular.shared.Initializer;
import com.directmodelling.demo.angular.shared.PostcodeLookup.PostcodeLookupResult;
import com.directmodelling.gwt.GWTUpdateTracker;
import com.directmodelling.gwt.sync.Synchronizer;
import com.directmodelling.synchronization.RemoteClientImpl;
import com.directmodelling.synchronization.RemoteFunction;
import com.directmodelling.synchronization.RemoteFunction.Impl;
import com.directmodelling.synchronization.RemoteFunction.Impl.AsyncFunction;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/** Bits and pieces I don't have a good place for yet. */
public class ClientInitializer extends Initializer {

	private MySyncServiceAsync greetingService;

	public ClientInitializer(MySyncServiceAsync greetingService) {
		this.greetingService = greetingService;
		// init();
	}

	@Override
	protected Impl<String, PostcodeLookupResult> postcodeImpl(ID postcodeCacheID) {
		Synchronizer.it.init(new Synchronizer(baseValues, changes,
				greetingService));
		return new RemoteClientImpl<String, PostcodeLookupResult>(
				postcodeCacheID,
				Suppliers.memoize(//
						new Supplier<RemoteFunction.Impl.AsyncFunction<String, PostcodeLookupResult>>() {

							@Override
							public AsyncFunction<String, PostcodeLookupResult> get() {
								return Synchronizer.it
										.it()
										.<String, PostcodeLookupResult> asFunction();
							}
						}));
	}

	@Override
	protected Tracker updatesTracker() {
		return new GWTUpdateTracker();
	}
}
