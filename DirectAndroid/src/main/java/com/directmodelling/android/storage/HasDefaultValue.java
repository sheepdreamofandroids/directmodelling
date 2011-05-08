package com.directmodelling.android.storage;

import java.util.HashMap;
import java.util.Map;

import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.impl.Applicable;

public interface HasDefaultValue<T> {
	T getDefaultValue();

	public static class Registry {
		private static final Map<Value<?>, Object> defaults = new HashMap<Value<?>, Object>();

		public static <S> void setDefaultValue(Value<S> v, S dflt) {
			defaults.put(v, dflt);
		}

		@SuppressWarnings("unchecked")
		public static <S> S getDefaultValue(Value<S> v) {
			return (S) defaults.get(v);
		}

		@SuppressWarnings("unchecked")
		public static <S> Applicable<Value.Mutable<? super S>> defaultValue(final S dflt) {
			return new Applicable<Value.Mutable<? super S>>() {
				@Override
				public void applyTo(Mutable<? super S> t) {
					// TODO Auto-generated method stub
					setDefaultValue(t, dflt);
				}
			};
		}
	}
}