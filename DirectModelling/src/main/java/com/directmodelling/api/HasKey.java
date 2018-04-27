package com.directmodelling.api;

import java.util.HashMap;
import java.util.Map;

import com.directmodelling.impl.Applicable;

public interface HasKey {
	String getKey();

	public class Registry {
		public static final Map<Object, String> names = new HashMap<Object, String>();

		public static void register(final Object o, final String name) {
			System.out.println("Registering name '" + name + "' with object [" + o + "]");
			names.put(o, name);
		}

		public static String get(final Object o) {
			return names.get(o);
		}

		public static Applicable<Object> named(final String name) {
			return new Applicable<Object>() {
				@Override
				public void applyTo(final Object t) {
					register(t, name);
				}
			};
		}
	}
}
