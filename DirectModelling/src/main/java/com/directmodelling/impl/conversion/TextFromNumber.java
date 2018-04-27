package com.directmodelling.impl.conversion;

import java.text.NumberFormat;
import java.text.ParseException;

import com.directmodelling.api.Value;
import com.directmodelling.impl.StringVar;
import com.directmodelling.impl.ValueConversion;

public abstract class TextFromNumber<T extends Number> extends
		ValueConversion<String, T> {
	public TextFromNumber(final Value<T> wrapped) {
		this(wrapped, NumberFormat.getInstance());
	}

	public TextFromNumber(final Value<T> wrapped,
			final NumberFormat numberFormatter) {
		super(wrapped);
		this.numberFormatter = numberFormatter;
	}

	// TODO make format configurable
	private final NumberFormat numberFormatter;

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

	public final StringVar format = new StringVar("no format");

}
