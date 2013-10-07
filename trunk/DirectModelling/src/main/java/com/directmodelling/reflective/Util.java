package com.directmodelling.reflective;

import java.lang.reflect.ParameterizedType;

public class Util {
	/**
	 * This method only works when called on subclasses of classes with at least
	 * one type parameter, not on instances of the Base itself. Thank java
	 * generics erasure for that. This is the reason that ListVar is abstract.
	 * 
	 * @return the type parameter given to ListVar
	 */
	@SuppressWarnings({ "unchecked" })
	public static <Base> Class<?> typeArgument(Class<? extends Base> c, final Class<Base> base) {
		// find the direct subclass of ListVar
		while (c.getSuperclass() != base)
			c = (Class<? extends Base>) c.getSuperclass();
		// get the base (=Base.class) as a ParameterizedType and then the first
		// type argument
		return (Class<?>) ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[0];
	}

}
