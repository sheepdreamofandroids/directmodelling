package com.directmodelling.reflective;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class Util {
	/**
	 * @param base
	 *            class or interface which has type parameters
	 * @param sub
	 *            class or interface derived from base, NOT base itself
	 * @param index
	 *            zero based index of the type parameter to resolve, must not be
	 *            a parameter anymore in sub
	 * @return base as a parameterizedType with the parameters as they appear in
	 *         the type hierarchy of sub. {@link TypeVariable}s are resolved to
	 *         actual types.
	 */
	@SuppressWarnings({ "unchecked" })
	public static <Base, Sub extends Base> Class<?> typeArgument(final Class<Sub> sub, final Class<Base> base,
									final int index) {
		// find the direct subclass of base
		// Class<? extends Sub> directSub = sub;
		// while (directSub.getSuperclass() != base)
		// directSub = (Class<? extends Sub>) directSub.getSuperclass();
		// get the base (=Base.class) as a ParameterizedType and then the first
		// type argument
		final Pair pair = new Pair(base);
		pair.parameterizedType(sub);
		final ParameterizedType parameterizedType = pair.parameterizedType;
		final Type type = parameterizedType.getActualTypeArguments()[index];

		if (type instanceof Class<?>)
			return (Class<?>) type;

		if (type instanceof ParameterizedType)
			return (Class<?>) ((ParameterizedType) type).getRawType();

		if (type instanceof TypeVariable<?>) {
			final TypeVariable<?>[] typeParameters = pair.sub.getTypeParameters();
			for (int i = 0; i < typeParameters.length; i++) {
				if (typeParameters[i] == type)
					return typeArgument(sub, pair.sub, i);
			}
		}
		throw new RuntimeException("Cannot resolve typeParam " + index + " from base " + base + ".");
	}

	private static final class Pair<Base, Sub extends Base> {
		private Class<Base> base;
		private Class<Base> origin;
		public Class<?> sub;
		// public Class<?> argumentSourceClass;
		public ParameterizedType parameterizedType;

		public Pair(final Class<Base> base) {
			this.base = base;
		}

		/**
		 * @param base
		 *            class object of a class or interface which has type
		 *            parameters
		 * @param sub
		 *            class or interface derived from base (must be class when
		 *            base is class)
		 * @return base as a parameterizedType with the parameters as they
		 *         appear in the type hierarchy of sub. Might still contain
		 *         {@link TypeVariable}s.
		 */
		public final boolean parameterizedType(final Class<?> newsub) {
			final Class<?> superclass = newsub.getSuperclass();
			if (superclass == base) {
				this.parameterizedType = (ParameterizedType) newsub.getGenericSuperclass();
				sub = newsub;
				return true;
			}
			if (superclass != null) {
				if (parameterizedType(superclass)) {
					return true;
				}
			}
			if (base.isInterface()) {
				final Class<?>[] interfaces = newsub.getInterfaces();
				for (int i = 0; i < interfaces.length; i++) {
					if (interfaces[i] == base) {
						this.parameterizedType = (ParameterizedType) newsub.getGenericInterfaces()[i];
						sub = newsub;
						return true;
					}
				}
				for (final Class<?> c : interfaces) {
					if (parameterizedType(c))
						return true;
				}
			}
			return false;
		}
	}
}