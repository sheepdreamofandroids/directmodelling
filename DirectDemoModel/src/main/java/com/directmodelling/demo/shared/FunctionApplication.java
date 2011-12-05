package com.directmodelling.demo.shared;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.impl.ObjectVar;

/** Binds a binary operator to arguments. */
@SuppressWarnings("serial")
public class FunctionApplication extends DoubleFunction {

	public FunctionApplication(DoubleValue left, DoubleValue right, Operator op) {
		super();
		this.left = left;
		this.right = right;
		operator.setValue(op);
	}

	/**
	 * Delegates the actual calculation to the operator.
	 */
	@Override
	public double get() {
		return operator.getValue().eval(left.get(), right.get());
	}

	public DoubleValue left() {
		return left;
	}

	public DoubleValue right() {
		return right;
	}

	public Mutable<Operator> operator() {
		return operator;
	}

	private final DoubleValue left;
	private final DoubleValue right;
	private final Mutable<Operator> operator = new ObjectVar<Operator>();
}
