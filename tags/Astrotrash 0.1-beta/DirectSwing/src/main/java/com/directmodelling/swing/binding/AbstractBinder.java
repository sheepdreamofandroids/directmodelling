package com.directmodelling.swing.binding;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;

public abstract class AbstractBinder<T> implements Receiver {

	protected final Value<T> val;
	protected final Mutable<T> var;
	protected final Converter<T, Integer> toWidget;
	protected final Converter<Integer, T> toVar;

	public AbstractBinder(Value<T> val, Converter<T, Integer> toWidget, Converter<Integer, T> toVar) {
		this.val = val;
		var = val instanceof Mutable ? (Mutable) val : null;
		this.toWidget = toWidget;
		this.toVar = toVar;
	}

}