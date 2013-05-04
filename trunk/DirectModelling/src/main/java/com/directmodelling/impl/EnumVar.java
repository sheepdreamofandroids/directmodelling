package com.directmodelling.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.directmodelling.api.EnumValue;

public class EnumVar<T extends Enum<T>> extends ObjectVar<T> implements EnumValue.Mutable<T> {
	private final List<T> values;

	public EnumVar(T[] values) {
		// TODO turn the following into one object or implement it right here
		this.values = Collections.unmodifiableList(Arrays.asList(values));
	}

	@Override
	public List<T> allPotentialValues() {
		return values;
	}
}