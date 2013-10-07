package com.directmodelling.reflective;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

import com.directmodelling.api.HasKey.Registry;
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
	public void forAllProperties(final Object entity, final Applicable<Value<?>> code) {
		if (null != entity)
			try {
				final Class<? extends Object> clazz = entity.getClass();
				if (Value.class.isAssignableFrom(clazz))
					code.applyTo((Value<?>) entity);
				for (final Field field : clazz.getFields()) {
					field.setAccessible(true);
					final Object val = field.get(entity);
					if (null != val)
						forAllProperties(val, code);
				}
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.directmodelling.impl.EntityUtil#allProperties(java.lang.Object)
	 */
	@Override
	public Collection<Value<?>> allProperties(final Object entity) {
		final ArrayList<Value<?>> result = new ArrayList<Value<?>>();
		final Class<? extends Object> clazz = entity.getClass();
		for (final Method method : clazz.getMethods()) {
			method.setAccessible(true);
			if (method.getParameterTypes().length == 0 && Value.class.isAssignableFrom(method.getReturnType()))
				try {
					result.add((Value<?>) method.invoke(entity));
				} catch (final Exception e) {
					throw new RuntimeException("While trying to retrieve property '" + method.getName()
							+ "' from method on:" + entity, e);
				}
		}
		for (final Field field : clazz.getFields()) {
			field.setAccessible(true);
			if (Value.class.isAssignableFrom(field.getType()))
				try {
					result.add((Value<?>) field.get(entity));
				} catch (final Exception e) {
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
	public void store(final Object bean, final Storage storage) {
		forAllProperties(bean, new Applicable<Value<?>>() {
			@Override
			public void applyTo(final Value<?> value) {
				if (value instanceof HasStorage)
					((HasStorage) value).setStorage(storage);
				storage.bindProperty(value);
			}
		});
	}

	/** Recursively register all names of properties as their field names. */
	public static void registerKeys(final Object o) {
		final Field[] fields = o.getClass().getFields();
		for (final Field f : fields) {
			if (Modifier.isFinal(f.getModifiers()) && Modifier.isPublic(f.getModifiers()))
				try {
					final Object o2 = f.get(o);
					final boolean containsKey = Registry.names.containsKey(o2);
					if (!containsKey) {
						System.out.println("Containskey: " + containsKey);
						Registry.register(o2, f.getName());
						registerKeys(o2);
					}
				} catch (final IllegalArgumentException e) {
					e.printStackTrace();
				} catch (final IllegalAccessException e) {
					e.printStackTrace();
				}
		}
	}

}
