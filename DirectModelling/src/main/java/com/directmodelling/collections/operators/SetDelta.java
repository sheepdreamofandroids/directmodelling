package com.directmodelling.collections.operators;

import com.directmodelling.collections.Delta;
import com.directmodelling.collections.Set;

public class SetDelta<Element> extends Delta<Set<Element>, SetDelta<Element>> {

	final Element element;
	final boolean add;

	public SetDelta(DeltaTracker<SetDelta<Element>> container, Element element, boolean add) {
		super(container);
		this.element = element;
		this.add = add;
	}
	
}