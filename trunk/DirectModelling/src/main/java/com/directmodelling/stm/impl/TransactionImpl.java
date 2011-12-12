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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;

public class TransactionImpl extends VersionImpl {
	protected HashMap<Value<?>, Object> reads = new HashMap<Value<?>, Object>();

	public TransactionImpl() {
		this(null);
	}

	public TransactionImpl(final VersionImpl parentTransaction) {
		super(parentTransaction);
	}

	@Override
	public TransactionImpl createChild() {
		return new TransactionImpl(this);
	}

	// @Override
	// public boolean get(final Modifiable v) {
	// final boolean val = super.get(v);
	// if (!reads.containsKey(v)) {
	// reads.put(v, val);
	// }
	// return val;
	// }
	//
	// @Override
	// public int get(final com.directmodelling.api.IntValue.Modifiable v) {
	// final int val = super.get(v);
	// if (!reads.containsKey(val)) {
	// reads.put(v, val);
	// }
	// return val;
	// }
	//
	// @Override
	// public double get(final com.directmodelling.api.DoubleValue.Modifiable v)
	// {
	// final double val = super.get(v);
	// if (!reads.containsKey(val)) {
	// reads.put(v, val);
	// }
	// return val;
	// }

	@Override
	public <T> T get(final Value<T> v) {
		// TODO fail here when any parent received a commit
		final Object a = values.get(v);
		if (null != a) {
			return a == nullMarker ? null : (T) a;
		}
		final Object b = reads.get(v);
		if (null != b) {
			return b == nullMarker ? null : (T) b;
		}
		final T val = parent.get(v);
		// remember that this value was known before and used by the transaction
		reads.put(v, val == null ? nullMarker : val);
		return val;
	}

	/**
	 * Try to accept all changes in t. This will work when none of the reads in
	 * t have been changed already. This only makes sense when both transactions
	 * emerge from the same parent.
	 * 
	 * @return whether the commit succeeded.
	 */
	public boolean mergeAfter(final TransactionImpl t) {
		assert t.parent == parent;
		return mergeAfter(t.getReads(), t.getWrites());
	}

	public HashMap<Value<?>, Object> getReads() {
		return reads;
	}

	@Override
	public void reset() {
		super.reset();
		reads.clear();
	}

	/**
	 * Like {@link TransactionImpl#mergeAfter(TransactionImpl)}, only reads and
	 * writes are specified seperately.
	 */
	public boolean mergeAfter(final Map<Value<?>, Object> reads,
			final Map<Value.Mutable<?>, Object> writes) {
		final boolean success = Collections.disjoint(getWrites().keySet(), reads.keySet());
		// TODO Maybe a better criterion is checking whether all the read values
		// are still the same so that writing without changing the value doesn't
		// prohibit a commit.
		if (success) {
			getWrites().putAll(writes);
			reads.putAll(reads);
		}
		return success;
	}

	public void commitTo(final VersionImpl t) {
		// check that none of the reads have been modified
		for (final Entry<Value<?>, Object> entry : reads.entrySet()) {
			if (!equals(t.get(entry.getKey()), entry.getValue())) {
				throw new CommitAbortedException("Value of " + entry.getKey()
						+ " was changed: expected (" + entry.getValue() + ") but got ("
						+ t.get(entry.getKey()) + ").");
			}
		}
		// all input is still correct, now apply the output
		for (final Entry<Mutable<Object>, ?> entry : (Set<Entry<Mutable<Object>, ?>>) (Set) values
				.entrySet()) {
			t.set(entry.getKey(), entry.getValue());
		}
	}

	private boolean equals(final Object a, final Object b) {
		if (a == b) {
			return true;
		}
		if (null == a) {
			return false;
		}
		return a.equals(b);
	}
}
