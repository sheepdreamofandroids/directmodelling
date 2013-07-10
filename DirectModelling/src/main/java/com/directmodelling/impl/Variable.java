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

import java.io.Serializable;

import com.directmodelling.api.HasKey;
import com.directmodelling.api.Status;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Value;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Storage.HasStorage;
import com.directmodelling.stm.Storage.Util;

public abstract class Variable<T> extends Function<T> implements Value.Mutable<T>, HasKey, HasStorage, Serializable {
	public Variable() {
		super();
	}

	public Variable(final T v) {
		setValue(v);
	}

	public Variable(final Applicable<Object> as[]) {
		for (final Applicable<Object> applicable : as) {
			applicable.applyTo(this);
		}
	}

	@Override
	public Status status() {
		return Status.writeable;
	}

	@Override
	public T getValue() {
		return storage.get(this);
	}

	@Override
	public void setValue(final T value) {
		storage.set(this, value);
		Updates.aValueChanged(this);
	}

	// Make sure (de-)serialized vars refer to the same values
	private transient int hash = 0;
	private static int uniqueHash = 0;
	private transient Storage storage = Util.current.it();

	// private final String id = UUID.uuid();

	@Override
	public int hashCode() {
		if (0 == hash)
			hash = ++uniqueHash;
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Variable)
			return obj.hashCode() == hash;
		return super.equals(obj);
	}

	@Override
	public String getKey() {
		return Registry.get(this);
	}

	@Override
	public void setStorage(final Storage s) {
		this.storage = s;
	}

	@Override
	public Storage getStorage() {
		return storage;
	}

	@Override
	public String toString() {
		String val = "UNINITIALIZED";
		try {
			val = String.valueOf(storage.get(this));
		} catch (final Exception e) {
			// no reason to throw
		}
		return "(Var " + hash + " '" + getKey() + "' = " + val + ")";
	}
}