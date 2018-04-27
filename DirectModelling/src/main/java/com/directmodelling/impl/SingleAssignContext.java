package com.directmodelling.impl;

public class SingleAssignContext<T> extends SimpleContext<T> {
	public void init(final T val) {
		if (value == null)
			value = val;
		else
			throw new IllegalStateException("Context already initialized");
	}
}
