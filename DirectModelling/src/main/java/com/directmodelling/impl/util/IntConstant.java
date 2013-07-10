package com.directmodelling.impl.util;

import com.directmodelling.api.IntValue;

public class IntConstant implements IntValue {

	private static final long serialVersionUID = -7824237821721101523L;
	private int value;

	public IntConstant(final int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public com.directmodelling.api.Value.Type type() {
		return Type.tInteger;
	}

	@Override
	public int get() {
		return value;
	}

}
