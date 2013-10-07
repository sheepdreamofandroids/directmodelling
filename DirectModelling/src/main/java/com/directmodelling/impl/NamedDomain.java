package com.directmodelling.impl;

import java.util.HashMap;
import java.util.Map;

/** Describes a set of values that have unique names. */
public interface NamedDomain<T> {
	/** get name of value */
	String nameOf(T t);

	/** find value for name */
	T valueOf(String name);

	/** Utility for creating a name => value mapping. */
	public static class Name2Value<T> {
		private final Map<String, T> map = new HashMap<String, T>();

		public Name2Value(final NamedDomain<T> nd, final T... values) {
			for (final T t : values) {
				map.put(nd.nameOf(t), t);
			}
		}

		public T valueOf(final String name) {
			return map.get(name);
		}
	}
}
