package com.directmodelling.swing.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;

public abstract class AbstractBinder<TVal, TWidget> implements Receiver {

	protected final Value<TVal> val;
	protected final Mutable<TVal> var;
	protected final Converter<? super TVal, ? extends TWidget> toWidget;
	protected final Converter<? super TWidget, ? extends TVal> toVar;
	public Exception lastConversionError;
	private static final Logger log = LoggerFactory.getLogger(AbstractBinder.class);
	{
		Updates.registerForChanges(this);
	}

	@SuppressWarnings("unchecked")
	public AbstractBinder(final Value<TVal> val, final Converter<? super TVal, ? extends TWidget> toWidget,
			final Converter<? super TWidget, ? extends TVal> toVar) {
		this.val = val;
		var = val instanceof Mutable ? (Mutable<TVal>) val : null;
		this.toWidget = (Converter<? super TVal, ? extends TWidget>) (toWidget == null ? Converter.Util.id() : toWidget);
		this.toVar = (Converter<? super TWidget, TVal>) (toVar == null ? Converter.Util.id() : toVar);
		Updates.aValueChanged(null);
	}

	/** Will be called whenever the variable changed with the converted value. */
	protected abstract void setWidgetValue(TWidget v);

	/** Will be called to retrieve the value the user has set. */
	protected abstract TWidget getWidgetValue();

	/**
	 * Install a listener in your constructor and call this whenever the widget
	 * changed. It's up to you how often this gets called. E.g. for a textfield
	 * this might be called on every keystroke or only when it loses focus.
	 */
	protected void widgetChanged() {
		if (var != null) {
			final TWidget value = getWidgetValue();
			TVal converted;
			try {
				converted = toVar.convert(value);
			} catch (final Exception e) {
				lastConversionError = e;
				log.debug("Cannot convert value " + value + " for widget ");
				return;
			}
			var.setValue(converted);
		}
	}

	@Override
	public void valuesChanged() {
		setWidgetValue(toWidget.convert(val.getValue()));
	}
}