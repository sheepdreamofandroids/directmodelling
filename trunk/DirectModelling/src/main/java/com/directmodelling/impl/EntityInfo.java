package com.directmodelling.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import com.directmodelling.api.Value;

public class EntityInfo {
	public static Collection<Value<?>> allProperties(Object entity) {
		ArrayList<Value<?>> result = new ArrayList<Value<?>>();
		final Class<? extends Object> clazz = entity.getClass();
		for (Method method : clazz.getMethods()) {
			method.setAccessible(true);
			if (method.getParameterTypes().length == 0 && Value.class.isAssignableFrom(method.getReturnType()))
				try {
					result.add((Value<?>) method.invoke(entity));
				} catch (Exception e) {
					throw new RuntimeException("While trying to retrieve property '" + method.getName()
									+ "' from method on:" + entity, e);
				}
		}
		for (Field field : clazz.getFields()) {
			field.setAccessible(true);
			if (Value.class.isAssignableFrom(field.getType()))
				try {
					result.add((Value<?>) field.get(entity));
				} catch (Exception e) {
					throw new RuntimeException("While trying to retrieve property '" + field.getName()
									+ "' from field on:" + entity, e);
				}
		}
		return result;
	}
}
