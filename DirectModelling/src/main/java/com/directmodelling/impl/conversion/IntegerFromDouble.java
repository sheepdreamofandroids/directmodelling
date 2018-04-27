package com.directmodelling.impl.conversion;

import com.directmodelling.api.Value;
import com.directmodelling.impl.ValueConversion;
import com.directmodelling.properties.HasMaximum;
import com.directmodelling.properties.HasMinimum;

public class IntegerFromDouble extends ValueConversion<Integer, Double>
		implements HasMaximum<Integer>, HasMinimum<Integer> {

	public IntegerFromDouble(final Value<Double> wrapped) {
		super(wrapped);
	}

	@Override
	public Double outer2inner(final Integer value) throws Exception {
		return value.doubleValue();
	}

	@Override
	public Integer inner2outer(final Double inner) {
		return inner.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getMinimum() {
		return wrapped instanceof HasMinimum ? ((HasMinimum<Integer>) wrapped)
				.getMinimum().intValue() : Integer.MIN_VALUE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getMaximum() {
		return wrapped instanceof HasMaximum ? ((HasMaximum<Integer>) wrapped)
				.getMaximum().intValue() : Integer.MAX_VALUE;
	}

}
