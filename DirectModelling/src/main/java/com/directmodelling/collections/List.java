package com.directmodelling.collections;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;

import com.directmodelling.collections.List.ListReplace;

public interface List<Element> extends HasDeltas<ListReplace<Element>, java.util.List<Element>>, java.util.List<Element>
/* , RCollection<S> */ {

	public static final class ListReplace<Element>
			extends HasDeltas.Delta<java.util.List<Element>, ListReplace<Element>> {
		/** new elements */
		public final Element elements[];
		/** replaced range */
		public final int from, to;

		public ListReplace(final DeltaTracker<ListReplace<Element>, ?, ?> container, final int from, final int to,
				@SuppressWarnings("unchecked") final Element... elements) {
			super(container);
			this.from = from;
			this.to = to;
			this.elements = elements;
		}

		// protected ListReplace() {
		// from = 0;
		// to = 0;
		// elements = null;
		// }

		@Override
		protected void apply(java.util.List<Element> l) {
			if (from != to)
				l.subList(from, to).clear();
			l.addAll(from, Arrays.asList(elements));
		}
	}

	@SuppressWarnings("serial")
	public static final class Singleton<T> extends AbstractList<T> implements List<T>, Serializable {
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