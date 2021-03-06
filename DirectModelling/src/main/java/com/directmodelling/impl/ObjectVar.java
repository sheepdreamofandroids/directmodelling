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

import com.directmodelling.api.ObjectValue;

public class ObjectVar<T> extends Variable<T> implements ObjectValue.Mutable<T> {
	public ObjectVar() {
	}

	public ObjectVar(final T t) {
		set(t);
	}

	@Override
	public final T get() {
		return getValue();
	}

	@Override
	public final void set(final T val) {
		setValue(val);
	}
}