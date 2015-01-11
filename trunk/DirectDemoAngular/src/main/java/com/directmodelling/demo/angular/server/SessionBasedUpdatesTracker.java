package com.directmodelling.demo.angular.server;

import com.directmodelling.api.Value;
import com.directmodelling.impl.ExplicitUpdatesTracker;

public class SessionBasedUpdatesTracker extends
		ExplicitUpdatesTracker {
	@Override
	public void aValueChanged(Value<?> v) {
		super.aValueChanged(v);
		runUpdates();
	}
}