package com.directmodelling.impl.util;

import com.directmodelling.impl.IntFun;

public class IntConstant extends IntFun {

	private static final long serialVersionUID = -7824237821721101523L;
	private final int value;

	public IntConstant(final int value) {
		this.value = value;
	}

	@Override
	public Integer get() {
		return value;
	}

	@Override
	public int getAsInt() {
		return value;
	}

}
