package com.directmodelling.impl;

import java.util.Collection;

import com.directmodelling.api.Value;
import com.directmodelling.stm.Storage;

public interface EntityUtil {

	/** Iterates all properties recursively while applying code. */
	void forAllProperties(Object entity, Applicable<Value<?>> code);

	/** @return all properties as a collection. */
	Collection<Value<?>> allProperties(Object entity);

	/** Binds all properties in the bean to the storage. */
	void store(Object bean, final Storage storage);

}