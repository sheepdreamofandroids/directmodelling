package com.directmodelling.demo.shared;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.Status;
import com.directmodelling.impl.Function;

public abstract class DoubleFunction extends Function<Double> implements
		DoubleValue {
	// TODO should be base for DoubleVar

	@Override
	public Double getValue() {
		return get();
	}

	@Override
	public com.directmodelling.api.Value.Type type() {
		return Type.tDouble;
	}

	@Override
	public Status status() {
		return Status.readonly;
	}
}
