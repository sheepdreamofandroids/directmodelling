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

import com.directmodelling.api.IntValue;

public class IntVar extends Variable<Integer> implements IntValue.Modifiable {

	@Override
	public void set(final int value) {
		setValue(value);
	}

	@Override
	public int get() {
		return getValue();
	}

	@Override
	public Type type() {
		return Type.tInteger;
	}

	public IntVar _(Applicable<? super IntVar> a) {
		a.applyTo(this);
		return this;
	}

	public IntVar() {
	}

	public IntVar(Applicable<? super IntVar> as[]) {
		super((Applicable<Object>[]) as);
	}
}