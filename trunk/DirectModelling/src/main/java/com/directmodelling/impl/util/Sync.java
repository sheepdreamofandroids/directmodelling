package com.directmodelling.impl.util;

import java.util.Iterator;

import com.google.common.base.Function;

/**
 * Embodies a simple algorithm for syncing a mapping from an In iterable to some
 * Out collection. Implement {@link #apply(In, Material)} to define the mapping
 * and {@link #append(In, Out)} for appending to Out container.
 */
public final class Sync<In, Out> {
	public interface Appendable<In, Out> {
		/**
		 * Appends out to whatever 'this' is.
		 * 
		 * @param in
		 *            value that out has been derived from
		 * @param out
		 *            element to append
		 */
		void append(final In in, final Out out);

	}

	public static <In, Out> void sync(final Iterator<In> ins,
			final Iterator<Out> outs, final Function<In, Out> f,
			final Appendable<In, Out> append) {
		while (ins.hasNext() && outs.hasNext()) {
			In in = ins.next();
			final Out out = f.apply(in);
			while (outs.hasNext() && outs.next() != out)
				outs.remove();
			if (!outs.hasNext()) {
				append.append(in, out);
				while (ins.hasNext()) {
					in = ins.next();
					append.append(in, f.apply(in)); // end of panel
				}
				return;
			}
		}

		// remove superfluous children
		while (outs.hasNext()) {
			outs.next();
			outs.remove();
		}

		while (ins.hasNext()) {
			final In in = ins.next();
			append.append(in, f.apply(in)); // end of panel
		}

	}
}