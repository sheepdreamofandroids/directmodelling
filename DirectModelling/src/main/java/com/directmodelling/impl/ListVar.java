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

/**
 * A variable that IS a list. See {@link #typeParameter()} for why it is
 * abstract. When used as-is, simply write <code>{}</code> right after the
 * declaration.
 */
public abstract class ListVar<T> extends ArrayList<T> implements
		ListValue.UserValue<T> {
	private final int gwtDummy = 5;
	private static final long serialVersionUID = 1L;

	// public final Class<T> type;

	public ListVar() {
		super();
		// this.type = typeParameter();
	}

	public ListVar(final Collection<? extends T> c) {
		super(c);
		// this.type = typeParameter();
	}

	public ListVar(final int initialCapacity) {
		super(initialCapacity);
		// this.type = typeParameter();
	}

	// /**
	// * This method only works when called on subclasses of ListVar, not on
	// * instances of ListVar itself. Thank java generics erasure for that. This
	// * is the reason that ListVar is abstract.
	// *
	// * @return the type parameter given to ListVar
	// */
	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// public static Class<?> componentType(Class<? extends ListVar> c) {
	// // find the direct subclass of ListVar
	// while (c.getSuperclass() != ListVar.class)
	// c = (Class<? extends ListVar>) c.getSuperclass();
	// // get the base (=ListVar) as a ParameterizedType and then the first
	// // type argument
	// return ((ParameterizedType) c.getGenericSuperclass())
	// .getActualTypeArguments()[0].getClass();
	// }

	public Status status() {
		return Status.writeable;
	}

	@Override
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

	public boolean setAll(final T... t) {
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

}
