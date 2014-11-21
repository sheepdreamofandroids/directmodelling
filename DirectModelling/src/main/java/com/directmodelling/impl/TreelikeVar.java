package com.directmodelling.impl;

import com.directmodelling.api.TreelikeValue;

@SuppressWarnings("serial")
public abstract class TreelikeVar<T> extends ObjectVar<T> implements
		TreelikeValue.Mutable<T> {

	public TreelikeVar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TreelikeVar(final T t) {
		super(t);
		// TODO Auto-generated constructor stub
	}
}
