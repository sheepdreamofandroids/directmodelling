/**
 * Copyright (c) Guus C. Bloemsma
 */
package com.directmodelling.demo.shared;

import java.io.Serializable;

import com.directmodelling.api.ListValue.Modifiable;
import com.directmodelling.impl.DoubleVar;
import com.directmodelling.impl.ListVar;
import com.directmodelling.impl.ObjectVar;
import com.directmodelling.impl.Variable;
import com.directmodelling.properties.HasMaximum;
import com.directmodelling.properties.HasMinimum;

public class DemoModel implements IDemoModel, Serializable {

	public static final class MyDoubleVar extends DoubleVar implements HasMaximum<Double>, HasMinimum<Double> {

		@Override
		public Double getMaximum() {
			// TODO Auto-generated method stub
			return 10d;
		}

		public void setMaximum(final Double max) {
			// TODO Auto-generated method stub

		}

		@Override
		public Double getMinimum() {
			// TODO Auto-generated method stub
			return -10d;
		}

		public void setMinimum(final Double min) {
			// TODO Auto-generated method stub

		}
	}

	ObjectVar<String> var = new ObjectVar<String>();
	DemoModel.MyDoubleVar doub = new MyDoubleVar();
	ListVar<IPerson> persons = new ListVar<IPerson>();

	{
		doub.set(5.3);
		var.set("initial");
		for (int i = 10; i > 0; i--)
			persons.add(new Person());
	}

	@Override
	public Variable<String> var() {
		return var;
	}

	@Override
	public DoubleVar doub() {
		return doub;
	}

	@Override
	public DoubleVar getDoubleVar() {
		return doub;
	}

	@Override
	public Modifiable<IPerson> persons() {
		return persons;
	}
}