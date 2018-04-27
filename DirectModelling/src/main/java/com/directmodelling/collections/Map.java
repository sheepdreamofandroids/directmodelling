package com.directmodelling.collections;

public interface Map<K, V> extends java.util.Map<K, V>,
		HasDeltas<Map.MapReplace<K, V>, java.util.Map<K, V>> {

	/**
	 * Records a change in a map. <br>
	 * key instanceOf K, value instanceOf V -> insert/replace value under key key instanceOf Object, value equals REMOVE
	 * -> remove key with old value key equals REMOVE -> clear map
	 * 
	 * @author guus
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class MapReplace<K, V> extends HasDeltas.Delta<java.util.Map<K, V>, MapReplace<K, V>> {
		/**
		 * key==REMOVE means clear map. Could be anything when value==REMOVE,
		 * otherwise a K.
		 */
		public final K key;
		/** When ==REMOVE, then removal otherwise addition/overwrite. */
		public final V value;
		/** Marker for value to designate removals. */
		public static final Object REMOVE = new Object();

		/** Create a remove delta */
		public MapReplace(final DeltaTracker<MapReplace<K, V>, ?, ?> container) {
			this(container, (K) REMOVE, null);
		}

		/** Create a remove delta */
		public MapReplace(final DeltaTracker<MapReplace<K, V>, ?, ?> container, final K key) {
			this(container, key, (V) REMOVE);
		}

		/** create a put delta */
		public MapReplace(final DeltaTracker<MapReplace<K, V>, ?, ?> container, final K key, final V value) {
			super(container);
			this.key = key;
			this.value = value;
		}

		@Override
		protected
				void apply(java.util.Map<K, V> c) {
			if (key == REMOVE)
				c.clear();
			else if (value == REMOVE)
				c.remove(key);
			else
				c.put(key, value);
		}
	}

}