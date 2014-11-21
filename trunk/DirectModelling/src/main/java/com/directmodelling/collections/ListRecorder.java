package com.directmodelling.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import com.directmodelling.api.ListValue;
import com.directmodelling.api.Updates;

public class ListRecorder<T> extends
		CollectionRecorder<T, java.util.List<T>, List.ListReplace<T>> implements
		List<T>, ListValue<T>, java.util.List<T> {

	public ListRecorder() {
		super(new ArrayList<T>());
	}

	public ListRecorder(final java.util.List<T> delegate) {
		super(delegate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(final int index, final T t) {
		// recorder.add(new Replace<T>(index, index, t));
		delegate.add(index, t);
		inserted(index, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(final T t) {
		final int size = delegate.size();
		final boolean added = delegate.add(t);
		if (added)
			inserted(size, t);
		return added;
	}

	@Override
	public boolean addAll(final int index,
			final java.util.Collection<? extends T> c) {
		final boolean added = delegate.addAll(index, c);
		inserted(index, c);
		return added;
	}

	@Override
	public boolean addAll(final java.util.Collection<? extends T> c) {
		final int size = delegate.size();
		final boolean added = delegate.addAll(c);
		inserted(size, c);
		return added;
	}

	public boolean setAll(final T... c) {
		return setAll(Arrays.asList(c));
	}

	public boolean setAll(final java.util.Collection<? extends T> c) {
		delegate.clear();
		final int size = delegate.size();
		final boolean added = addAll(c);
		replaced(0, size, c);
		return added;
	}

	@Override
	public void clear() {
		replaced(0, delegate.size(), (T[]) null);
		delegate.clear();
	}

	@Override
	public T get(final int arg0) {
		return delegate.get(arg0);
	}

	@Override
	public List<T> getValue() {
		return this;
	}

	@Override
	public int indexOf(final Object arg0) {
		return delegate.indexOf(arg0);
	}

	private void inserted(final int index,
			final java.util.Collection<? extends T> c) {
		@SuppressWarnings("unchecked")
		final T[] array = (T[]) c.toArray();
		replaced(index, index, array);
	}

	private void inserted(final int index, final T... array) {
		replaced(index, index, array);
	}

	@Override
	public int lastIndexOf(final Object arg0) {
		return delegate.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<T> listIterator() {
		return delegate.listIterator();// TODO remove?
	}

	@Override
	public ListIterator<T> listIterator(final int arg0) {
		return delegate.listIterator(arg0);// TODO remove?
	}

	@Override
	public T remove(final int i) {
		final T removed = delegate.remove(i);
		removed(i);
		return removed;
	}

	@Override
	public boolean remove(final Object arg0) {
		final int i = delegate.indexOf(arg0);
		if (i < 0)
			return false;
		delegate.remove(i);
		removed(i);
		return true;
	}

	private void removed(final int i) {
		replaced(i, i + 1, (T[]) null);
	}

	private void replaced(final int from, final int to, final Object... by) {
		new ListReplace<T>(this, from, to, by);
		Updates.aValueChanged(this);
	}

	@Override
	public T set(final int arg0, final T arg1) {
		final T result = delegate.set(arg0, arg1);
		replaced(arg0, arg0 + 1, (T[]) null);
		return result;
	}

	@Override
	public java.util.List<T> subList(final int arg0, final int arg1) {
		return delegate.subList(arg0, arg1);
	}

	@Override
	public com.directmodelling.api.Value.Type type() {
		return Type.tObject;
	}

	@Override
	public boolean retainAll(final java.util.Collection<?> c) {
		// TODO Optimize
		return super.retainAll(c);
	}
}
