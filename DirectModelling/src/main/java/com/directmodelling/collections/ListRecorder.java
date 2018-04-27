package com.directmodelling.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import com.directmodelling.api.ListValue;
import com.directmodelling.api.Updates;

/** Wraps a standard list and records all modifications. */
@SuppressWarnings("serial")
public class ListRecorder<Element>
		extends CollectionRecorder<Element, java.util.List<Element>, List.ListReplace<Element>, ListRecorder<Element>>
		implements List<Element>, ListValue<Element>, java.util.List<Element> {

	/** Supplies a default list implementation. */
	public ListRecorder() {
		super(new ArrayList<Element>());
	}

	/** Wraps the given list assuming that it will never be modified directly. */
	public ListRecorder(final java.util.List<Element> delegate) {
		super(delegate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(final int index, final Element t) {
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
	public boolean add(final Element t) {
		final int size = delegate.size();
		final boolean added = delegate.add(t);
		if (added)
			inserted(size, t);
		return added;
	}

	@Override
	public boolean addAll(final int index,
			final java.util.Collection<? extends Element> c) {
		final boolean added = delegate.addAll(index, c);
		inserted(index, c);
		return added;
	}

	@Override
	public boolean addAll(final java.util.Collection<? extends Element> c) {
		final int size = delegate.size();
		final boolean added = delegate.addAll(c);
		inserted(size, c);
		return added;
	}

	public boolean setAll(final Element... c) {
		return setAll(Arrays.asList(c));
	}

	public boolean setAll(final java.util.Collection<? extends Element> c) {
		delegate.clear();
		final int size = delegate.size();
		final boolean added = addAll(c);
		replaced(0, size, (Element[]) c.toArray());
		return added;
	}

	@Override
	public void clear() {
		replaced(0, delegate.size(), (Element[]) null);
		delegate.clear();
	}

	@Override
	public Element get(final int arg0) {
		return delegate.get(arg0);
	}

	@Override
	public List<Element> get() {
		return this;
	}

	@Override
	public int indexOf(final Object arg0) {
		return delegate.indexOf(arg0);
	}

	private void inserted(final int index,
			final java.util.Collection<? extends Element> c) {
		@SuppressWarnings("unchecked")
		final Element[] array = (Element[]) c.toArray();
		replaced(index, index, array);
	}

	private void inserted(final int index, final Element... array) {
		replaced(index, index, array);
	}

	@Override
	public int lastIndexOf(final Object arg0) {
		return delegate.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<Element> listIterator() {
		return delegate.listIterator();// TODO remove?
	}

	@Override
	public ListIterator<Element> listIterator(final int arg0) {
		return delegate.listIterator(arg0);// TODO remove?
	}

	@Override
	public Element remove(final int i) {
		final Element removed = delegate.remove(i);
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
		replaced(i, i + 1, (Element[]) null);
	}

	private void replaced(final int from, final int to, @SuppressWarnings("unchecked") final Element... by) {
		new ListReplace<>(this, from, to, by);
		Updates.aValueChanged(this);
	}

	@Override
	public Element set(final int arg0, final Element arg1) {
		final Element result = delegate.set(arg0, arg1);
		replaced(arg0, arg0 + 1, (Element[]) null);
		return result;
	}

	@Override
	public java.util.List<Element> subList(final int arg0, final int arg1) {
		return delegate.subList(arg0, arg1);
	}

	// @Override
	// public com.directmodelling.api.Value.Type type() {
	// return Type.tObject;
	// }

	@Override
	public boolean retainAll(final java.util.Collection<?> c) {
		// TODO Optimize
		return super.retainAll(c);
	}

	@Override
	protected com.directmodelling.collections.List.ListReplace<Element> createSentinel() {
		return new ListReplace<>(this, 0, 0, (Element[]) null);
	}
}
