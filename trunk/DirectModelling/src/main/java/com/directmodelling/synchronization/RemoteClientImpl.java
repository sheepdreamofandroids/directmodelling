package com.directmodelling.synchronization;

import java.util.Map;

import com.directmodelling.api.ID;
import com.directmodelling.api.Updates;
import com.directmodelling.stm.Storage;
import com.google.common.base.Optional;

public class RemoteClientImpl<I, O> implements RemoteFunction.Impl<I, O> {

	private final ID cacheId;
	private RemoteFunction.Impl.AsyncFunction<I, O> f;

	public RemoteClientImpl(final ID cacheId,
			RemoteFunction.Impl.AsyncFunction<I, O> f) {
		this.cacheId = cacheId;
		this.f = f;
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
			f.apply(requester, argument,
					new RemoteFunction.Impl.ResultCallback<I, O>() {
						@Override
						public void result(I i, O o) {
							theCache.put(i, o);
							Updates.aValueChanged(requester);
						}
					});
			return Optional.absent();
		} else if (p instanceof IsCalculating)
			return Optional.absent();
		else {
			@SuppressWarnings("unchecked")
			O p2 = (O) p;
			return Optional.of(p2);
		}
	}
}
