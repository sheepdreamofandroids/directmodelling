package com.directmodelling.demo.angular.shared;

import com.directmodelling.api.ID;
import com.directmodelling.demo.angular.shared.PostcodeLookup.PostcodeLookupResult;
import com.directmodelling.impl.DirectInit;
import com.directmodelling.synchronization.RemoteFunction.Impl;

public abstract class Initializer extends DirectInit {
	@Override
	public void init() {
		PostcodeLookup.impl.init(postcodeImpl(ID.generator.it().createID()));
		demo = new PostcodeDemo();// must be after
		// PostcodeLookup.impl.init
	}

	public PostcodeDemo demo;

	protected abstract Impl<String, PostcodeLookupResult> postcodeImpl(
			ID postcodeCacheID);

}
