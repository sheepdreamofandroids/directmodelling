package com.directmodelling.impl.conversion;

import com.directmodelling.api.Value;

public class TextFromInteger extends TextFromNumber<Integer> {
	public TextFromInteger(final Value<Integer> wrapped) {
		super(wrapped);
	}

	@Override
	protected Integer number2inner(final Number n) {
		return n.intValue();
	}

}
