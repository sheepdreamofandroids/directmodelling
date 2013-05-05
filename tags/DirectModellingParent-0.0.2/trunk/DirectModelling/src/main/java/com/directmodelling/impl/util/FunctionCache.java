package com.directmodelling.impl.util;

import java.util.Map;
import java.util.WeakHashMap;

public class FunctionCache<In, Out> implements Function<In, Out> {
	private static final Object NULL = new Object();
	private final Function<In, Out> f;
	private final Map<In, Out> previousResults = new WeakHashMap<In, Out>();

	public FunctionCache(final Function<In, Out> f) {
		this.f = f;
	}

	@Override
	public Out apply(final In in) {
		Out out = previousResults.get(in);
		if (out == NULL)
			return null;
		if (out == null) {
			out = f.apply(in);
			previousResults.put(in, out);
		}
		return out;
	}
}