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
package com.directmodelling.demo.angular.client;

import com.directmodelling.api.BooleanValue;
import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.FloatValue;
import com.directmodelling.api.IntValue;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Value;
import com.directmodelling.gwt.sync.SyncService;
import com.directmodelling.gwt.sync.SyncService.MakeSerializable;
import com.directmodelling.gwt.sync.SyncServiceAsync;
import com.directmodelling.gwt.sync.Synchronizer;
import com.directmodelling.stm.Version;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.js.JsExport;
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
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final MySyncServiceAsync greetingService = GWT
			.create(MySyncService.class);

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		makeSerializable();

		final ClientInitializer system = new ClientInitializer(greetingService);
		system.init();
		greetingService.initial(new AsyncCallback<Version>() {

			@Override
			public void onSuccess(final Version result) {
				system.baseValues.initializeValues(result);
				// Util.current.init(changes);
				Updates.tracker.runUpdates();
				// show(result.getModel().a());
				// show(new Obfuscated());
				// show(new Pretty());
				// Element.as(Document.get().getParentNode()).setPropertyObject(
				// "directDemoModel", result.model);
				// setUpGetterSetter(result.model.a());
				AngularAdapter.startAngular(// result.getModel(), new
											// Calculator(),
						// result.postcodeDemo
						system.demo);
				Synchronizer.it.it().poll();
			}

			@Override
			public void onFailure(final Throwable caught) {
				GWT.log("", caught);
			}
		});
	}

	public static native void show(Object o)/*-{
		console.log(o);
	}-*/;

	public static native void setUpGetterSetter(Value.Mutable<?> mut)/*-{
		var proto = mut.getPrototypeOf();
		mut.prototype.value = function(val) {
			if (val != undefined) {
				mut.setValue(val);
			}
			return mut.getValue();
		};
	}-*/;

	@JsExport("$wnd.access")
	public static native Object access(Value v)/*-{
		return function(val) {
			if (val != undefined) {
				mut.setValue(val);
			}
			return mut.getValue();
		};
	}-*/;

	/** dummy code to work around some serialization issues */
	public void makeSerializable() {
		if (Document.get() == null) {
			greetingService.dummy(new MakeSerializable(),
					new AsyncCallback<MakeSerializable>() {

						@Override
						public void onFailure(final Throwable caught) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(final MakeSerializable result) {
							// TODO Auto-generated method stub

						}
					});
			greetingService.dummy(new FixSerialization(), new AsyncCallback<FixSerialization>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(FixSerialization result) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}

	public void showCalculator() {
	}

	public void popup(final Widget calc, final String title) {
		final DialogBox popupPanel = new DialogBox(false, false);
		popupPanel.setTitle(title);
		popupPanel.setText("title");
		popupPanel.add(calc);
		popupPanel.center();
	}

	@JsExport()
	public static native Object asInt(IntValue.Mutable i)/*-{
		return function(val) {
			if (val != undefined) {
				i.set(val);
			}
			return i.get();
		};
	}-*/;

	@JsExport()
	public static native Object asFloat(FloatValue.Mutable m)/*-{
		return function(val) {
			if (val != undefined) {
				m.set(val);
			}
			return m.get();
		};
	}-*/;

	@JsExport()
	public static native Object asDouble(DoubleValue.Mutable m)/*-{
		return function(val) {
			if (val != undefined) {
				m.set(val);
			}
			return m.get();
		};
	}-*/;

	@JsExport()
	public static native Object asBoolean(BooleanValue.Mutable m)/*-{
		return function(val) {
			if (val != undefined) {
				m.set(val);
			}
			return m.get();
		};
	}-*/;

	@JsExport("directAngular.setup")
	public static native void setup()/*-{
		return function($scope) {
			$scope.asInt = com.directmodelling.demo.angular.client.DirectDemo.asInt;
			$scope.asFloat = com.directmodelling.demo.angular.client.DirectDemo.asFloat;
			$scope.asDouble = com.directmodelling.demo.angular.client.DirectDemo.asDouble;
			$scope.asBoolean = com.directmodelling.demo.angular.client.DirectDemo.asBoolean;
		};
	}-*/;
}
