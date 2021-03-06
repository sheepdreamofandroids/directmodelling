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

import com.directmodelling.api.Converter;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.google.gwt.user.client.ui.HasValue;

public class DoubleBinder extends Binder<Double, Object> {

	public DoubleBinder(final HasValue<Double> gwtValue) {
		super(gwtValue);
	}

	public final void setStringVar(final Mutable<String> var) {
		setVar(var, Converter.Double2String, Converter.String2Double);
	}

	public final void setDoubleVar(final Mutable<Double> var) {
		setVar(var, Converter.ID_Double, Converter.ID_Double);
	}

	public final void setDoubleVal(final Value<Double> var) {
		setVal(var, Converter.ID_Double);
	}

}
