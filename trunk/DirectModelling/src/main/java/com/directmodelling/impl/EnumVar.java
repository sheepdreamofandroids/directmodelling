package com.directmodelling.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.directmodelling.api.EnumValue;

public class EnumVar<T extends Enum<T>> extends ObjectVar<T> implements EnumValue.Mutable<T> {
	private List<T> values;
	private NamedDomain.Name2Value<T> nameMap;

	private EnumVar() {
		values = null;
		nameMap = null;
	}

	public EnumVar(final T[] values) {
		// TODO turn the following into one object or implement it right here
		this.values = Collections.unmodifiableList(Arrays.asList(values));
		nameMap = new Name2Value<T>(this, values);
	}

	public EnumVar(final T[] values, final T init) {
		super(init);
		// TODO turn the following into one object or implement it right here
		this.values = Collections.unmodifiableList(Arrays.asList(values));
	}

	@Override
	public List<T> allPotentialValues() {
		return values;
	}

	private void never() {
		values = null;
	}

	@Override
	public String nameOf(final T t) {
		// TODO Auto-generated method stub
		return t.name();
	}

	@Override
	public T valueOf(final String name) {
		return nameMap.valueOf(name);
	}
}
