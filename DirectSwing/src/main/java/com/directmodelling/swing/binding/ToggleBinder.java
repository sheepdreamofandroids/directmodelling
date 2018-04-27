package com.directmodelling.swing.binding;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;

import com.directmodelling.api.Value;

public class ToggleBinder extends AbstractBinder<Boolean> implements ItemListener {
	private final AbstractButton c;
	AbstractButton b;

	public ToggleBinder(final Value<Boolean> val, final AbstractButton c) {
		super(val);
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