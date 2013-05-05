package com.directmodelling.gwt;

import java.util.HashMap;

import com.directmodelling.impl.util.Function;

public class FunctionCache<In, Out> implements Function<In, Out> {

	private final HashMap<In, Out> cache = new HashMap<In, Out>();
	Function<In, Out> fun;

	public FunctionCache(final Function<In, Out> fun) {
		super();
		this.fun = fun;
	}

	public Out apply(final In in) {
		Out out = cache.get(in);
		if (null == out) {
			out = fun.apply(in);
			cache.put(in, out);
		}
		return out;
	}

}