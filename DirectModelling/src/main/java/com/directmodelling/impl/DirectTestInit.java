package com.directmodelling.impl;

import com.directmodelling.api.Updates.Tracker;

public class DirectTestInit extends DirectInit {

	@Override
	protected Tracker updatesTracker() {
		return new ExplicitUpdatesTracker();
	}

}
