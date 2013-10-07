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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Storage.AbstractStorage;
import com.directmodelling.stm.Version;

/**
 * An implementation of {@link Version} that keeps track of writes, not of
 * reads. A subclass should do that.
 * 
 * @author guus
 * 
 */
public class VersionImpl extends AbstractStorage implements Version, Serializable {
	protected final Storage parent;
	protected final Map<Value.Mutable<?>, Object> values = new HashMap<Value.Mutable<?>, Object>();
	protected static final Object nullMarker = "NULL MARKER";

	// TODO use specific hashtables for primitive types
	public VersionImpl() {
		this(null);
	}

	public VersionImpl(final Storage versionImpl) {
		parent = versionImpl;
	}

	@Override
	public Version createChild() {
		return new VersionImpl(this);
	}

	@Override
	public <T> T get(final Value<T> v) {
		if (values.containsKey(v)) {
			final Object result = values.get(v);
			return result == nullMarker ? null : (T) result;
		} else if (null != parent) {
			return parent.get(v);
		} else {
			throw new UninitializedException(v);
		}
	}

	@Override
	public <T> void set(final Value.Mutable<T> m, final T val) {
		System.err.println(m + " := " + val);
		values.put(m, val == null ? nullMarker : val);
		Updates.tracker.aValueChanged(m);
	}

	/** Writes all values to the parent. */
	public void commit() {
		commitTo(parent);
	}

	/** Writes all values to the given storage. */
	public void commitTo(final Storage t) {
		for (final Entry<Mutable<Object>, ?> entry : (Set<Entry<Mutable<Object>, ?>>) (Set) values.entrySet()) {
			t.set(entry.getKey(), entry.getValue());
		}
	}

	// @Override
	// public boolean get(final BooleanValue.Mutable v) {
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
	// public void set(final BooleanValue.Mutable v, final boolean val) {
	// values.put(v, val);
	// Updates.tracker.aValueChanged(v);
	// }
	//
	// @Override
	// public int get(final IntValue.Mutable v) {
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
	// public double get(final DoubleValue.Mutable v) {
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
	// public void set(final com.directmodelling.api.IntValue.Mutable v,
	// final
	// int val) {
	// values.put(v, val);
	// Updates.tracker.aValueChanged(v);
	// }
	//
	// @Override
	// public void set(final com.directmodelling.api.DoubleValue.Mutable v,
	// final double val) {
	// values.put(v, val);
	// Updates.tracker.aValueChanged(v);
	// }

	public Map<Value.Mutable<?>, Object> getWrites() {
		return values;
	}

	@Override
	public void addValues(final Map<Value.Mutable<?>, Object> values) {
		this.values.putAll(values);
	}

	public void reset() {
		values.clear();
	}

	private final Date token = new Date();

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return token.toString();
	}

	public void initializeValues(final VersionImpl storage) {
		values.putAll(storage.values);
	}

	@Override
	public void bindProperty(final Value<?> value) {
		// TODO Auto-generated method stub

	}

}
