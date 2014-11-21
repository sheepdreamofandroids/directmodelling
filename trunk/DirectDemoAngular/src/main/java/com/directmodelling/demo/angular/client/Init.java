package com.directmodelling.demo.angular.client;

import java.io.Serializable;

import com.directmodelling.demo.shared.DemoModel;
import com.directmodelling.stm.impl.VersionImpl;

public class Init implements Serializable, IInit {
	public Init() {
	}

	public VersionImpl storage;
	public DemoModel model;

	public Init(final VersionImpl storage, final DemoModel model) {
		super();
		this.storage = storage;
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see com.directmodelling.demo.angular.client.IInit#getStorage()
	 */
	@Override
	public final VersionImpl getStorage() {
		return storage;
	}

	/* (non-Javadoc)
	 * @see com.directmodelling.demo.angular.client.IInit#getModel()
	 */
	@Override
	public final DemoModel getModel() {
		return model;
	}

	// public Calculator calculator;
}