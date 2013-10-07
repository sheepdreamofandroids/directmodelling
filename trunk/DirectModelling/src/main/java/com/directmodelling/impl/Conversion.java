package com.directmodelling.impl;

import com.directmodelling.api.Status;
import com.directmodelling.api.Status.HasStatus;
import com.directmodelling.api.Value;

/**
 * A conversion presents a wrapped {@link Value} as one of another type. It
 * converts both ways, so {@link #setValue()} should also work.
 */
public abstract class Conversion<Outer, Inner> implements Value.Mutable<Outer>, HasStatus {
	Value<Inner> wrapped;
	boolean conversionFailed = false;
	private Value.Mutable<Inner> writeable;

	public Conversion(final Value<Inner> wrapped) {
		this.wrapped = wrapped;
		if (wrapped instanceof Value.Mutable)
			writeable = ((Value.Mutable<Inner>) wrapped);
	}

	@Override
	public Outer getValue() {
		return inner2outer(wrapped.getValue());
	}

	@Override
	public void setValue(final Outer value) {
		if (writeable == null)
			throw new UnsupportedOperationException(wrapped + " is not mutable.");
		try {
			writeable.setValue(outer2inner(value));
			conversionFailed = false;
		} catch (final Exception e) {
			conversionFailed = true;
		}
	}

	@Override
	public Status status() {
		return conversionFailed ? Status.invalid : (writeable == null ? Status.readonly : Status.writeable)
				.unlessFrom(wrapped);
	}

	@Override
	public com.directmodelling.api.Value.Type type() {
		return Type.tObject;
	}

	public abstract Inner outer2inner(Outer value) throws Exception;

	public abstract Outer inner2outer(final Inner inner);

}
