package com.directmodelling.demo.angular.client;

import java.io.Serializable;

import com.directmodelling.api.ID;
import com.directmodelling.demo.angular.shared.PostcodeDemo;
import com.directmodelling.stm.Version;
import com.google.gwt.core.client.js.JsType;

@JsType
public class Init implements Serializable, IInit {
	public Init() {
	}

	public Version storage;
	// public DemoModel model;
	public ID postcodeCacheID;

	public final ID getPostcodeCacheID() {
		return postcodeCacheID;
	}

	// public PostcodeDemo postcodeDemo;

	public Init(final Version storage,// final DemoModel model,
			final PostcodeDemo postcodeDemo, final ID postcodeCacheID) {
		super();
		this.storage = storage;
		// this.model = model;
		// this.postcodeDemo = postcodeDemo;
		this.postcodeCacheID = postcodeCacheID;
	}

	// public final PostcodeDemo getPostcodeDemo() {
	// return postcodeDemo;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.directmodelling.demo.angular.client.IInit#getStorage()
	 */
	@Override
	public final Version getStorage() {
		return storage;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see com.directmodelling.demo.angular.client.IInit#getModel()
	// */
	// @Override
	// public final DemoModel getModel() {
	// return model;
	// }

	// public Calculator calculator;
}