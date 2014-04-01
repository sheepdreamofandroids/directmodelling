package com.directmodelling.impl.conversion;

import com.directmodelling.api.Value;

public class TextFromLong extends TextFromNumber<Long> {
	public TextFromLong(final Value<Long> wrapped) {
		super(wrapped);
	}

	@Override
	protected Long number2inner(final Number n) {
		return n.longValue();
	}

}
