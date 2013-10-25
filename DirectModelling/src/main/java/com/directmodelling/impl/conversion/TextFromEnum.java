package com.directmodelling.impl.conversion;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.directmodelling.api.Status;
import com.directmodelling.api.Value;
import com.directmodelling.impl.Conversion;

public class TextFromEnum<T extends Enum<?>> extends Conversion<String, T> {
	private final Map<String, T> valueOfString = new HashMap<String, T>();

	public TextFromEnum(final Value<T> wrapped, final T... values) {
		super(wrapped);
		for (final T t : values) {
			valueOfString.put(t.name(), t);
		}
	}

	@Override
	public T outer2inner(final String value) throws ParseException {
		final T t = valueOfString.get(value);
		if (t == null)
			throw new RuntimeException("\"" + value + "\" is not recognized.");
		return t;
	}

	@Override
	public Status status() {
		// TODO Auto-generated method stub
		return super.status();
	}

	@Override
	public String inner2outer(final T inner) {
		return inner.name();
	}

}
