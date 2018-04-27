package com.directmodelling.demo.shared;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.Value;
import com.directmodelling.impl.IntFun;
import com.directmodelling.impl.util.Constant;

/** A DSL for building expressions of {@link Value}s */
public class ValueBuilder {
	public DoubleValue val(final double d) {
		return new DoubleFunction() {
			@Override
			public double getAsDouble() {
				return d;
			}
		};
	}

	public IntFun val(final int d) {
		return new IntFun() {
			public int getAsDouble() {
				return d;
			}
		};
	}
}
