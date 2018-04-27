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
package com.directmodelling.api;

import java.util.function.DoubleSupplier;

import com.google.common.base.Supplier;
import com.google.gwt.core.client.js.JsType;

@JsType
public interface DoubleValue extends Value<Double>/*, DoubleSupplier*/ {
	double getAsDouble();

	@JsType
	public interface Mutable extends DoubleValue, Value.Mutable<Double> {
		DoubleValue.Mutable set(double value);
	}

	// interface UserValue extends Mutable, Value.UserValue<Double> {
	// }
}
