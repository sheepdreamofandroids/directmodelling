package com.directmodelling.demo.angular.shared;

import java.io.Serializable;

public enum IsCalculating implements Serializable {
	/** Indicates the server is calculating this key */
	onClient, onServer;

	IsCalculating() {
	}
}