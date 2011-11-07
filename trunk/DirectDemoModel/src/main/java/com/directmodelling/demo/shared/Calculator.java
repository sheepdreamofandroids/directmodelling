package com.directmodelling.demo.shared;

import java.util.ArrayList;
import java.util.Collections;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.impl.Command;
import com.directmodelling.impl.DoubleVar;
import com.directmodelling.impl.ObjectFun;
import com.directmodelling.impl.ObjectVar;

public class Calculator {

	/** Link to outermost (rightmost) operator. Initially link to first value. */
	public final Mutable<DoubleValue> calculation = new ObjectVar<DoubleValue>();

	public final Command plus = new DoAppendOperator(Operator.plus);
	public final Command minus = new DoAppendOperator(Operator.minus);
	public final Command multiply = new DoAppendOperator(Operator.multiply);
	public final Command divide = new DoAppendOperator(Operator.divide);
	public final Command clear = new Command() {
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
			ArrayList<DoubleValue> result = new ArrayList<DoubleValue>();
			DoubleValue o = calculation.getValue();
			while (o instanceof FunctionApplication) {
				result.add(o);
				o = ((FunctionApplication) o).left;
			}
			result.add(o);
			Collections.reverse(result);
			return result;
		}

	};

	{
		clear.run();
	}

	private final class DoAppendOperator extends Command {
		private final Operator op;

		public DoAppendOperator(Operator op) {
			this.op = op;
		}

		@Override
		public void run() {
			final DoubleVar right = new DoubleVar();
			right.set(0);
			calculation.setValue(new FunctionApplication(calculation.getValue(), right, op));
		}
	}
}
