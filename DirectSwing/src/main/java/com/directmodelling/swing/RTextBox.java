/*******************************************************************************
 * Copyright (c) 2010 Guus C. Bloemsma.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *******************************************************************************/
package com.directmodelling.swing;

import java.awt.event.FocusEvent;

import javax.swing.JTextField;

import com.directmodelling.api.ObjectValue.Mutable;
import com.directmodelling.api.Updates.Receiver;


public class RTextBox extends JTextField implements Receiver {
	private Mutable<String> value;

	public RTextBox() {
		value = null;
	}

	public RTextBox(final Mutable<String> val) {
		setValue(val);
	}

	@Override
	public void valuesChanged() {
		setText(value.get());
	}

	@Override
	protected void processFocusEvent(final FocusEvent e) {
		if (e.getID() == FocusEvent.FOCUS_LOST) {
			value.set(getText());
		}
		super.processFocusEvent(e);
	}

	public void setValue(final Mutable<String> value) {
		this.value = value;
		setText(value.get());
	}

	public Mutable<String> getValue() {
		return value;
	}

}
