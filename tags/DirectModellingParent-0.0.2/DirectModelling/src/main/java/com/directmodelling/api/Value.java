/*******************************************************************************
 * Copyright (c) 2010 Guus C. Bloemsma.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *******************************************************************************/
package com.directmodelling.api;

import java.io.Serializable;

public interface Value<T> extends Serializable {
	/** @return Boxed value */
	T getValue();

	Type type();

	/** Indicate JVM type */
	enum Type {
		tBoolean, tByte, tShort, tInteger, tLong, tCharacter, tFloat, tDouble, tObject;

		public static Type fromClass(Class<?> c) {
			if (c == Boolean.class || c == Boolean.TYPE)
				return tBoolean;
			if (c == Byte.class || c == Byte.TYPE)
				return tByte;
			if (c == Short.class || c == Short.TYPE)
				return tShort;
			if (c == Integer.class || c == Integer.TYPE)
				return tInteger;
			if (c == Long.class || c == Long.TYPE)
				return tLong;
			if (c == Character.class || c == Character.TYPE)
				return tCharacter;
			if (c == Float.class || c == Float.TYPE)
				return tFloat;
			if (c == Double.class || c == Double.TYPE)
				return tDouble;
			return tObject;
		}
	}

	public interface Mutable<T> extends Value<T> {
		/**
		 * @param value
		 *            Boxed value to be stored.
		 */
		void setValue(T value);
		// a setter
	}

	// public interface UserValue<T> extends Mutable<T>, HasStatus {
	// // maybe another status getter
	// }

}
