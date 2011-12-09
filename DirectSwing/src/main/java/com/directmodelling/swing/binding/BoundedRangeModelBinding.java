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

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;

public class BoundedRangeModelBinding<T> implements ChangeListener, Receiver {

	private final Value<T> val;
	private final Mutable<T> var;
	private final DefaultBoundedRangeModel brm;
	private final Converter<T, Integer> toWidget;
	private final Converter<Integer, T> toVar;

	public BoundedRangeModelBinding(final Value<T> val, final Converter<T, Integer> toWidget,
			final Converter<Integer, T> toVar) {
		this.val = val;
		this.var = (val instanceof Mutable ? (Mutable<T>) val : null);
		this.toWidget = toWidget;
		this.toVar = toVar;
		brm = new DefaultBoundedRangeModel();
		brm.addChangeListener(this);
		Updates.registerForChanges(this);
	}

	public DefaultBoundedRangeModel getModel() {
		return brm;
	}

	@Override
	public void stateChanged(final ChangeEvent e) {
		if (var != null)
			var.setValue(toVar.convert(brm.getValue()));
	}

	@Override
	public void valuesChanged() {
		brm.setValue(toWidget.convert(val.getValue()).intValue());
	}

	public static <T> BoundedRangeModelBinding<T> bind(final JSlider slider, final Value<T> var,
			final Converter<T, Integer> toWidget, final Converter<Integer, T> toVar) {
		final BoundedRangeModelBinding<T> bmr = new BoundedRangeModelBinding<T>(var, toWidget,
				toVar);
		slider.setModel(bmr.getModel());
		return bmr;
	}
}
