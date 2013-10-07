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
package com.directmodelling.swing.binding;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Value;
import com.directmodelling.properties.HasMaximum;
import com.directmodelling.properties.HasMinimum;

public class BoundedRangeModelBinding<T extends Comparable<T>> extends AbstractBinder<T, Integer> implements
		ChangeListener {

	private BoundedRangeModel bmr;

	public BoundedRangeModelBinding(final Value<T> val, final BoundedRangeModel bmr,
			final Converter<T, Integer> toWidget, final Converter<Integer, T> toVar) {
		super(val, toWidget, toVar);
		this.bmr = bmr;
		bmr.addChangeListener(this);
		Updates.registerForChanges(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valuesChanged() {
		super.valuesChanged();
		if (val instanceof HasMinimum) {
			bmr.setMinimum(toWidget.convert(((HasMinimum<T>) val).getMinimum()));
		}
		if (val instanceof HasMaximum) {
			bmr.setMaximum(toWidget.convert(((HasMaximum<T>) val).getMaximum()));
		}
	}

	@Override
	public void stateChanged(final ChangeEvent e) {
		widgetChanged();
	}

	public static <T extends Comparable<T>> BoundedRangeModelBinding<T> bind(final JSlider slider, final Value<T> var,
			final Converter<T, Integer> toWidget, final Converter<Integer, T> toVar) {
		return new BoundedRangeModelBinding<T>(var, slider.getModel(), toWidget, toVar);
	}

	@Override
	protected void setWidgetValue(final Integer v) {
		bmr.setValue(v);
	}

	@Override
	protected Integer getWidgetValue() {
		return bmr.getValue();
	}
}
