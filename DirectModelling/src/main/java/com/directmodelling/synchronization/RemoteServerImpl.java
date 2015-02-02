package com.directmodelling.synchronization;

import java.util.HashMap;
import java.util.Map;

import com.directmodelling.api.Context;
import com.directmodelling.api.ID;
import com.directmodelling.api.Identifiable;
import com.directmodelling.api.Updates;
import com.directmodelling.stm.Storage;
import com.directmodelling.synchronization.RemoteFunction.Impl;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;

/** One instance per type of service. The synchronized cache is per session. */
public class RemoteServerImpl<I, O> implements Impl<I, O>,
		Impl.ResultCallback<I, O>, Identifiable {

	@Override
	public void init(final RemoteFunction<I, O> f) {
		remotes.it().put(f.id(), f);
	}

	private final ID id;// = ID.generator.it().createID();
	private Impl.AsyncFunction<I, O> f;

	public RemoteServerImpl(ID id, Impl.AsyncFunction<I, O> f) {
		this.id = id;
		this.f = f;
		Storage.Util.current.it().set(id, new HashMap<I, Object>());
	}

	@Override
	public ID id() {
		return id;
	}

	// private final Map<I, Object> cache = new HashMap<I, Object>();
	// {
	// Storage.Util.current.it().set(id, new HashMap<I, Object>());
	// }

	@Override
	public Optional<O> apply(final RemoteFunction<I, O> requester) {
		final I argument = requester.argument();
		final Map<I, Object> cache = Storage.Util.current.it().get(id);
		Object cached = cache.get(argument);
		// avoid double calculations
		if (cached == null || cached == IsCalculating.onClient) {
			cache.put(argument, IsCalculating.onServer);
			f.apply(requester, argument, this);
			cached = cache.get(argument); // apply might be synchronous
		}
		return cached == IsCalculating.onServer ? Optional.<O> absent()
				: Optional.of((O) cached);
	}

	/**
	 * Implement this to do your actual calculation ASYNCHRONOUSLY. The result
	 * must be delivered using the {@link #setResult(Object, Object)} callback
	 */
	// TODO change mechanism to collect all arguments for one type of call to be
	// able to combine calculations
	// protected abstract void calculate(I argument);

	@Override
	public void result(final I argument, final O result) {
		final Map<I, Object> cache = Storage.Util.current.it().get(id);
		cache.put(argument, result);
		Storage.Util.current.it().set(id, cache);
		Updates.aValueChanged(/* TODO id */null);
	}

	/**
	 * holds the {@link RemoteFunction}for each id. The model is singleton
	 * therefore so are the remoteFunctions.
	 */
	private static final Context<Map<ID, RemoteFunction<?, ?>>> remotes = Context.GLOBAL
			.it().create(new Supplier<Map<ID, RemoteFunction<?, ?>>>() {
				@Override
				public Map<ID, RemoteFunction<?, ?>> get() {
					return new HashMap<ID, RemoteFunction<?, ?>>();
				}
			});

	public static void calculateAll(final Iterable<ID> toCalc) {
		if (Iterables.isEmpty(toCalc))
			return;
		final Map<ID, RemoteFunction<?, ?>> rfs = remotes.it();
		for (final ID id : toCalc) {
			final RemoteFunction<?, ?> rf = rfs.get(id);
			final Object argument = rf.get();// side effect is to fill cache
		}
	}

}
