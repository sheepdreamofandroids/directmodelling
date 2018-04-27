package com.directmodelling.collections.operators;

import java.util.Set;

import com.directmodelling.collections.HasDeltas.Delta;
import com.directmodelling.collections.HasDeltas.DeltaTracker;

public class SetDelta<Element> extends Delta<java.util.Set<Element>, SetDelta<Element>> {

	final Element element;
	final boolean add;

	public SetDelta(SetTracker<Element> container, Element element, boolean add) {
		super(container);
		this.element = element;
		this.add = add;
	}
	
	public static class SetTracker<Element>
			extends DeltaTracker<SetDelta<Element>, java.util.Set<Element>, SetTracker<Element>> {

		@Override
		protected SetDelta<Element> createSentinel() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	protected void apply(Set<Element> c) {
		if (add)
			c.add(element);
		else
			c.remove(element);
	}

}