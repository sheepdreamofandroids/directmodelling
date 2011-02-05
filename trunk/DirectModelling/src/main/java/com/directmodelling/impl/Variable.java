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
package com.directmodelling.impl;

import com.directmodelling.api.Status;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Value;
import com.directmodelling.api.Status.HasStatus;
import com.directmodelling.stm.Storage.Util;

public class Variable<T> implements Value.Mutable<T>, HasStatus {
	public Variable() {
		super();
	}

	@Override
	public Status status() {
		return Status.writeable;
	}

	@Override
	public T getValue() {
		return Util.current.it().get(this);
	}

	@Override
	public void setValue(final T value) {
		Util.current.it().set(this, value);
		Updates.aValueChanged(this);
	}

	// Make sure (de-)serialized vars refer to the same values
	private int hash = 0;
	private static int uniqueHash = 0;

	@Override
	public int hashCode() {
		if (0 == hash)
			hash = ++uniqueHash;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Variable)
			return obj.hashCode() == hash;
		return super.equals(obj);
	}

}