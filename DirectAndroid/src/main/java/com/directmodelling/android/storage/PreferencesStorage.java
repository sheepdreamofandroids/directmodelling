package com.directmodelling.android.storage;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.directmodelling.api.HasKey;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Storage.AbstractStorage;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PreferencesStorage extends AbstractStorage implements Storage, OnSharedPreferenceChangeListener {
	private SharedPreferences preferences;
	@Inject
	Application application;

	// public PreferencesStorage(SharedPreferences preferences) {
	// super();
	// this.preferences = preferences;
	// preferences.registerOnSharedPreferenceChangeListener(this);
	// }

	@SuppressWarnings("unchecked")
	public <T> T get(Value<T> v) {
		final String key = ((HasKey) v).getKey();
		switch (v.type()) {
		case tBoolean:
			return (T) (Boolean) getPreferences().getBoolean(key, true);
		case tByte:
			return (T) (Byte) (byte) getPreferences().getInt(key, 0);
		case tShort:
			return (T) (Short) (short) getPreferences().getInt(key, 0);
		case tInteger:
			return (T) (Integer) getPreferences().getInt(key, 0);
		case tLong:
			return (T) (Long) getPreferences().getLong(key, 0);
		case tCharacter:
			return (T) String.valueOf(getPreferences().getString(key, " ").charAt(0));
		case tFloat:
			return (T) (Float) getPreferences().getFloat(key, 0);
		case tDouble:
			return (T) (Double) (double) getPreferences().getFloat(key, 0);
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
		final String key = ((HasKey) v).getKey();
		switch (v.type()) {
		case tBoolean:
			getPreferences().edit().putBoolean(key, (Boolean) val);
			break;
		case tByte:
			getPreferences().edit().putInt(key, ((Number) val).intValue());
			break;
		case tShort:
			getPreferences().edit().putInt(key, ((Number) val).intValue());
			break;
		case tInteger:
			getPreferences().edit().putInt(key, ((Number) val).intValue());
			break;
		case tLong:
			getPreferences().edit().putLong(key, ((Number) val).longValue());
			break;
		case tCharacter:
			getPreferences().edit().putString(key, String.valueOf(val));
			break;
		case tFloat:
			getPreferences().edit().putFloat(key, (Float) val);
			break;
		case tDouble:
			getPreferences().edit().putFloat(key, ((Number) val).floatValue());
			break;
		case tObject:
			throw new RuntimeException("Cannot store an object in Android SharedPreferences");
		}
		throw new RuntimeException("The previous switch statement should be complete");
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		getPreferences().getAll();
	}

	private SharedPreferences getPreferences() {
		if (null == preferences)
			preferences = application.getSharedPreferences("prefs", Application.MODE_PRIVATE);
		return preferences;
	}
}
