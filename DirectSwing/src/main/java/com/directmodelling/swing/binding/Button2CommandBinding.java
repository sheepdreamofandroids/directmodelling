package com.directmodelling.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import com.directmodelling.impl.Command;

public class Button2CommandBinding implements ActionListener {
	private final Command c;

	public Button2CommandBinding(AbstractButton b, Command c) {
		this.c = c;
		b.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		c.run();
	}
}
