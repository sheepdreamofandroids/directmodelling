package com.directmodelling.synchronization;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import com.directmodelling.api.ID;
import com.directmodelling.api.Updates;
import com.directmodelling.stm.Storage;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;

public class RemoteClientImpl<I, O> implements RemoteFunction.Impl<I, O> {

	private final ID cacheId;
	private Supplier<com.directmodelling.synchronization.RemoteFunction.Impl.AsyncFunction<I, O>> f;

	public RemoteClientImpl(final ID cacheId,
			Supplier<RemoteFunction.Impl.AsyncFunction<I, O>> f) {
		this.cacheId = cacheId;
		this.f = f;
	}

	@Override
	public void init(final RemoteFunction<I, O> f) {

	}
	
	@Inject Provider<Storage> storage;

	@Override
	public Optional<O> apply(final RemoteFunction<I, O> requester) {
		final I argument = requester.argument();
		final Storage storage = this.storage.get();
		@SuppressWarnings("unchecked")
		final Map<I, Object> theCache = (Map<I, Object>) storage.get(cacheId);
		final Object p = theCache.get(argument);
		if (p == null) {
			theCache.put(argument, IsCalculating.onClient);
			f.get().apply(requester, argument,
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
