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
package com.directmodelling.demo.gwt.client;


import com.directmodelling.demo.shared.IDemoModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DemoPanel extends Composite {
	public static interface Factory {
		DemoPanel create(IDemoModel i);
	}

	private static DemoPanelUiBinder uiBinder = GWT.create(DemoPanelUiBinder.class);

	interface DemoPanelUiBinder extends UiBinder<Widget, DemoPanel> {
	}

	@UiField(provided = true)
	IDemoModel model;
	private final System system;

	public DemoPanel(final IDemoModel model, final System system) {
		this.model = model;
		this.system = system;
		initWidget(uiBinder.createAndBindUi(this));
	}

}
