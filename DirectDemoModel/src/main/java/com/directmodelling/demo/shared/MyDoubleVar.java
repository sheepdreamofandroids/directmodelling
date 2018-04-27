package com.directmodelling.demo.shared;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.impl.Range;

public class MyDoubleVar extends Range<Double> implements DoubleValue.Mutable {

	public MyDoubleVar(double i, double j, double k) {
		super(i, j, k);
	}

	@Override
	public double getAsDouble() {
		return get().doubleValue();
	}

	@Override
	public com.directmodelling.api.DoubleValue.Mutable set(double value) {
		// TODO Auto-generated method stub
		return null;
	}

}
