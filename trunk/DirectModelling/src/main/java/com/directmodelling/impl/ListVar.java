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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.directmodelling.api.ListValue;
import com.directmodelling.api.Status;
import com.directmodelling.api.Updates;

public class ListVar<T> extends ArrayList<T> implements ListValue.UserValue<T> {

	private static final long serialVersionUID = 1L;

	public ListVar() {
		super();
	}

	public ListVar(final Collection<? extends T> c) {
		super(c);
	}

	public ListVar(final int initialCapacity) {
		super(initialCapacity);
	}

	public Status status() {
		return Status.writeable;
	}

	public Status status(final int i) {
		return Status.writeable;
	}

	@Override
	public T set(final int i, final T value) {
		final T result = super.set(i, value);
		Updates.aValueChanged(this);
		return result;
	}

	@Override
	public T get(final int i) {
		return super.get(i);
	}

	@Override
	public void add(final int index, final T element) {
		super.add(index, element);
		Updates.aValueChanged(this);
	}

	@Override
	protected void removeRange(final int fromIndex, final int toIndex) {
		super.removeRange(fromIndex, toIndex);
		Updates.aValueChanged(this);
	}

	@Override
	public boolean add(final T e) {
		final boolean result = super.add(e);
		Updates.aValueChanged(this);
		return result;
	}

	public boolean setAll(T...t){
		Updates.aValueChanged(this);
		this.clear();
		return this.addAll(Arrays.asList(t));
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
		final boolean result = super.addAll(c);
		Updates.aValueChanged(this);
		return result;
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends T> c) {
		final boolean result = super.addAll(index, c);
		Updates.aValueChanged(this);
		return result;
	}

	@Override
	public void clear() {
		super.clear();
		Updates.aValueChanged(this);
	}

	@Override
	public T remove(final int index) {
		final T result = super.remove(index);
		Updates.aValueChanged(this);
		return result;
	}

	@Override
	public boolean remove(final Object o) {
		final boolean result = super.remove(o);
		Updates.aValueChanged(this);
		return result;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		final boolean result = super.removeAll(c);
		Updates.aValueChanged(this);
		return result;
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		final boolean result = super.retainAll(c);
		Updates.aValueChanged(this);
		return result;
	}

	@Override
	public List<T> getValue() {
		return this;
	}

	@Override
	public Type type() {
		return Type.tObject; // TODO have a tCollection?
	}

}
