package com.directmodelling.collections.operators;

import java.io.Serializable;
import java.util.Collection;

import com.directmodelling.collections.List;
import com.directmodelling.collections.List.ListReplace;

/**
 * Simple baseclass for implementing readonly {@link com.directmodelling.collections.List}s. It throws an
 * {@link UnsupportedOperationException} for each method that tries to modify it. Its contents can actually change, just
 * not by calling mutators on this instance.
 */
@SuppressWarnings("serial")
public abstract class AbstractReadonlyList<Element> extends // ListRecorder<Element>
		AbstractReadonlyCollection<Element, AbstractReadonlyList<Element>, java.util.List<Element>, ListReplace<Element>>
		implements List<Element>, Serializable
{

	@Override
	public void add(final int index, final Element element) {
		throw immutable();
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends Element> c) {
		throw immutable();
	}

	@Override
	public Element remove(final int index) {
		throw immutable();
	}

	@Override
	public Element set(final int index, final Element element) {
		throw immutable();
	}

	@Override
	protected ListReplace<Element> createSentinel() {
		return new ListReplace<>(this, 0, 0);
	}

}
