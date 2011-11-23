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
import com.google.gwt.user.client.ui.RootPanel;

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

		final System system = new System();
		greetingService.getInitial(new AsyncCallback<GreetingService.Init>() {

			@Override
			public void onSuccess(final Init result) {
				// final MyGinjector ginjector = GWT.create(MyGinjector.class);
				final DemoPanel demoPanel = new DemoPanel(result.model, system);
				RootPanel.get().add(demoPanel);
				system.initializeValues(result.storage);
				system.doneInitializing();
				// Updates.registerForChanges(new Receiver() {
				//
				// private GreetingServiceAsync create;
				//
				// @Override
				// public void valuesChanged() {
				// commit(baseValues, changes);
				//
				// }
				// });
				final CalculatorPanel calc = new CalculatorPanel(new Calculator());
				DialogBox popupPanel = new DialogBox(false, false);
				popupPanel.setTitle("Calculator");
				popupPanel.setText("Calculator");
				popupPanel.add(calc);
				popupPanel.center();
				// popupPanel.show();

			}

			@Override
			public void onFailure(final Throwable caught) {
				GWT.log("", caught);
			}
		});
	}
}
