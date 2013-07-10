package com.directmodelling.swing.binding;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Value;

public class ToggleBinder<TVal> extends AbstractBinder<TVal, Boolean> implements ItemListener {
	private final AbstractButton c;
	AbstractButton b;

	public ToggleBinder(final Value<TVal> val, final Converter<? super TVal, Boolean> toWidget,
			final Converter<Boolean, ? extends TVal> toVar, final AbstractButton c) {
		super(val, toWidget, toVar);
		c.addItemListener(this);
		this.c = c;
		b = c;
	}

	@Override
	public void itemStateChanged(final ItemEvent itemEvent) {
		widgetChanged();
	}

	@Override
	protected void setWidgetValue(final Boolean v) {
		b.setSelected(v);
	}

	@Override
	protected Boolean getWidgetValue() {
		return c.isSelected();
	}
}