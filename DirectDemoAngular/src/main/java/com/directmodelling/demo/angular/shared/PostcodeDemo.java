package com.directmodelling.demo.angular.shared;

import java.io.Serializable;

import com.google.gwt.core.client.js.JsType;

@JsType
public class PostcodeDemo implements Serializable {
	public final Adres1 adres1 = new Adres1();
	public final Adres2 adres2 = new Adres2();

	public final Adres1 adres1() {
		return adres1;
	}

	public final Adres2 adres2() {
		return adres2;
	}

}
