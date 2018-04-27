package com.directmodelling.impl.conversion;

import java.text.NumberFormat;

import com.directmodelling.api.Value;

public class TextFromLong extends TextFromNumber<Long> {
	public TextFromLong(final Value<Long> wrapped,
			final NumberFormat numberFormatter) {
		super(wrapped, numberFormatter);
		// TODO Auto-generated constructor stub
	}

	public TextFromLong(final Value<Long> wrapped) {
		super(wrapped);
	}

	@Override
	protected Long number2inner(final Number n) {
		return n.longValue();
	}

}
