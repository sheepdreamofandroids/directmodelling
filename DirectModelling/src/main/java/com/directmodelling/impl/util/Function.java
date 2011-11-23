package com.directmodelling.impl.util;

public interface Function<In, Out> {
	Out apply(In in);
}