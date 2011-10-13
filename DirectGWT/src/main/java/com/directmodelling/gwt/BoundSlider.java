/*******************************************************************************
 * Copyright (c) 2010 Guus C. Bloemsma.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *******************************************************************************/
package com.directmodelling.gwt;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.properties.HasMaximum;
import com.directmodelling.properties.HasMinimum;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

/** Slider bound to a variable. */
public class BoundSlider<N extends Number & Comparable<N>, T extends Mutable<N> & HasMinimum<N> & HasMaximum<N>>
				extends SliderBar implements HasValue<Double>/*
															 * , HasMaximum<N>,
															 * HasMinimum<N>
															 */, Receiver {
	public BoundSlider() {
		super(0, 100);
		setNumTicks(10);
		setStepSize(3);
		// setMinValue(0);
		// setMaxValue(100);
		setEnabled(true);
		setCurrentValue(5);
		// onResize(500, 500);
	}

	private final DoubleBinder binder = new DoubleBinder(this);
	private Mutable<Double> v;

	/**
	 * @param var
	 */
	// @SuppressWarnings("unchecked")
	// public void setVar(final Mutable<? extends Number> var) {
	// assert var instanceof HasMaximum;
	// assert var instanceof HasMinimum;
	// binder.setVar((Mutable<Number>) var);
	// setMaxValue(((HasMaximum<N>) var).getMaximum().doubleValue());
	// setMinValue(((HasMinimum<N>) var).getMinimum().doubleValue());
	// }

	public void setDoubleVar(Mutable<Double> v) {
		this.v = v;
		binder.setDoubleVar(v);
		if (v instanceof HasMinimum || v instanceof HasMaximum)
			Updates.tracker.registerForChanges(this);
	}

	public void setStringVar(Mutable<String> v) {
		binder.setStringVar(v);
	}

	public Double getValue() {
		return getCurrentValue();
	}

	public void setValue(final Double value) {
		setCurrentValue(value, false);
	}

	public void setValue(final Double value, final boolean fireEvents) {
		setCurrentValue(value, fireEvents);
	}

	public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<Double> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public void setCurrentValue(double newValue, boolean fireEvent) {
		double oldValue = getCurrentValue();
		super.setCurrentValue(newValue, fireEvent);
		if (fireEvent)
			ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
	}

	// @Override
	// public N getMaximum() {
	// return getMaxValue();
	// }
	//
	// @Override
	// public void setMaximum(final Number max) {
	// setMaxValue(max.doubleValue());
	// }
	//
	// @Override
	// public Double getMinimum() {
	// // TODO Auto-generated method stub
	// return getMinValue();
	// }
	//
	// @Override
	// public void setMinimum(final Number min) {
	// setMinValue(min.doubleValue());
	// }

	@SuppressWarnings("unchecked")
	public void valuesChanged() {
		if (v instanceof HasMinimum)
			setMinValue(((HasMinimum<Double>) v).getMinimum());
		if (v instanceof HasMaximum)
			setMaxValue(((HasMaximum<Double>) v).getMaximum());
	}
}
