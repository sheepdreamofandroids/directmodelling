package com.directmodelling.impl.util;

import com.directmodelling.impl.Function;

public class Constant<T> extends Function<T> {

	private final T value;

	public Constant(final T value) {
		this.value = value;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public com.directmodelling.api.Value.Type type() {
		return Type.tInteger;
	}

}
