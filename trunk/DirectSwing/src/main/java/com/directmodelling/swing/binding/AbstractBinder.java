package com.directmodelling.swing.binding;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;

public abstract class AbstractBinder<TVal, TWidget> implements Receiver {

	protected final Value<TVal> val;
	protected final Mutable<TVal> var;
	protected final Converter<TVal, ? extends TWidget> toWidget;
	protected final Converter<? extends TWidget, TVal> toVar;

	@SuppressWarnings("unchecked")
	public AbstractBinder(final Value<TVal> val,
			final Converter<TVal, ? extends TWidget> toWidget,
			final Converter<? extends TWidget, TVal> toVar) {
		this.val = val;
		var = val instanceof Mutable ? (Mutable<TVal>) val : null;
		this.toWidget = (toWidget == null ? (Converter<TVal, ? extends TWidget>) Converter.Util
				.id() : toWidget);
		this.toVar = (Converter<? extends TWidget, TVal>) (toVar == null ? Converter.Util
				.id() : toVar);
	}

}