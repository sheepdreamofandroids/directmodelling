package com.directmodelling.api;

import com.directmodelling.impl.Applicable;

public interface HasKey {
	String getKey();

	public class Registry {
		public static <T> T register(T o, String name) {
			return o;
		}

		public static String get(Object o) {
			return null;
		}

		public static Applicable<Object> named(final String name) {
			return new Applicable<Object>() {
				@Override
				public void applyTo(Object t) {
					register(t, name);
				}
			};
		}
	}
}