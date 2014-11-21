package com.directmodelling.collections;

import com.directmodelling.collections.Delta.HasDeltas;

public interface Map<K, V> extends java.util.Map<K, V>,
		HasDeltas<Map.MapReplace<K, V>> {

	public static class MapReplace<K, V> extends Delta<Map<K, V>> {
		/**
		 * key==REMOVE means clear map. Could be anything when value==REMOVE,
		 * otherwise a K.
		 */
		public final Object key;
		/** When ==REMOVE, then removal otherwise addition/overwrite. */
		public final V value;
		/** Marker for value to designate removals. */
		public static final Object REMOVE = new Object();

		/** Create a remove delta */
		public MapReplace(final Map<K, V> container, final Object key) {
			super(container);
			this.key = key;
			@SuppressWarnings("unchecked")
			final V remove = (V) REMOVE;
			this.value = remove;
		}

		/** create a put delta */
		public MapReplace(final Map<K, V> container, final K key, final V value) {
			super(container);
			this.key = key;
			this.value = value;
		}
	}

}