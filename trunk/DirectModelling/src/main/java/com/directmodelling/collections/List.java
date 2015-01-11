package com.directmodelling.collections;

import java.io.Serializable;
import java.util.AbstractList;

import com.directmodelling.collections.Delta.HasDeltas;
import com.directmodelling.collections.List.ListReplace;

public interface List<S> extends HasDeltas<ListReplace<S>>, java.util.List<S>
/* , RCollection<S> */{

	public static final class ListReplace<T> extends Delta<List<T>> {
		/** new elements */
		public final Object elements[];
		/** replaced range */
		public final int from, to;

		public ListReplace(final List container, final int from, final int to,
				final Object... elements) {
			super(container);
			this.from = from;
			this.to = to;
			this.elements = elements;
		}

		@Override
		public ListReplace<T> getNext() {
			@SuppressWarnings("unchecked")
			final ListReplace<T> next = (ListReplace<T>) super.getNext();
			return next;
		}
	}

	public static final class Singleton<T> extends AbstractList<T> implements
			List<T>, Serializable {
		private final T t;

		public Singleton(final T t) {
			this.t = t;
		}

		@Override
		public ListReplace<T> getLastDelta() {
			return null;
		}

		@Override
		public T get(final int index) {
			assert index == 0;
			return t;
		}

		@Override
		public int size() {
			return 1;
		}

	}

	// class Replace<T> extends CollectionDelta<T> {
	// public Replace(List<T> container, int from, int to, T... elements) {
	// super(container);
	// this.from = from;
	// this.to = to;
	// this.elements = elements;
	// }
	// /** replaced range */
	// int from, to;
	// /** new elements */
	// T elements[];
	// }

	// class Permute extends Delta {
	// /** start of permuted range, end given by array length */
	// int from;
	// /** after permutation element_after[indexes[n-from]] = element_before[n]
	// */
	// int indexes[];
	// }
}