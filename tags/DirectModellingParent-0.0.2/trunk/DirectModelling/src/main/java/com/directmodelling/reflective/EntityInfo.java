package com.directmodelling.reflective;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import com.directmodelling.api.Value;
import com.directmodelling.impl.Applicable;
import com.directmodelling.impl.EntityUtil;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Storage.HasStorage;
import com.google.inject.Singleton;

@Singleton
public class EntityInfo implements EntityUtil {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.directmodelling.impl.EntityUtil#forAllProperties(java.lang.Object,
	 * com.directmodelling.impl.Applicable)
	 */
	@Override
	public void forAllProperties(Object entity, Applicable<Value<?>> code) {
		if (null != entity)
			try {
				final Class<? extends Object> clazz = entity.getClass();
				if (Value.class.isAssignableFrom(clazz))
					code.applyTo((Value<?>) entity);
				for (Field field : clazz.getFields()) {
					field.setAccessible(true);
					final Object val = field.get(entity);
					if (null != val)
						forAllProperties(val, code);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.directmodelling.impl.EntityUtil#allProperties(java.lang.Object)
	 */
	@Override
	public Collection<Value<?>> allProperties(Object entity) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.directmodelling.impl.EntityUtil#bind(java.lang.Object,
	 * com.directmodelling.stm.Storage)
	 */
	@Override
	public void store(Object bean, final Storage storage) {
		forAllProperties(bean, new Applicable<Value<?>>() {
			@Override
			public void applyTo(Value<?> value) {
				if (value instanceof HasStorage)
					((HasStorage) value).setStorage(storage);
				storage.bindProperty(value);
			}
		});
	}

}
