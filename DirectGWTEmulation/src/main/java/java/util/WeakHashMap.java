package java.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Faux, non-weak hash map. Should be replaced with a wrapper for a WeakMap shim
 * or the real thing.
 * 
 * @see http://stackoverflow.com/questions/18093753/creating-weakmap-wrapper-
 *      implementation-in-gwt-getting-errors
 * @see https://code.google.com/p/es-lab/source/browse/trunk/src/ses/WeakMap.js
 * */
public class WeakHashMap<K, V> extends HashMap<K, V> {

	public WeakHashMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeakHashMap(final int initialCapacity, final float loadFactor) {
		super(initialCapacity, loadFactor);
		// TODO Auto-generated constructor stub
	}

	public WeakHashMap(final int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	public WeakHashMap(final Map<? extends K, ? extends V> m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

}
