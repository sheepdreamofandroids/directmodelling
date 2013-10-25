package com.directmodelling.impl.conversion;

import com.directmodelling.api.Value;

public class TextFromDouble extends TextFromNumber<Double> {
	public TextFromDouble(final Value<Double> wrapped) {
		super(wrapped);
	}

	@Override
	protected Double number2inner(final Number n) {
		return n.doubleValue();
	}

}
