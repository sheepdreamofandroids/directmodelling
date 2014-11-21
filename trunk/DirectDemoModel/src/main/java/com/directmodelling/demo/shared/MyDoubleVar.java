package com.directmodelling.demo.shared;

import java.io.Serializable;

import com.directmodelling.impl.DoubleVar;
import com.directmodelling.properties.HasMaximum;
import com.directmodelling.properties.HasMinimum;
import com.google.gwt.core.client.js.JsType;

@JsType
public final class MyDoubleVar extends DoubleVar implements HasMaximum<Double>,
		HasMinimum<Double>, Serializable {

	private double min;
	private double max;

	MyDoubleVar() {
	}

	public MyDoubleVar(final double val, final double min, final double max) {
		super();
		this.min = min;
		this.max = max;
		set(val);
	}

	@Override
	public Double getMaximum() {
		return max;
	}

	public void setMaximum(final Double max) {
		this.max = max;
	}

	@Override
	public Double getMinimum() {
		return min;
	}

	public void setMinimum(final Double min) {
		this.min = min;
	}
}