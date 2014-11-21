package com.directmodelling.demo.angular.shared;

import com.directmodelling.api.Status;
import com.directmodelling.demo.angular.client.FunctionCache;
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
 * @param <O>
 */
public abstract class RemoteFunction<I, O> extends ObjectFun<O> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1337587676729866300L;

	/**
	 * This will be a {@link FunctionCache} on the client and the actual
	 * implementation on the server.
	 */
	// public static Context<Function<I,O>> impl =
	// Context.perUser.it().create(null);

	// private final Map<I, O> results = new MapRecorder<I, O>();

	// private final I input;

	private final Function<I, Optional<O>> f;

	// private final O initial;

	private O current;

	// private Status status = Status.readonly;

	// public RemoteFunction(final I input) {
	// this.input = input;
	// }
	/**
	 * 
	 * @param f
	 *            a particular {@link Function} injected that takes an I and
	 *            returns an {@link Optional}O. Returning
	 *            {@link Optional#absent()} means that we're waiting for a
	 *            result. Throwing any exception means that the calculation
	 *            cannot be done. Any caching should be done in this function.
	 * @param initial
	 */
	public RemoteFunction(final Function<I, Optional<O>> f, final O initial) {
		this.f = f;
		// this.initial = initial;
		current = initial;
	}

	protected abstract I argument();

	@Override
	public O get() {
		final Optional<O> r = f.apply(argument());
		// TODO catch exceptions and set status invalid?
		if (r.isPresent()) {
			// status = Status.readonly;
			return current = r.get();
		} else {
			// status = Status.pending;
			return current;// or fail?
		}
	}

	@Override
	public Status status() {
		return f.apply(argument()).isPresent() ? Status.readonly
				: Status.pending;
	}

	// @Override
	// public void setValue(final O value) {
	// throw new UnsupportedOperationException(
	// "RemoteFunction should be evaluated on the server.");
	// }
	//
	// protected void setProtectedValue(final O value) {
	// super.setValue(value);
	// }
}