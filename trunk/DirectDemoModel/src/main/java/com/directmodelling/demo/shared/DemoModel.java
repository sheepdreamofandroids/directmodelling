/**
 * Copyright (c) Guus C. Bloemsma
 */
package com.directmodelling.demo.shared;

import java.io.Serializable;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.DoubleValue.Mutable;
import com.directmodelling.api.ListValue.Modifiable;
import com.directmodelling.impl.ObjectVar;
import com.directmodelling.impl.Variable;
import com.google.gwt.core.client.js.JsType;

@JsType
public class DemoModel implements IDemoModel, Serializable {

	ObjectVar<String> var = new ObjectVar<String>("initial");
	MyDoubleVar doub = new MyDoubleVar(5, -10, 10);
	// ListVar<IPerson> persons = new ListVar<IPerson>() {
	// };

	public MyDoubleVar a = new MyDoubleVar(50, 0, 100);
	public MyDoubleVar b = new MyDoubleVar(50, 0, 100);
	public Sum sum = new Sum(a, b);

	@Override
	public Variable<String> var() {
		return var;
	}

	@Override
	public Mutable doub() {
		return doub;
	}

	@Override
	public Mutable getDoubleVar() {
		return doub;
	}

	@Override
	public Modifiable<IPerson> persons() {
		return null;
	}

	@Override
	public Mutable a() {
		return a;
	}

	@Override
	public Mutable b() {
		return b;
	}

	@Override
	public DoubleValue sum() {
		return sum;
	}
}