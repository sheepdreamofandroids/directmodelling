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
import com.directmodelling.demo.angular.shared.RemoteFunction.Impl;
import com.directmodelling.gwt.GWTUpdateTracker;

/** Bits and pieces I don't have a good place for yet. */
public class ClientInitializer extends Initializer {

	@Override
	protected Impl<String, PostcodeLookupResult> postcodeImpl(ID postcodeCacheID) {
		return new RemoteClientImpl<String, PostcodeLookupResult>(
				postcodeCacheID);
	}

	@Override
	protected Tracker updatesTracker() {
		return new GWTUpdateTracker();
	}
}
