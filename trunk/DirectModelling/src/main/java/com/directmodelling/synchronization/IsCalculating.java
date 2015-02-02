package com.directmodelling.synchronization;

import java.io.Serializable;

public enum IsCalculating implements Serializable {
	/** Indicates the server is calculating this key */
	onClient, onServer;

	IsCalculating() {
	}
}