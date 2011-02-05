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
/**
 * 
 */
package com.directmodelling.swing;

import java.awt.event.FocusEvent;

import javax.swing.JTextArea;

import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value.Mutable;


public class RRichText extends JTextArea implements Receiver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mutable<String> value;

	public RRichText() {
		value = null;
	}

	public RRichText(final Mutable<String> val) {
		value = val;
	}

	@Override
	public void valuesChanged() {
		setText(value.getValue());
	}

	@Override
	protected void processFocusEvent(final FocusEvent e) {
		if (e.getID() == FocusEvent.FOCUS_LOST) {
			value.setValue(getText());
		}
		super.processFocusEvent(e);
	}

	public void setValue(final Mutable<String> value) {
		this.value = value;
		setText(value.getValue());
	}

	public Mutable<String> getValue() {
		return value;
	}

}