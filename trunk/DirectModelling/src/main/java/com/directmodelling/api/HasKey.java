package com.directmodelling.api;

import java.util.HashMap;
import java.util.Map;

import com.directmodelling.impl.Applicable;

public interface HasKey {
	String getKey();

	public class Registry {
		private static final Map<Object, String> names = new HashMap<Object, String>();

		public static void register(Object o, String name) {
			names.put(o, name);
		}

		public static String get(Object o) {
			return names.get(o);
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