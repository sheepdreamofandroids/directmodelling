package com.directmodelling.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.directmodelling.collections.Delta.DeltaTracker;

public class MapRecorder<K, V> extends DeltaTracker<Map.MapReplace<K, V>>
		implements Map<K, V> {
	private final java.util.Map<K, V> delegate;

	public MapRecorder() {
		this(new HashMap<K, V>());
	}

	public MapRecorder(final java.util.Map<K, V> delegate) {
		this.delegate = delegate;
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public boolean containsKey(final Object key) {
		return delegate.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		return delegate.containsValue(value);
	}

	@Override
	public V get(final Object key) {
		return delegate.get(key);
	}

	@Override
	public V put(final K key, final V value) {
		new MapReplace<K, V>(this, key, value);
		return delegate.put(key, value);
	}

	@Override
	public V remove(final Object key) {
		new MapReplace<K, V>(this, key);
		return delegate.remove(key);
	}

	@Override
	public void putAll(final java.util.Map<? extends K, ? extends V> m) {
		final Set<Entry<K, V>> entrySet = (Set) m.entrySet();
		for (final Entry<K, V> entry : entrySet) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapReplace<K, V> getLastDelta() {
		// TODO Auto-generated method stub
		return null;
	}

}
