package com.directmodelling.gwt;

import java.util.Iterator;
import java.util.List;

import com.directmodelling.api.EnumerableValue;
import com.directmodelling.api.EnumerableValue.Mutable;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.impl.util.FunctionCache;
import com.directmodelling.impl.util.Sync;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public abstract class BoundOptions<T> extends Widget implements Receiver,
		Function<T, Widget>, Sync.Appendable<T, Widget> {
	protected Mutable<T> var;
	private HasWidgets container;
	private final FunctionCache<T, Widget> cache = new FunctionCache<T, Widget>(
			this);

	{
		setElement(Document.get().createDivElement());
	}

	public void setVar(final EnumerableValue.Mutable<T> var) {
		this.var = var;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		final Widget wrappee = getParent();
		if (wrappee instanceof HasWidgets) {
			container = (HasWidgets) wrappee;
			container.remove(this);
		} else
			throw new RuntimeException("Must wrap HasWidgets");
		Updates.registerForChanges(this);
	}

	@Override
	protected void onUnload() {
		Updates.unregister(this);
		super.onUnload();
	}

	@Override
	public void valuesChanged() {
		final Iterable<T> allPotentialValues = options();
		Sync.sync(allPotentialValues.iterator(), container.iterator(), cache,
				this);
		Iterator<T> it;
		if (!Iterables.contains(allPotentialValues, var.get())) {
			it = allPotentialValues.iterator();
			var.set(it.hasNext() ? it.next() : null);
		}
	}

	protected List<T> options() {
		return var.allPotentialValues();
	}

	@Override
	public void append(final T in, final Widget out) {
		container.add(out);
	}
}
