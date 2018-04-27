package com.directmodelling.impl;

import java.io.Serializable;

import com.directmodelling.api.Value.Mutable;

public abstract class AbstractMutable<T> implements Mutable<T>, Serializable {
	public AbstractMutable() {
	}

	@Override
	public T value(final T newValue) {
		if (newValue != null)
			setValue(newValue);
		return get();
	};
}
