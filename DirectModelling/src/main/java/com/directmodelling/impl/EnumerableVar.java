package com.directmodelling.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.directmodelling.api.EnumerableValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

//TODO remove NamedDomain and implement between this and EnumVar
@SuppressWarnings("serial")
public class EnumerableVar<T> extends ObjectVar<T> implements
		EnumerableValue.Mutable<T>, NamedDomain<T> {
	private List<T> values;
	private NamedDomain.Name2Value<T> nameMap;

	private EnumerableVar() {
		values = null;
		nameMap = null;
	}

	public EnumerableVar(final T[] values) {
		// TODO turn the following into one object or implement it right here
		this.values = Collections.unmodifiableList(Arrays.asList(values));
		nameMap = new Name2Value<T>(this, values);
	}

	public EnumerableVar(final T[] values, final T init) {
		this(values, init, false);
	}

	public EnumerableVar(final T[] values, final T init,
			final boolean acceptNull) {
		super(init);
		// TODO turn the following into one object or implement it right here
		final List<T> asList = Arrays.asList(values);
		if (acceptNull) {
			final Builder<T> add = new ImmutableList.Builder<T>().add(values)
					.add((T) null);
			asList.add(null);
		}
		this.values = Collections.unmodifiableList(asList);
		nameMap = new Name2Value<T>(this, values);
	}

	@Override
	public List<T> allPotentialValues() {
		return ImmutableList.copyOf(values);
	}

	private void never() {
		values = null;
	}

	@Override
	public String nameOf(final T t) {
		return t.toString();
	}

	@Override
	public T valueOf(final String name) {
		return nameMap.valueOf(name);
	}
}
