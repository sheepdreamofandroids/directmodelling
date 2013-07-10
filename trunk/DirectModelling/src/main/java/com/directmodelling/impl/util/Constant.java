package com.directmodelling.impl.util;

import com.directmodelling.api.Value;

public class Constant<T> implements Value<T> {

	private T value;

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
