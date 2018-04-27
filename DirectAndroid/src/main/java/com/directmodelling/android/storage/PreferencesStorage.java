package com.directmodelling.android.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import com.directmodelling.api.HasKey;
import com.directmodelling.api.ID;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Value;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Storage.AbstractStorage;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PreferencesStorage extends AbstractStorage implements Storage,
		OnSharedPreferenceChangeListener {
	private final SharedPreferences preferences;
	// @Inject
	// Application application;
	// private Editor editor;
	// TODO check whether using editedValues makes reading significantly faster
	// private final Map<Value<?>, Object> editedValues = new HashMap<Value<?>,
	// Object>();
	private final Map<String, Value<?>> boundVars = new HashMap<String, Value<?>>();

	@Inject
	public PreferencesStorage(final Context context) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		preferences.registerOnSharedPreferenceChangeListener(this);
	}

	// public PreferencesStorage(SharedPreferences preferences) {
	// super();
	// this.preferences = preferences;
	// preferences.registerOnSharedPreferenceChangeListener(this);
	// }

	@SuppressWarnings("unchecked")
	public <T> T get(final Value<T> v) {
		// Object o = editedValues.get(v);
		// if (null != o)
		// return (T) o;

		final String key = ((HasKey) v).getKey();
		final T defaultValue = HasDefaultValue.Registry.getDefaultValue(v);
		Class type = typeOf(v);
		if (type == Boolean.TYPE || type == Boolean.class)
			return (T) (Boolean) getPreferences().getBoolean(key,
					Boolean.TRUE.equals(defaultValue));
		else if (type == Byte.TYPE || type == Byte.class)
			return (T) (Byte) (byte) getPreferences().getInt(key,
					((Number) defaultValue).byteValue());
		else if (type == Short.TYPE || type == Short.class)
			return (T) (Short) (short) getPreferences().getInt(key,
					((Number) defaultValue).shortValue());
		else if (type == Integer.TYPE || type == Integer.class)
			return (T) (Integer) getPreferences().getInt(key,
					((Number) defaultValue).intValue());
		else if (type == Long.TYPE || type == Long.class)
			return (T) (Long) getPreferences().getLong(key,
					((Number) defaultValue).longValue());
		else if (type == String.class)
			return (T) String.valueOf(getPreferences().getString(key,
					String.valueOf(defaultValue)).charAt(0));
		else if (type == Float.TYPE || type == Float.class)
			return (T) (Float) getPreferences().getFloat(key,
					((Number) defaultValue).floatValue());
		else if (type == Double.TYPE || type == Double.class)
			return (T) (Double) (double) getPreferences().getFloat(key,
					((Number) defaultValue).floatValue());
		else
			throw new RuntimeException(
					"Cannot retrieve an object from Android SharedPreferences");
		// throw new
		// RuntimeException("The previous switch statement should be complete");
	}

	@Override
	public <T> void set(final ID v, final T val) {
		// editedValues.put(v, val);
		final String key = ((HasKey) v).getKey();
		// final Type type = v.type();
		final Editor editor = preferences.edit();
		write(val, key, editor);
		editor.commit();
		// throw new
		// RuntimeException("The previous switch statement should be complete");
	}

	private Class typeOf(Value<?> v) {
		return com.directmodelling.reflective.Util.typeArgument(v.getClass(),
				Value.class, 0);
	}

	private <T> void write(final T val, final String key, final Editor editor) {
		Class type = val.getClass();
		if (type == Boolean.TYPE || type == Boolean.class)
			editor.putBoolean(key, (Boolean) val);
		else if (type == Byte.TYPE || type == Byte.class || type == Short.TYPE
				|| type == Short.class || type == Integer.TYPE
				|| type == Integer.class)
			editor.putInt(key, ((Number) val).intValue());
		else if (type == Long.TYPE || type == Long.class)
			editor.putLong(key, ((Number) val).longValue());
		else if (type == Float.TYPE || type == Float.class//
				|| type == Double.TYPE || type == Double.class)
			editor.putFloat(key, (Float) val);
		else
			editor.putString(key, String.valueOf(val));
		// case tObject:
		// throw new RuntimeException(
		// "Cannot store an object in Android SharedPreferences");
		// default:
		// throw new
		// RuntimeException("Value.type() returned the illegal value: " +
		// type);
	}

	@Override
	public void onSharedPreferenceChanged(
			final SharedPreferences sharedPreferences, final String key) {
		final Value<?> value = boundVars.get(key);
		if (null != value) {
			// editedValues.remove(value);// re-fetch from prefs
			Updates.aValueChanged(value);
		}
	}

	private SharedPreferences getPreferences() {
		if (null == preferences) {
			// preferences =
			// PreferenceManager.getDefaultSharedPreferences(application);
			// application.getpr getSharedPreferences("prefs",
			// Application.MODE_PRIVATE);
			preferences.registerOnSharedPreferenceChangeListener(this);
		}

		return preferences;
	}

	public void startEdit() {// TODO should be generic 'Transaction' API
		// editor = getPreferences().edit();
	}

	public void stopEdit() {
		// editor.commit();
	}

	// @Override
	// public void bindProperty(final Value<?> value) {
	// boundVars.put(HasKey.Registry.get(value), value);
	// }

	/** Write data to preferences. */
	public void store() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addValues(final Map<ID, Object> values) {
		final Editor editor = preferences.edit();
		for (final Entry<ID, Object> entry : values.entrySet()) {
			final ID v = entry.getKey();
			final Object val = entry.getValue();
			final String key = ((HasKey) v).getKey();
			write(val, key, editor);
		}
		editor.commit();
	}

	@Override
	public <T> T get(ID id) {
		// TODO Auto-generated method stub
		return null;
	}
}
