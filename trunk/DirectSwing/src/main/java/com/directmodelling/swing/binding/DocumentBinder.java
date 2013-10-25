package com.directmodelling.swing.binding;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.directmodelling.api.Value;
import com.directmodelling.stm.impl.UninitializedException;

public class DocumentBinder extends AbstractBinder<String> implements DocumentListener {

	private final JTextComponent component;
	private boolean suppressTextChanges;

	public DocumentBinder(final JTextComponent component, final Value<String> val) {
		super(val);
		this.component = component;
		component.getDocument().addDocumentListener(this);
	}

	@Override
	protected void setWidgetValue(final String v) {
		if (!component.hasFocus()) {
			suppressTextChanges = true;
			try {
				component.setText(v);
			} catch (final UninitializedException uie) {
				// ignore, textfield not set
			} finally {
				suppressTextChanges = false;
			}
		}
	}

	@Override
	protected java.lang.String getWidgetValue() {
		return component.getText();
	}

	private void edited() {
		if (!suppressTextChanges) {
			widgetChanged();
		}
	}

	// DocumentListener
	@Override
	public void changedUpdate(final DocumentEvent e) {
		edited();
	}

	@Override
	public void insertUpdate(final DocumentEvent e) {
		edited();
	}

	@Override
	public void removeUpdate(final DocumentEvent e) {
		edited();
	}

}
