package com.directmodelling.impl;

import com.directmodelling.api.BranchingValue;

@SuppressWarnings("serial")
public abstract class BranchingVar<T> extends ObjectVar<T> implements
		BranchingValue.Mutable<T> {

	public BranchingVar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BranchingVar(final T t) {
		super(t);
		// TODO Auto-generated constructor stub
	}
}
