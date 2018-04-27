package com.directmodelling.impl;

import com.directmodelling.api.ID;

/** Trivial id generator, NOT unique among network peers. */
public class SimpleIDGenerator implements ID.IDGenerator {
	public static final SimpleIDGenerator INSTANCE = new SimpleIDGenerator();
	private int counter = 0;

	// TODO extend to make unique in network

	@Override
	public ID createID() {
		return new SimpleID(++counter);
	}
}