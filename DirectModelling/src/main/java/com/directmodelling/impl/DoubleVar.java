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
/**
 * 
 */
package com.directmodelling.impl;

import com.directmodelling.api.DoubleValue;
import com.google.gwt.core.client.js.JsType;

@JsType
public class DoubleVar extends Variable<Double> implements DoubleValue.Mutable {

	@Override
	public DoubleVar set(final double value) {
		setValue(value);
		return this;
	}

	@Override
	public double getAsDouble() {
		return get();
	}

	// @Override
	// public Type type() {
	// return Type.tDouble;
	// }
}