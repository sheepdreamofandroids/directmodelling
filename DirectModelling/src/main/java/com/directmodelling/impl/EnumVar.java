package com.directmodelling.impl;

import com.directmodelling.api.EnumValue;

/** Enumerable and namable domain. */
public class EnumVar<T extends Enum<T>> extends EnumerableVar<T> implements
		EnumValue.Mutable<T> {

	public EnumVar(final T[] values) {
		super(values);
	}

	public EnumVar(final T[] values, final T init) {
		super(values, init);
	}

	public EnumVar(final T[] values, final T init, final boolean acceptNull) {
		super(values, init, acceptNull);
	}

}
