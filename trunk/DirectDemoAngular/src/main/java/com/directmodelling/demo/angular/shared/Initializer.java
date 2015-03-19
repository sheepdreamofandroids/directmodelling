package com.directmodelling.demo.angular.shared;

import com.directmodelling.api.ID;
import com.directmodelling.demo.angular.shared.PostcodeLookup.PostcodeLookupResult;
import com.directmodelling.impl.DirectInit;
import com.directmodelling.synchronization.RemoteFunction.Impl;

public abstract class Initializer extends DirectInit {

	{
		PostcodeLookup.impl.init(postcodeImpl(ID.generator.it().createID()));
	}
	public final PostcodeDemo demo = new PostcodeDemo();// must be after
														// PostcodeLookup.impl.init

	protected abstract Impl<String, PostcodeLookupResult> postcodeImpl(
			ID postcodeCacheID);

}
