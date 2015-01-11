package com.directmodelling.demo.angular.client;

import com.directmodelling.stm.Version;
import com.google.gwt.core.client.js.JsType;

@JsType
public interface IInit {

	public abstract Version getStorage();

	// public abstract DemoModel getModel();

}