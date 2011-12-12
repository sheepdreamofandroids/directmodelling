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
import com.directmodelling.api.Value;

public class BoundedRangeModelBinding<T> extends AbstractBinder<T> implements ChangeListener {

	final DefaultBoundedRangeModel brm;

	public BoundedRangeModelBinding(final Value<T> val, final Converter<T, Integer> toWidget,
					final Converter<Integer, T> toVar) {
		super(val, toWidget, toVar);
		brm = new DefaultBoundedRangeModel();
		brm.addChangeListener(this);
		Updates.registerForChanges(this);
	}

	@Override
	public void valuesChanged() {
		brm.setValue(toWidget.convert(val.getValue()).intValue());
	}

	public DefaultBoundedRangeModel getModel() {
		return brm;
	}

	@Override
	public void stateChanged(final ChangeEvent e) {
		if (var != null)
			var.setValue(toVar.convert(brm.getValue()));
	}

	public static <T> BoundedRangeModelBinding<T> bind(final JSlider slider, final Value<T> var,
					final Converter<T, Integer> toWidget, final Converter<Integer, T> toVar) {
		final BoundedRangeModelBinding<T> bmr = new BoundedRangeModelBinding<T>(var, toWidget, toVar);
		slider.setModel(bmr.getModel());
		return bmr;
	}
}
