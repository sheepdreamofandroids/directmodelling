package com.directmodelling.impl.conversion;

import java.text.NumberFormat;
import java.text.ParseException;

import com.directmodelling.api.Value;
import com.directmodelling.impl.Conversion;

public abstract class TextFromNumber<T extends Number> extends Conversion<String, T> {
	public TextFromNumber(final Value<T> wrapped) {
		super(wrapped);
	}

	NumberFormat numberFormatter = NumberFormat.getInstance();

	@Override
	public T outer2inner(final String value) throws ParseException {
		return number2inner(numberFormatter.parse(value));
	}

	/** extract intended format from number */
	protected abstract T number2inner(Number number);

	@Override
	public String inner2outer(final T inner) {
		return numberFormatter.format(inner);
	}

}
