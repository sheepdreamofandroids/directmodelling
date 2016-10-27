package com.directmodelling.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.directmodelling.collections.operators.AbstractReadonlyList;

@SuppressWarnings("serial")
public abstract class AbstractReadonlyListWrapper<Element> extends AbstractReadonlyList<Element> {

	protected final List<Element> delegate;

	protected AbstractReadonlyListWrapper() {
		this(new ArrayList<Element>());
	}

	protected AbstractReadonlyListWrapper(List<Element> delegate) {
		this.delegate = delegate;
	}

	/** Implement this to make sure the delegate is up to date. */
	protected abstract void updateDelegate();

	@Override
	public int size() {
		updateDelegate();
		return delegate.size();
	}

	@Override
	public boolean isEmpty() {
		updateDelegate();
		return delegate.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		updateDelegate();
		return delegate.contains(o);
	}

	@Override
	public Iterator<Element> iterator() {
		updateDelegate();
		return delegate.iterator();
	}

	@Override
	public Object[] toArray() {
		updateDelegate();
		return delegate.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		updateDelegate();
		return delegate.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		updateDelegate();
		return delegate.containsAll(c);
	}

	@Override
	public Element get(int index) {
		updateDelegate();
		return delegate.get(index);
	}

	@Override
	public int indexOf(Object o) {
		updateDelegate();
		return delegate.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		updateDelegate();
		return delegate.lastIndexOf(o);
	}

	@Override
	public ListIterator<Element> listIterator() {
		updateDelegate();
		return delegate.listIterator();
	}

	@Override
	public ListIterator<Element> listIterator(int index) {
		updateDelegate();
		return delegate.listIterator(index);
	}

	@Override
	public List<Element> subList(int fromIndex, int toIndex) {
		updateDelegate();
		return delegate.subList(fromIndex, toIndex);
	}

}
