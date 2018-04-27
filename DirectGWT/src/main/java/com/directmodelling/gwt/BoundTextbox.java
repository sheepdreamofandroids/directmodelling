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

import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.IntValue;
import com.directmodelling.api.Value;
import com.google.gwt.user.client.ui.TextBox;

public class BoundTextbox extends TextBox {

	private final TextBinder binder = new TextBinder(this);

	public BoundTextbox() {
	}

	public BoundTextbox(final Value var) {
		setStringVar(var);
	}

	public BoundTextbox(final IntValue var) {
		setIntVar(var);
	}

	public BoundTextbox(final DoubleValue var) {
		setDoubleVar(var);
	}

	public final void setStringVar(final Value<String> var) {
		binder.setStringVar(var);
	}

	public final void setIntVar(final Value<Integer> var) {
		binder.setIntVar(var);
	}

	public void setDoubleVar(final Value<Double> var) {
		binder.setDoubleVar(var);
	}
}
