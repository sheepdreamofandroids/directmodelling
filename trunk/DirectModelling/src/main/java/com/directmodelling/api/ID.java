package com.directmodelling.api;

import java.io.Serializable;

import com.directmodelling.impl.SingleAssignContext;

/**
 * Marker interface that promises to implement {@link #equals(Object)} to be
 * unique across connected systems and {@link #hashCode()} accordingly.
 */
public interface ID extends Serializable {

	interface IDGenerator {
		ID createID();
	}

	SingleAssignContext<IDGenerator> generator = Context.GLOBAL.it()
			.create();

}
