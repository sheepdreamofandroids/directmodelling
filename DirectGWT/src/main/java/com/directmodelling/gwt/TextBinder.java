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
import com.google.gwt.user.client.ui.HasValue;

public class TextBinder extends Binder<String, Object> {

	public TextBinder(final HasValue<String> gwtValue) {
		super(gwtValue);
	}

	public final void setVal(final Value<?> var) {
		setVal(var, Converter.toString);
	}

	public final void setStringVar(final Value<String> var) {
		setVar(var, Converter.ID_String, Converter.ID_String);
	}

	public final void setDoubleVar(final Value<Double> var) {
		setVar(var, Converter.String2Double, Converter.Double2String);
	}

	public final void setDoubleVal(final Value<Double> var) {
		setVal(var, Converter.Double2String);
	}

}
