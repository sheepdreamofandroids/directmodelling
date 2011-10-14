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

import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;

public class BoundLabel extends Label implements HasValue<String> {
	private final TextBinder binder = new TextBinder(this);

	private Mutable<String> var;

	/**
	 * @param var
	 */
	public void setStringVar(final Mutable<String> var) {
		binder.setStringVar(var);
	}

	public void setDoubleVar(Mutable<Double> var) {
		binder.setDoubleVar(var);
	}

	public void setDoubleVal(Value<Double> var) {
		binder.setDoubleVal(var);
	}

	public String getValue() {
		return getText();
	}

	public void setValue(String value) {
		setText(value);
	}

	public void setValue(String value, boolean fireEvents) {
		setText(value);
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return null;
	}
}
