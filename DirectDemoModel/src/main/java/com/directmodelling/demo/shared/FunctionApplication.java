package com.directmodelling.demo.shared;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.impl.ObjectVar;

@SuppressWarnings("serial")
public class FunctionApplication extends DoubleFunction {

	public FunctionApplication(DoubleValue left, DoubleValue right, Operator op) {
		super();
		this.left = left;
		this.right = right;
		operator.setValue(op);
	}

	/**
	 * Does the actual calculation. Obviously the operator should know how to
	 * calculate but that is irrelevant to this demo.
	 */
	@Override
	public double get() {
		final double l = left.get();
		final double r = right.get();
		switch (operator.getValue()) {
		case plus:
			return l + r;
		case minus:
			return l - r;
		case multiply:
			return l * r;
		case divide:
			return l / r;
		}
		return 0;
	}

	public final DoubleValue left;
	public final DoubleValue right;
	public final Mutable<Operator> operator = new ObjectVar<Operator>();
}
