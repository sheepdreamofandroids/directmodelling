package com.directmodelling.android.storage;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import com.directmodelling.api.HasKey;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.api.Value.Type;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Storage.AbstractStorage;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PreferencesStorage extends AbstractStorage implements Storage, OnSharedPreferenceChangeListener {
	private final SharedPreferences preferences;
	// @Inject
	// Application application;
	// private Editor editor;
	// TODO check whether using editedValues makes reading significantly faster
	// private final Map<Value<?>, Object> editedValues = new HashMap<Value<?>,
	// Object>();
	private final Map<String, Value<?>> boundVars = new HashMap<String, Value<?>>();

	@Inject
	public PreferencesStorage(Context context) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		preferences.registerOnSharedPreferenceChangeListener(this);
	}

	// public PreferencesStorage(SharedPreferences preferences) {
	// super();
	// this.preferences = preferences;
	// preferences.registerOnSharedPreferenceChangeListener(this);
	// }

	@SuppressWarnings("unchecked")
	public <T> T get(Value<T> v) {
		// Object o = editedValues.get(v);
		// if (null != o)
		// return (T) o;

		final String key = ((HasKey) v).getKey();
		T defaultValue = HasDefaultValue.Registry.getDefaultValue(v);
		switch (v.type()) {
		case tBoolean:
			return (T) (Boolean) getPreferences().getBoolean(key, Boolean.TRUE.equals(defaultValue));
		case tByte:
			return (T) (Byte) (byte) getPreferences().getInt(key, ((Number) defaultValue).byteValue());
		case tShort:
			return (T) (Short) (short) getPreferences().getInt(key, ((Number) defaultValue).shortValue());
		case tInteger:
			return (T) (Integer) getPreferences().getInt(key, ((Number) defaultValue).intValue());
		case tLong:
			return (T) (Long) getPreferences().getLong(key, ((Number) defaultValue).longValue());
		case tCharacter:
			return (T) String.valueOf(getPreferences().getString(key, String.valueOf(defaultValue)).charAt(0));
		case tFloat:
			return (T) (Float) getPreferences().getFloat(key, ((Number) defaultValue).floatValue());
		case tDouble:
			return (T) (Double) (double) getPreferences().getFloat(key, ((Number) defaultValue).floatValue());
		case tObject:
			throw new RuntimeException("Cannot retrieve an object from Android SharedPreferences");
		default:
			return null;
		}
		// throw new
		// RuntimeException("The previous switch statement should be complete");
	}

	@Override
	public <T> void set(Mutable<T> v, T val) {
		// editedValues.put(v, val);
		final String key = ((HasKey) v).getKey();
		final Type type = v.type();
		Editor editor = preferences.edit();
		write(val, key, type, editor);
		editor.commit();
		// throw new
		// RuntimeException("The previous switch statement should be complete");
	}

	private <T> void write(T val, final String key, final Type type, Editor editor) {
		switch (type) {
		case tBoolean:
			editor.putBoolean(key, (Boolean) val);
			return;
		case tByte:
			editor.putInt(key, ((Number) val).intValue());
			return;
		case tShort:
			editor.putInt(key, ((Number) val).intValue());
			return;
		case tInteger:
			editor.putInt(key, ((Number) val).intValue());
			return;
		case tLong:
			editor.putLong(key, ((Number) val).longValue());
			return;
		case tCharacter:
			editor.putString(key, String.valueOf(val));
			return;
		case tFloat:
			editor.putFloat(key, (Float) val);
			return;
		case tDouble:
			editor.putFloat(key, ((Number) val).floatValue());
			return;
		case tObject:
			throw new RuntimeException("Cannot store an object in Android SharedPreferences");
			// default:
			// throw new
			// RuntimeException("Value.type() returned the illegal value: " +
			// type);
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
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

	@Override
	protected void bindProperty(Value<?> value) {
		boundVars.put(HasKey.Registry.get(value), value);
	}
}
