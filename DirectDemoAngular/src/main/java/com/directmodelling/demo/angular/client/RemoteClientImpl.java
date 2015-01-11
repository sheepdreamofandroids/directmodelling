package com.directmodelling.demo.angular.client;

import java.util.Map;

import com.directmodelling.api.ID;
import com.directmodelling.demo.angular.shared.IsCalculating;
import com.directmodelling.demo.angular.shared.RemoteFunction;
import com.directmodelling.demo.angular.shared.RemoteFunction.Impl;
import com.directmodelling.stm.Storage;
import com.google.common.base.Optional;

public class RemoteClientImpl<I, O> implements Impl<I, O> {

	private final ID cacheId;

	// public static final String GOCALC =
	// "Any string that will never occur by chance in GWT RPC in this millennium. DO (*#$PIUH O*Y(*Y# P(*U FEPHW GKJB B MND CBHG YGIH KJWHEio230948 lkdfj 028y lwnf 028jhf wdjf ije i wfnl skejhg iushdf gksjd fu er";

	public RemoteClientImpl(final ID cacheId) {
		this.cacheId = cacheId;
	}

	@Override
	public void init(final RemoteFunction<I, O> f) {

	}

	@Override
	public Optional<O> apply(final RemoteFunction<I, O> requester) {
		final I argument = requester.argument();
		final Storage storage = Storage.Util.current.it();
		@SuppressWarnings("unchecked")
		final Map<I, Object> theCache = (Map<I, Object>) storage.get(cacheId);
		final Object p = theCache.get(argument);
		if (p == null) {
			theCache.put(argument, IsCalculating.onClient);
			Synchronizer.it.it().requestValue(requester);
			return Optional.absent();
		} else if (p == IsCalculating.onClient)
			return Optional.absent();
		else {
			@SuppressWarnings("unchecked")
			O p2 = (O) p;
			return Optional.of(p2);
		}
	}
}
