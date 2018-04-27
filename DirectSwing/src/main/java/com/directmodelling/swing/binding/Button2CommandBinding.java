package com.directmodelling.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import com.directmodelling.api.Status;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.impl.Command;

public class Button2CommandBinding implements ActionListener, Receiver {
	private final Command c;
	private AbstractButton button;

	public Button2CommandBinding(final AbstractButton b, final Command c) {
		this.button = b;
		this.c = c;
		b.addActionListener(this);
		Updates.registerForChanges(this);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		c.run();
	}

	@Override
	public void valuesChanged() {
		final Status status = c.status();
		button.setEnabled(status.isEnabled());
	}
}
