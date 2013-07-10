package com.directmodelling.demo.shared;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.EnumValue;
import com.directmodelling.impl.DoubleVar;
import com.directmodelling.impl.EnumVar;

/** Binds a binary operator to arguments. */
@SuppressWarnings("serial")
public class FunctionApplication extends DoubleFunction {

	public FunctionApplication(final DoubleValue left, final DoubleValue right, final Operator op) {
		super();
		this.left = left;
		this.right = right;
		operator.setValue(op);
	}

	private FunctionApplication() {
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

	public DoubleVar right() {
		return (DoubleVar) right;
	}

	public EnumValue.Mutable<Operator> operator() {
		return operator;
	}

	private DoubleValue left;
	private DoubleValue right;
	private final EnumVar<Operator> operator = new EnumVar<Operator>(Operator.values());
}
