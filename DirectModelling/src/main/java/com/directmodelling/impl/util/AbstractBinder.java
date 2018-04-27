package com.directmodelling.impl.util;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;

/**
 * Base for all kinds of binders.
 * 
 * @param <V>
 *            type of Value
 * @param <W>
 *            type of widget content
 */
public abstract class AbstractBinder<V, W> implements Receiver {
	/** read-only reference, always set */
	protected final Value<V> val;
	/** mutable reference, null when Value not mutable */
	protected final Mutable<V> var;
	protected final Converter<V, W> toWidget;
	protected final Converter<W, V> toVar;

	public AbstractBinder(Value<V> val, Converter<V, W> toWidget, Converter<W, V> toVar) {
		this.val = val;
		var = val instanceof Mutable ? (Mutable<V>) val : null;
		this.toWidget = toWidget;
		this.toVar = toVar;
	}

}