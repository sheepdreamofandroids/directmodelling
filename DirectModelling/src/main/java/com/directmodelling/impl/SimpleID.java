package com.directmodelling.impl;

import com.directmodelling.api.ID;

public final class SimpleID implements ID {
	private final int id;

	private SimpleID() {
		id = -1;
	}

	SimpleID(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof ID;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return "ID" + id + "=";
	}
}