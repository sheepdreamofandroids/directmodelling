package com.directmodelling.impl;

import java.util.Comparator;

import com.directmodelling.api.Status;
import com.directmodelling.api.Value;
import com.directmodelling.properties.HasMaximum;
import com.directmodelling.properties.HasMinimum;

/**
 * A variable with a bounded value. Bounds can be dynamic either by passing in
 * non-constant {@link Value}s or by overriding {@link #getMaximum()} or
 * {@link #getMinimum()}. The same goes for the comparator.
 */
public class Range<T extends Comparable<? super T>> extends ObjectVar<T>
		implements HasMinimum<T>, HasMaximum<T>, Comparator<T> {
	private Value<T> minimum;
	private Value<T> maximum;
	private Comparator<? super T> comparator;

	public Range(T initialValue, Value<T> minimum, Value<T> maximum) {
		this(initialValue, minimum, maximum, null);
	}

	public Range(T initialValue, Value<T> minimum, Value<T> maximum,
			Comparator< T> comparator) {
		super(initialValue);
		this.minimum = minimum;
		this.maximum = maximum;
		this.comparator =  (comparator == null ? this
				: comparator);
	}

	@Override
	public com.directmodelling.api.Status status() {
		if (get() == null)
			return new Status.Invalid.Missing();
		else if (comparator.compare(minimum.getValue(), get()) > 0)
			return new Status.Invalid.TooLow(minimum);
		else if (comparator.compare(maximum.getValue(), get()) < 0)
			return new Status.Invalid.TooHigh(minimum);
		else
			return super.status();
	}

	@Override
	public T getMaximum() {
		return maximum.getValue();
	}

	@Override
	public T getMinimum() {
		return minimum.getValue();
	}

	@Override
	public int compare(T o1, T o2) {
		return o1 == o2 || o1 == null || o2 == null ? 0 : o1.compareTo(o2);
	}
}