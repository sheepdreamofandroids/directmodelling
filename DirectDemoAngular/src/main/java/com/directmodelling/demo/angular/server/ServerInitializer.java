package com.directmodelling.demo.angular.server;

import com.directmodelling.api.Context;
import com.directmodelling.api.Context.Factory;
import com.directmodelling.api.ID;
import com.directmodelling.api.Updates.Tracker;
import com.directmodelling.demo.angular.shared.Initializer;
import com.directmodelling.demo.angular.shared.PostcodeLookup.PostcodeLookupResult;
import com.directmodelling.server.SessionBasedUpdatesTracker;
import com.directmodelling.synchronization.RemoteServerImpl;
import com.directmodelling.synchronization.RemoteFunction.Impl;

public class ServerInitializer extends Initializer {

	@Override
	protected Impl<String, PostcodeLookupResult> postcodeImpl(ID postcodeCacheID) {
		return new RemoteServerImpl(postcodeCacheID,
				new PostcodeLookupServerImpl());
	}

	@Override
	protected Factory sessionContext() {
		return Context.Factory.Default.INSTANCE;
		// return new SessionContextFactory();
	}

	@Override
	protected Tracker updatesTracker() {
		return new SessionBasedUpdatesTracker();
	}
}
