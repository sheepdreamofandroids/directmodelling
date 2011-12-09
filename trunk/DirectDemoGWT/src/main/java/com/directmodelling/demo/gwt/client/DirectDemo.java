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

import com.directmodelling.demo.gwt.client.GreetingService.Init;
import com.directmodelling.demo.gwt.client.GreetingService.MakeSerializable;
import com.directmodelling.demo.gwt.client.calculator.CalculatorPanel;
import com.directmodelling.demo.shared.Calculator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DirectDemo implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
					+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {

		makeSerializable();

		final System system = new System();
		greetingService.getInitial(new AsyncCallback<GreetingService.Init>() {

			@Override
			public void onSuccess(final Init result) {
				system.initializeValues(result.storage);
				system.doneInitializing();
				popup(new DemoPanel(result.model, system), "sliders");
				popup(new CalculatorPanel(new Calculator()), "Calculator");
			}

			@Override
			public void onFailure(final Throwable caught) {
				GWT.log("", caught);
			}
		});
	}

	/** dummy code to work around some serialization issues */
	public void makeSerializable() {
		if (Document.get() == null) {
			greetingService.dummy(new MakeSerializable(), new AsyncCallback<MakeSerializable>() {

				@Override
				public void onFailure(final Throwable caught) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(final MakeSerializable result) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	public void showCalculator() {
		popup(new CalculatorPanel(new Calculator()), "Calculator");
	}

	public void popup(final Widget calc, String title) {
		DialogBox popupPanel = new DialogBox(false, false);
		popupPanel.setTitle(title);
		popupPanel.setText("title");
		popupPanel.add(calc);
		popupPanel.center();
	}
}
