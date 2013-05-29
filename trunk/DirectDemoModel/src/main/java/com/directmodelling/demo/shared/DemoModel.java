/**
 * Copyright (c) Guus C. Bloemsma
 */
package com.directmodelling.demo.shared;

import java.io.Serializable;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.ListValue.Modifiable;
import com.directmodelling.impl.DoubleVar;
import com.directmodelling.impl.ListVar;
import com.directmodelling.impl.ObjectVar;
import com.directmodelling.impl.Variable;

public class DemoModel implements IDemoModel, Serializable {

	ObjectVar<String> var = new ObjectVar<String>();
	MyDoubleVar doub = new MyDoubleVar(5, -10, 10);
	ListVar<IPerson> persons = new ListVar<IPerson>() {
	};

	MyDoubleVar a = new MyDoubleVar(50, 0, 100);
	MyDoubleVar b = new MyDoubleVar(50, 0, 100);
	Sum sum = new Sum(a, b);

	{
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

	@Override
	public DoubleVar a() {
		return a;
	}

	@Override
	public DoubleVar b() {
		return b;
	}

	@Override
	public DoubleValue sum() {
		return sum;
	}
}