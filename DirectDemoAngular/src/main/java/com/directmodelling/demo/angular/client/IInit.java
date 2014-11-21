package com.directmodelling.demo.angular.client;

import com.directmodelling.demo.shared.DemoModel;
import com.directmodelling.stm.impl.VersionImpl;
import com.google.gwt.core.client.js.JsType;

@JsType
public interface IInit {

	public abstract VersionImpl getStorage();

	public abstract DemoModel getModel();

}