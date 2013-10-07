package com.directmodelling.impl.conversion;

import java.text.NumberFormat;
import java.text.ParseException;

import com.directmodelling.impl.Conversion;

public class TextFromNumber extends Conversion<String, Number> {
	public TextFromNumber(final com.directmodelling.api.Value.Mutable<Number> wrapped) {
		super(wrapped);
	}

	NumberFormat numberFormatter =  NumberFormat.getInstance();

	@Override
	public Number outer2inner(final String value) throws ParseException {
		return numberFormatter.parse(value);
	}

	@Override
	public String inner2outer(final Number inner) {
		return numberFormatter.format(inner);
	}

}
