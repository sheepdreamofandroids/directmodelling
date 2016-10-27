package com.directmodelling.collections;

public interface Map<K, V> extends java.util.Map<K, V>,
		HasDeltas<Map.MapReplace<K, V>, Map<K, V>> {

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
	public static class MapReplace<K, V> extends com.directmodelling.collections.HasDeltas.Delta<Map<K, V>> {
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
		public MapReplace(final DeltaTracker<MapReplace<K, V>, Map<K, V>> container) {
			this(container, (K) REMOVE, null);
		}

		/** Create a remove delta */
		public MapReplace(final DeltaTracker<MapReplace<K, V>, Map<K, V>> container, final Object key) {
			this(container, (K) key, (V) REMOVE);
		}

		/** create a put delta */
		public MapReplace(final DeltaTracker<MapReplace<K, V>, Map<K, V>> container, final K key, final V value) {
			super(container);
			this.key = key;
			this.value = value;
		}

		@Override
		void apply(Map<K, V> c) {
			// TODO Auto-generated method stub

		}
	}

}