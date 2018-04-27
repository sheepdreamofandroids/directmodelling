package com.directmodelling.impl.util;

public class Porting {
	/** This class only holds static functions. */
	private Porting() {
	}

	public static String UUID() {
		return java.util.UUID.randomUUID().toString();
	}
}
