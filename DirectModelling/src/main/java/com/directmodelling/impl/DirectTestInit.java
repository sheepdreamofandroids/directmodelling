package com.directmodelling.impl;

import com.directmodelling.api.Updates.Tracker;

import dagger.Module;
import dagger.Provides;

@Module
public class DirectTestInit extends DirectInit {

	@Override
	protected Tracker updatesTracker() {
		return new ExplicitUpdatesTracker();
	}

}
