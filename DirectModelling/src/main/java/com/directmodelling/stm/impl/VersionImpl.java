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
package com.directmodelling.stm.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Value;
import com.directmodelling.stm.Version;

/**
 * An implementation of {@link Version} that keeps track of writes, not of
 * reads. A subclass should do that.
 * 
 * @author guus
 * 
 */
public class VersionImpl implements Version, Serializable {
	protected transient VersionImpl parent;
	protected HashMap<Value.Mutable<?>, Object> values = new HashMap<Value.Mutable<?>, Object>();

	// TODO use specific hashtables for primitive types
	public VersionImpl() {
		this(null);
	}

	public VersionImpl(final VersionImpl versionImpl) {
		parent = versionImpl;
	}

	@Override
	public Version createChild() {
		return new VersionImpl(this);
	}

	@Override
	public <T> T get(final Value<T> v) {
		if (values.containsKey(v)) {
			return (T) values.get(v);
		} else if (null != parent) {
			return parent.get(v);
		} else {
			throw new UninitializedException(v);
		}
	}

	@Override
	public <T> void set(final Value.Mutable<T> v, final T val) {
		System.err.println(v.hashCode() + " := " + val);
		values.put(v, val);
		Updates.tracker.aValueChanged(v);
	}

	// @Override
	// public boolean get(final BooleanValue.Modifiable v) {
	// if (values.containsKey(v)) {
	// return (Boolean) values.get(v);
	// } else if (null != parent) {
	// return parent.get(v);
	// } else {
	// throw new UninitializedException(v);
	// }
	// }
	//
	// @Override
	// public void set(final BooleanValue.Modifiable v, final boolean val) {
	// values.put(v, val);
	// Updates.tracker.aValueChanged(v);
	// }
	//
	// @Override
	// public int get(final IntValue.Modifiable v) {
	// if (values.containsKey(v)) {
	// return (Integer) values.get(v);
	// } else if (null != parent) {
	// return parent.get(v);
	// } else {
	// throw new UninitializedException(v);
	// }
	// }
	//
	// @Override
	// public double get(final DoubleValue.Modifiable v) {
	// if (values.containsKey(v)) {
	// return (Double) values.get(v);
	// } else if (null != parent) {
	// return parent.get(v);
	// } else {
	// throw new UninitializedException(v);
	// }
	// }
	//
	// @Override
	// public void set(final com.directmodelling.api.IntValue.Modifiable v, final
	// int val) {
	// values.put(v, val);
	// Updates.tracker.aValueChanged(v);
	// }
	//
	// @Override
	// public void set(final com.directmodelling.api.DoubleValue.Modifiable v,
	// final double val) {
	// values.put(v, val);
	// Updates.tracker.aValueChanged(v);
	// }

	public HashMap<Value.Mutable<?>, Object> getWrites() {
		return values;
	}

	public void reset() {
		values.clear();
	}

	private Date token = new Date();

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return token.toString();
	}

	public void initializeValues(final VersionImpl storage) {
		values.putAll(storage.values);
	}

}
