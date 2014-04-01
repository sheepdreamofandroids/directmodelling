package com.directmodelling.gwt;

import java.util.Iterator;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.impl.util.FunctionCache;
import com.google.common.base.Function;

public abstract class IteratorBinding<In, Out> implements Receiver,
		Function<In, Out> {

	protected final Iterable<Out> container;
	protected final Value<? extends Iterable<In>> values;
	protected final Function<In, Out> factory;

	public IteratorBinding(final Iterable<Out> container,
			final Value<? extends Iterable<In>> values,
			final Function<In, Out> factory) {
		this.container = container;
		this.values = values;
		this.factory = new FunctionCache<In, Out>(factory);
		Updates.registerForChanges(this);
	}

	public IteratorBinding(final Iterable<Out> container,
			final Value<? extends Iterable<In>> values) {
		this.container = container;
		this.values = values;
		this.factory = new FunctionCache<In, Out>(this);
		Updates.registerForChanges(this);
	}

	//
	// public static <In, Out> IteratorBinding<In, Out> bind(
	// final Iterable<Out> container,
	// final Value<? extends Iterable<In>> values,
	// final Function<In, Out> factory) {
	// return new IteratorBinding<In, Out>(container, values) {
	//
	// @Override
	// public Out apply(final In input) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// protected void addWidget(final Out newWidget, final In val) {
	// // TODO Auto-generated method stub
	//
	// }
	// };
	// }

	@Override
	public void valuesChanged() {
		final Iterator<Out> cont = container.iterator();
		final Iterator<In> vals = values.getValue().iterator();

		while (vals.hasNext() && cont.hasNext()) {
			In val = vals.next();
			final Out newWidget = factory.apply(val);
			while (cont.hasNext() && cont.next() != newWidget)
				cont.remove();
			if (!cont.hasNext()) {
				addWidget(newWidget, val);
				while (vals.hasNext()) {
					val = vals.next();
					addWidget(factory.apply(val), val); // end of panel
				}
				return;
			}
		}

		// remove superfluous children
		while (cont.hasNext()) {
			cont.next();
			cont.remove();
		}

		while (vals.hasNext()) {
			final In val = vals.next();
			addWidget(factory.apply(val), val); // end of panel
		}
	}

	protected abstract void addWidget(final Out newWidget, final In val);

}