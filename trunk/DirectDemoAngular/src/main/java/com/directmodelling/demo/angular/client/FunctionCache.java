package com.directmodelling.demo.angular.client;

import javax.annotation.Nonnull;

import com.directmodelling.api.Value;
import com.directmodelling.impl.Function;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Storage.Util;
import com.google.common.base.Objects;

/** A {@link Function} that is evaluated on the server and cached on the client. */
public abstract class FunctionCache<T> extends Function<T> {
	private final Value<?>[] dependencies;
	private T lastValue;
	private Object[] lastArguments;
	private transient Storage storage = Util.current.it();

	/**
	 * @param dependencies
	 *            when the value of any of these changes, the server will be
	 *            updated and a new value retrieved.
	 */
	public FunctionCache(@Nonnull final Value<?>... dependencies) {
		this.dependencies = dependencies;// Context.perUser.it().create();
	}

	@Override
	public T getValue() {
		boolean match = lastArguments != null;
		int n = dependencies.length - 1;
		Object value;
		do {// do ... while, therefore value is initialized
			value = dependencies[n].getValue();
			match = !Objects.equal(value, lastArguments[n]);
		} while (match && n-- > 0);
		if (match)
			return lastValue;

		// no match, complete arguments and call
		lastArguments[n] = value;
		while (--n >= 0)
			lastArguments[n] = dependencies[n].getValue();

		call(lastArguments);

		return lastValue;
	}

	private void call(final Object[] lastArguments2) {
		// TODO Auto-generated method stub

	}

	@Override
	public com.directmodelling.api.Value.Type type() {
		// TODO Auto-generated method stub
		return null;
	}

}
