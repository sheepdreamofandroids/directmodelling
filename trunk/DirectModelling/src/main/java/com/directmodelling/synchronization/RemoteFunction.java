package com.directmodelling.synchronization;

import com.directmodelling.api.ID;
import com.directmodelling.api.Identifiable;
import com.directmodelling.api.Status;
import com.directmodelling.impl.ObjectFun;
import com.google.common.base.Function;
import com.google.common.base.Optional;

/**
 * Template class for {@link com.directmodelling.impl.Function}s that rely on an
 * injected implementation.
 * <p>
 * How to use:
 * <p>
 * An implementation is responsible for getting a particular {@link Function}
 * injected that takes an I and returns an {@link Optional}O. Returning
 * {@link Optional#absent()} means that we're waiting for a result. Throwing any
 * exception means that the calculation cannot be done. Any caching should be
 * done in this function.
 * <p>
 * Also
 * 
 * @author guus
 *
 * @param <I>
 *            inputs
 * @param <O>
 *            outputs
 */
public abstract class RemoteFunction<I, O> extends ObjectFun<O> implements
		Identifiable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1337587676729866300L;

	// transient private final Context<Impl<I, O>> impl;

	transient private O current;

	public abstract class MyImpl implements Impl<I, O> {
	};

	public interface Impl<I, O> {
		void init(RemoteFunction<I, O> f);

		Optional<O> apply(RemoteFunction<I, O> requester);

		/**
		 * Use a guava {@link com.google.common.util.concurrent.AsyncFunction}
		 * instead?
		 */
		public interface AsyncFunction<I, O> {
			void apply(RemoteFunction<I, O> requester, I i,
					ResultCallback<I, O> callback);
		}

		public interface ResultCallback<I, O> {
			void result(I i, O o);
		}
	}

	public RemoteFunction() {
	}

	// /**
	// *
	// * @param f
	// * a particular {@link Function} injected that takes an I and
	// * returns an {@link Optional}O. Returning
	// * {@link Optional#absent()} means that we're waiting for a
	// * result. Throwing any exception means that the calculation
	// * cannot be done. Any caching should be done in this function.
	// * @param initial
	// */
	// public RemoteFunction(final Context<Impl<I, O>> f, final O initial) {
	// this.impl = f;
	// // f.it().init(this);
	// // this.initial = initial;
	// current = initial;
	// }

	/**
	 * @return all current values of argument signals in one convenient package
	 *         that correctly implements {@link #hashCode()} and
	 *         {@link #equals(Object)} for use as a key in a cache.
	 */
	public abstract I argument();

	/** @return the specific implementation */
	protected abstract Impl<I, O> implementation();

	/** Initial value before any requests have succeeded */
	protected abstract O initial();

	private transient boolean doInitialize = true;

	@Override
	public O get() {
		doInit();
		final Optional<O> r = implementation().apply(this);
		// TODO catch exceptions and set status invalid?
		return r.isPresent() ? current = r.get() : current;
	}

	private void doInit() {
		if (doInitialize) {
			doInitialize = false;
			current = initial();
		}
	}

	@Override
	public Status status() {
		doInit();
		return implementation().apply(this).isPresent() ? Status.readonly
				: Status.pending;
	}

	private final ID id = ID.generator.it().createID();

	@Override
	public ID id() {
		return id;
	}

	{// as late as possible, at least after id is initialized
		implementation().init(this);
	}
}