package com.directmodelling.impl;

import com.directmodelling.api.Conversion;
import com.directmodelling.api.Status;
import com.directmodelling.api.Status.HasStatus;
import com.directmodelling.api.Status.Invalid;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Value;

/**
 * A conversion presents a wrapped {@link Value} as one of another type. It
 * converts both ways, so {@link #setValue()} should also work.
 */
public abstract class ValueConversion<Outer, Inner> extends
		AbstractMutable<Outer> implements HasStatus, Conversion<Outer, Inner> {
	protected Value<Inner> wrapped;
	private Invalid conversionFailure = null;
	private Value.Mutable<Inner> writeable;

	public ValueConversion(final Value<Inner> wrapped) {
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
			throw new UnsupportedOperationException(wrapped
					+ " is not mutable.");
		try {
			writeable.setValue(outer2inner(value));
			conversionFailure = null;
		} catch (final Invalid e) {
			conversionFailure = e;
			Updates.aValueChanged(null);
		} catch (final Exception e) {
			conversionFailure = new Invalid.Failure(e);
			// otherwise nobody knows that the status changed
			Updates.aValueChanged(null);
		}
	}

	@Override
	public Status status() {
		return conversionFailure != null ? conversionFailure
				: (writeable == null ? Status.readonly : Status.writeable)
						.unlessFrom(wrapped);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.directmodelling.impl.Conversion#outer2inner(Outer)
	 */
	@Override
	public abstract Inner outer2inner(Outer value) throws Exception;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.directmodelling.impl.Conversion#inner2outer(Inner)
	 */
	@Override
	public abstract Outer inner2outer(final Inner inner);

}
