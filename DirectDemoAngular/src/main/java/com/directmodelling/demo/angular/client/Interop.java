package com.directmodelling.demo.angular.client;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsType;

@JsType
interface Interop {
	public int integer();

	public double twoTimesFloat(double a);
}