package com.directmodelling.demo.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import com.directmodelling.api.DirectModelling;
import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.impl.Command;
import com.directmodelling.impl.DoubleVar;
import com.directmodelling.impl.ObjectFun;
import com.directmodelling.impl.ObjectVar;
import com.google.gwt.core.client.js.JsType;

import dagger.Component;

@JsType
public class Calculator implements Serializable {

	@Component(modules = DirectModelling.class)
	static interface Calc {
		Calculator calculator();
	}

	@Inject
	public Calculator() {
		// TODO Auto-generated constructor stub
	}
	/** Link to outermost (rightmost) operator. Initially link to first value. */
	public final Mutable<DoubleValue> calculation = new ObjectVar<DoubleValue>();

	private final Command plus = new DoAppendOperator(Operator.plus);
	private final Command minus = new DoAppendOperator(Operator.minus);
	private final Command multiply = new DoAppendOperator(Operator.multiply);
	private final Command divide = new DoAppendOperator(Operator.divide);
	private final Command clear = new Command() {
		@Override
		public void run() {
			final DoubleVar var = new DoubleVar();
			var.set(0);
			calculation.setValue(var);
		}
	};

	public final Value<Iterable<DoubleValue>> flattenedOperatorList = new ObjectFun<Iterable<DoubleValue>>() {

		@Override
		public Iterable<DoubleValue> get() {
			final ArrayList<DoubleValue> result = new ArrayList<DoubleValue>();
			DoubleValue o = calculation.getValue();
			while (o instanceof FunctionApplication) {
				result.add(o);
				o = ((FunctionApplication) o).left();
			}
			result.add(o);
			Collections.reverse(result);
			return result;
		}

	};

	static {
		DirectModelling.init();
	}

	{
		DirectModelling.init();
		Calculator calculator = DaggerCalculator_Calc.create().calculator();
		clear.run();
	}

	private final class DoAppendOperator extends Command {
		private final Operator op;

		public DoAppendOperator(final Operator op) {
			this.op = op;
		}

		@Override
		public void run() {
			final DoubleVar right = new DoubleVar();
			right.set(0);
			calculation.setValue(new FunctionApplication(calculation.getValue(), right, op));
		}
	}

	public Command plus() {
		return plus;
	}

	public Command minus() {
		return minus;
	}

	public Command multiply() {
		return multiply;
	}

	public Command divide() {
		return divide;
	}

	public Command clear() {
		return clear;
	}
}
