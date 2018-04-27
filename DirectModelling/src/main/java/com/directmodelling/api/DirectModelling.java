package com.directmodelling.api;

import com.directmodelling.impl.ExplicitUpdatesTracker;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.impl.VersionImpl;

import dagger.Module;
@Module
public final class DirectModelling {
	public static void init(final Storage storage,
			final Updates.Tracker updatesTracker) {
		Storage.current.init(storage);
		Updates.tracker = updatesTracker;
	}

	public static void initForTests() {
		init(new VersionImpl(), new ExplicitUpdatesTracker());
	}

	public static void init() {
		if (Storage.current == null)
			initForTests();
	}
}
