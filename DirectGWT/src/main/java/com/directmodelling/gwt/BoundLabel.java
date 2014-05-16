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
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Label;

public class BoundLabel extends Label implements TakesValue<String> {
	private final TextBinder binder = new TextBinder(this);

	private Mutable<String> var;

	/**
	 * @param var
	 */
	public void setStringVar(final Mutable<String> var) {
		binder.setStringVar(var);
	}

	public void setDoubleVar(final Mutable<Double> var) {
		binder.setDoubleVar(var);
	}

	public void setDoubleVal(final Value<Double> var) {
		binder.setDoubleVal(var);
	}

	public final void setVal(final Value<?> var) {
		binder.setVal(var, Converter.toString);
	}

	@Override
	public String getValue() {
		return getText();
	}

	@Override
	public void setValue(final String value) {
		setText(value);
	}

}
