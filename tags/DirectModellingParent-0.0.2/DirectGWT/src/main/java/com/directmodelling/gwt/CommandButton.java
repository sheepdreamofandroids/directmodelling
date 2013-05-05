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
package com.directmodelling.gwt;

import com.directmodelling.impl.Command;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;


public class CommandButton extends Button implements ClickHandler {
	private Command command;

	public CommandButton() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommandButton(final Element element) {
		super(element);
		// TODO Auto-generated constructor stub
	}

	public CommandButton(final String html, final ClickHandler handler) {
		super(html, handler);
		// TODO Auto-generated constructor stub
	}

	public CommandButton(final String html) {
		super(html);
		// TODO Auto-generated constructor stub
	}

	{
		addClickHandler(this);
	}

	public void setCommand(final Command command) {
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void onClick(final ClickEvent event) {
		command.run();
	}
}
