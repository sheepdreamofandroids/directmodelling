package com.directmodelling.demo.angular.shared;

import com.directmodelling.api.ID;
import com.directmodelling.demo.angular.shared.PostcodeLookup.PostcodeLookupResult;
import com.directmodelling.impl.DirectInit;
import com.directmodelling.synchronization.RemoteFunction.Impl;

public abstract class Initializer extends DirectInit {

	public final PostcodeDemo demo;
	public final ID postcodeCacheID;

	{
		postcodeCacheID = ID.generator.it().createID();
		PostcodeLookup.impl.init(postcodeImpl(postcodeCacheID));
		demo = new PostcodeDemo();// must be after PostcodeLookup.impl.init
	}

	protected abstract Impl<String, PostcodeLookupResult> postcodeImpl(
			ID postcodeCacheID);

}
