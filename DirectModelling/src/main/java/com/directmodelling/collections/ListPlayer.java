package com.directmodelling.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.directmodelling.collections.operators.AbstractReadonlyList;
import com.google.common.collect.ImmutableList;

@SuppressWarnings("serial")
public class ListPlayer<Element> extends AbstractReadonlyListWrapper<Element> {
	private ListReplace<Element> sentinel;

	public ListPlayer(HasListDeltas<Element> sentinel) {
		this(new ArrayList<Element>(), sentinel);
	}

	public ListPlayer(List<Element> delegate, HasListDeltas<Element> sentinel) {
		super(delegate);
		this.sentinel = sentinel.getLastDelta();
	}

	@Override
	protected void updateDelegate() {
		ListReplace<Element> next;
		while ((next = sentinel.getNext()) != null) {
			if (next.from != next.to)
				delegate.subList(next.from, next.to).clear();
			delegate.addAll(next.from, Arrays.asList(next.elements));
		}
	}

}
