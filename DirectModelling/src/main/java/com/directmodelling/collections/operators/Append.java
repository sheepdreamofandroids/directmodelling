package com.directmodelling.collections.operators;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.collections.List;

public class Append<S> extends AbstractImmutableList<S> implements List<S>,
		Receiver {
	// TODO derive from Union of two Collections?
	private List<S>[] components;
	private transient int[] offsets;
	private transient boolean dirty = false;
	private transient ListReplace<S>[] deltas;
	// TODO reset above after deserialization
	{
		Updates.registerForChanges(this);
	}

	private Append() {
		// only for serialization
	}

	private void readObject(final java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		init();
	}

	public Append(final List<S>... components) {
		this.components = components;
		init();
	}

	/**
	 * @param components
	 */
	private void init() {
		final int count = components.length;
		offsets = new int[count + 1];
		offsets[0] = 0;
		@SuppressWarnings("unchecked")
		final ListReplace<S>[] unchecked = new ListReplace[count];
		deltas = unchecked;
		for (int i = 0; i < unchecked.length; i++) {
			deltas[i] = components[i].getLastDelta();
		}
		dirty = false;
	}

	@Override
	public void valuesChanged() {
		dirty = true;
	}

	@Override
	public int size() {
		return updateSizes();
	}

	private int updateSizes() {
		int total = 0;
		for (int i = 0; i < components.length; i++) {
			total += components[i].size();
			offsets[i + 1] = total;
		}
		return total;
	}

	@Override
	public boolean isEmpty() {
		return updateSizes() == 0;
	}

	@Override
	public boolean contains(final Object o) {
		for (final List<S> list : components) {
			if (list.contains(o))
				return true;
		}
		return false;
	}

	@Override
	public Iterator<S> iterator() {
		return listIterator();
	}

	@Override
	public Object[] toArray() {
		return toArray(null);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		final int size = size();
		if (a == null) {
			@SuppressWarnings("unchecked")
			final T[] ts = (T[]) new Object[0];
			a = ts;
		} else if (a.length < size) {
			@SuppressWarnings("unchecked")
			final T[] na = (T[]) Array.newInstance(a.getClass()
					.getComponentType(), size);
			a = na;
		}
		final List<S>[] b = components;
		int pos = 0;
		for (final List<S> list : b) {
			final Object[] array = list.toArray();
			System.arraycopy(array, 0, a, pos, array.length);
			pos += array.length;
		}
		return a;
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		for (final Object object : c) {
			if (!contains(object))
				return false;
		}
		return true;
	}

	@Override
	public S get(final int index) {
		final int size = size();
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index " + index
					+ " out of range 0-" + (size - 1) + ".");
		// binary search
		int lo = 0, hi = components.length;

		while (hi > lo + 1) {
			final int mid = (lo + hi) / 2;
			if (offsets[mid] > index)
				hi = mid;
			else
				lo = mid;
		}
		return components[lo].get(index - offsets[lo]);
	}

	@Override
	public int indexOf(final Object o) {
		updateSizes();
		for (int i = 0; i < components.length; i++) {
			final int indexOf = components[i].indexOf(o);
			if (indexOf >= 0)
				return offsets[i] + indexOf;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(final Object o) {
		updateSizes();
		for (int i = components.length - 1; i >= 0; i--) {
			final int indexOf = components[i].indexOf(o);
			if (indexOf >= 0)
				return offsets[i] + indexOf;
		}
		return -1;
	}

	@Override
	public ListIterator<S> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<S> listIterator(final int index) {
		return new ListIterator<S>() {
			ListIterator<S> sub = null;
			int subFor = 0;

			@Override
			public boolean hasNext() {
				return sub.hasNext() || nextSub();
			}

			/**
			 * search for next non-empty component and take it's iterator
			 * 
			 * @returns whether iterator found
			 */
			private boolean nextSub() {
				while (subFor < components.length) {
					sub = components[subFor].listIterator();
					if (sub.hasNext())
						return true;
				}
				return false;
			}

			@Override
			public S next() {
				if (hasNext())
					return sub.next();
				else
					throw new NoSuchElementException("No more elements");
			}

			@Override
			public void remove() {
				sub.remove();
			}

			@Override
			public boolean hasPrevious() {
				return sub.hasPrevious() || previousSub();
			}

			/**
			 * search for previous non-empty component and take it's iterator
			 * 
			 * @returns whether iterator found
			 */
			private boolean previousSub() {
				while (subFor >= 0) {
					sub = components[subFor].listIterator();
					if (sub.hasNext())
						return true;
				}
				return false;
			}

			@Override
			public S previous() {
				if (hasPrevious())
					return sub.previous();
				else
					throw new NoSuchElementException("No previous elements");
			}

			@Override
			public int nextIndex() {
				if (subFor > 0)
					return offsets[subFor - 1] + sub.nextIndex();
				else
					return sub.nextIndex();
			}

			@Override
			public int previousIndex() {
				if (subFor > 0)
					return offsets[subFor - 1] + sub.previousIndex();
				else
					return sub.previousIndex();
			}

			@Override
			public void set(final S e) {
				sub.set(e);
			}

			@Override
			public void add(final S e) {
				throw immutable();
			}
		};
	}

	@Override
	public java.util.List<S> subList(final int fromIndex, final int toIndex) {
		// How would this behave while components are changing?
		throw new UnsupportedOperationException();
	}

	@Override
	public ListReplace<S> getLastDelta() {
		if (dirty) {
			for (int i = 0; i < deltas.length; i++) {
				ListReplace<S> delta = deltas[i];
				while (delta.getNext() != null) {
					delta = delta.getNext();
					if (delta instanceof ListReplace) {
						final ListReplace<S> r = delta;
						deltas[i] = delta;
						// constructor links after current lastDelta and updates
						// lastDelta field to this new one
						new ListReplace<S>(this, r.from + offsets[i], r.to
								+ offsets[i], r.elements);
					}
				}
			}
		}
		return super.getLastDelta();
	}
}
