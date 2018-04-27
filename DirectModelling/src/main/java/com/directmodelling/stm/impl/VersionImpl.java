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
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.directmodelling.api.ID;
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
public class VersionImpl extends AbstractStorage implements Version,
		Serializable {
	/** Where to attempt reading when no value is found here? */
	protected final transient Storage parent;
	protected Map<ID, Object> values = new HashMap<ID, Object>();
	protected static final Object nullMarker = "NULL MARKER".intern();

	// TODO use specific hashtables for primitive types
	public VersionImpl() {
		this(null);
	}

	public VersionImpl(final Storage versionImpl) {
		parent = versionImpl;
	}

	@Override
	public Version createChild() {
		freeze();
		return new VersionImpl(this);
	}

	private void freeze() {// mark the latest delta's?
		// final Set<Mutable<?>> a = values.keySet();
		// for (final Mutable<?> mutable : a) {
		//
		// }

	}

	@Override
	public <T> T get(final ID v) {
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
	public <T> void set(final ID m, final T val) {
		System.err.println(m + " := " + val + "   in " + this);
		values.put(m, val == null ? nullMarker : val);
	}

	/** Writes all values to the parent. */
	public void commit() {
		commitTo(parent);
	}

	/** Writes all values to the given storage. */
	public void commitTo(final Storage t) {
		for (final Entry<ID, ?> entry : (Set<Entry<ID, ?>>) (Set) values
				.entrySet()) {
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

	@Override
	public Map<ID, Object> getWrites() {
		return values;
	}

	@Override
	public void addValues(final Map<ID, Object> values) {
		this.values.putAll(values);
	}

	public void reset() {
		System.err.println("Reset " + this);
		values.clear();
	}

	protected final transient Date token = new Date();

	@Override
	public String toString() {
		String vals;
		try {
			vals = values.toString();
		} catch (ConcurrentModificationException e) {
			vals = "~~~concurrently modified~~~";
		}
		return "Version@" + System.identityHashCode(this) + " " + token + " : "
				+ vals + "   parent: " + parent;
	}

	public void initializeValues(final Version storage) {
		values.putAll(storage.getWrites());
	}

	/** move data to param and reset */
	public void moveTo(VersionImpl v) {
		v.values = values;
		values = new HashMap<ID, Object>();
	}

	// @Override
	// public void bindProperty(final ID value) {
	// // TODO Auto-generated method stub
	//
	// }

}
