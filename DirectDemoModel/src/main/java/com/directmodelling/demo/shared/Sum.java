package com.directmodelling.demo.shared;

import java.io.Serializable;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.Status;

public class Sum extends DoubleFunction implements Serializable {
	DoubleValue a;
	DoubleValue b;

	public Sum(DoubleValue a, DoubleValue b) {
		this.a = a;
		this.b = b;
	}

	Sum() {
		// needed for GWT serialization
	}

	@Override
	public double getAsDouble() {
		return a.getAsDouble() + b.getAsDouble();
	}

	@Override
	public Status status() {
		return Status.readonly;
	}
}