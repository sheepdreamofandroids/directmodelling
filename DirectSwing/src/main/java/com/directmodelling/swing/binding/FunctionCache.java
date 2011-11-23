package com.directmodelling.swing.binding;

import java.util.WeakHashMap;

import com.directmodelling.impl.util.Function;

public class FunctionCache<In, Out> implements Function<In, Out> {

	private final WeakHashMap<In, Out> cache = new WeakHashMap<In, Out>();
	Function<In, Out> fun;

	public FunctionCache(final Function<In, Out> fun) {
		super();
		this.fun = fun;
	}

	@Override
	public Out apply(final In in) {
		Out out = cache.get(in);
		if (null == out) {
			out = fun.apply(in);
			cache.put(in, out);
		}
		return out;
	}

}