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

import com.directmodelling.api.ID;
import com.directmodelling.collections.Delta.HasDeltas;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Version;

public class TransactionImpl extends VersionImpl {
	protected HashMap<ID, Object> reads = new HashMap<ID, Object>();

	public TransactionImpl() {
		this(null);
	}

	public TransactionImpl(final Version parentTransaction) {
		super(parentTransaction);
		if (parentTransaction != null)
			for (final Entry<ID, Object> entry : parentTransaction.getWrites()
					.entrySet()) {
				if (entry.getKey() instanceof HasDeltas)
					((HasDeltas) entry.getKey()).getLastDelta();
			}
	}

	@Override
	public TransactionImpl createChild() {
		return new TransactionImpl(this);
	}

	// @Override
	// public boolean get(final Mutable v) {
	// final boolean val = super.get(v);
	// if (!reads.containsKey(v)) {
	// reads.put(v, val);
	// }
	// return val;
	// }
	//
	// @Override
	// public int get(final com.directmodelling.api.IntValue.Mutable v) {
	// final int val = super.get(v);
	// if (!reads.containsKey(val)) {
	// reads.put(v, val);
	// }
	// return val;
	// }
	//
	// @Override
	// public double get(final com.directmodelling.api.DoubleValue.Mutable v)
	// {
	// final double val = super.get(v);
	// if (!reads.containsKey(val)) {
	// reads.put(v, val);
	// }
	// return val;
	// }

	@Override
	public <T> T get(final ID v) {
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

	public HashMap<ID, Object> getReads() {
		return reads;
	}

	@Override
	public void reset() {
		System.err.println("Reset of " + this);
		if (((VersionImpl) parent).values.size() < 2)
			throw new RuntimeException("Missing values");// TODO remove
		super.reset();
		reads.clear();
	}

	@Override
	public String toString() {
		return "Transaction " + token + " : " + values + "   parent: " + parent;
	}

	/**
	 * Like {@link TransactionImpl#mergeAfter(TransactionImpl)}, only reads and
	 * writes are specified seperately.
	 */
	public boolean mergeAfter(final Map<ID, Object> reads,
			final Map<ID, Object> writes) {
		final boolean success = Collections.disjoint(getWrites().keySet(),
				reads.keySet());
		// TODO Maybe a better criterion is checking whether all the read values
		// are still the same so that writing without changing the value doesn't
		// prohibit a commit.
		if (success) {
			getWrites().putAll(writes);
			reads.putAll(reads);
		}
		return success;
	}

	/** Writes all values to the given storage. */
	@Override
	public void commitTo(final Storage other) {
		// check that none of the reads have been modified
		for (final Entry<ID, Object> entry : reads.entrySet()) {
			if (!equals(other.get(entry.getKey()), entry.getValue())) {
				System.err.println("Value of " + entry.getKey()
						+ " was changed: expected (" + entry.getValue()
						+ ") but got (" + other.get(entry.getKey()) + ").");
				// throw new CommitAbortedException("Value of " + entry.getKey()
				// + " was changed: expected (" + entry.getValue()
				// + ") but got (" + other.get(entry.getKey()) + ").");
			}
		}
		// all input is still correct, now apply the output
		super.commitTo(other);
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

	public void moveTo(TransactionImpl t) {
		super.moveTo(t);
		t.reads = reads;
		reads = new HashMap<ID, Object>();
	}
}
