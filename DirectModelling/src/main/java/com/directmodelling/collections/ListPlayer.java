package com.directmodelling.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.directmodelling.collections.operators.AbstractReadonlyList;

@SuppressWarnings("serial")
public class ListPlayer<Element> extends AbstractReadonlyListWrapper<Element> {
	private ListReplace<Element> sentinel;

	public ListPlayer(AbstractReadonlyList<Element> sentinel) {
		this(new ArrayList<Element>(), sentinel);
	}

	public ListPlayer(List<Element> delegate, AbstractReadonlyList<Element> sentinel) {
		super(delegate);
		this.sentinel = sentinel.getLastDelta();
		delegate.addAll(sentinel);
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
