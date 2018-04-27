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

public interface Converter<From, To> {

	To convert(From value);

	public static final Converter<String, Double> String2Double = new Converter<String, Double>() {
		@Override
		public Double convert(final String value) {
			return Double.valueOf(value);
		}
	};

	public static final Converter<String, Integer> String2Integer = new Converter<String, Integer>() {
		@Override
		public Integer convert(final String value) {
			return Integer.valueOf(value);
		}
	};

	public static final Converter<String, Long> String2Long = new Converter<String, Long>() {
		@Override
		public Long convert(final String value) {
			return Long.valueOf(value);
		}
	};

	public static final Converter<? extends Object, String> toString = new Converter<Object, String>() {

		@Override
		public String convert(final Object value) {
			return String.valueOf(value);
		}
	};
	public static final Converter<Double, String> Double2String = (Converter<Double, String>) toString;
	public static final Converter<Integer, String> Integer2String = (Converter<Integer, String>) toString;
	public static final Converter<Long, String> Long2String = (Converter<Long, String>) toString;

	public abstract class Util {
		@SuppressWarnings("unchecked")
		public static <To, From extends To> Converter<From, To> id() {
			return (Converter<From, To>) id;
		}

		public static <T> Converter<T, T> id(final Class<T> c) {
			return (Converter<T, T>) id;
		}

		@SuppressWarnings("unchecked")
		public static <T extends Number> Converter<T, Double> toDouble() {
			return (Converter<T, Double>) Number2Double;
		}

		@SuppressWarnings("unchecked")
		public static <T extends Number> Converter<T, Integer> toInteger() {
			return (Converter<T, Integer>) Number2Integer;
		}

		private static final Converter<?, ?> id = new Converter<Object, Object>() {

			@Override
			public Object convert(final Object value) {
				return value;
			}
		};
	}

	Converter<Number, Double> Number2Double = new Converter<Number, Double>() {

		@Override
		public Double convert(final Number value) {
			return value.doubleValue();
		}
	};
	Converter<Number, Integer> Number2Integer = new Converter<Number, Integer>() {

		@Override
		public Integer convert(final Number value) {
			return value.intValue();
		}
	};
	Converter<Double, Integer> Double2Integer = Util.toInteger();
	Converter<Double, Number> Double2Number = Util.id();

	Converter<Integer, Double> Integer2Double = Util.toDouble();

	public static final Converter<String, String> ID_String = Util.id();
	public static final Converter<Double, Double> ID_Double = Util.id();
	public static final Converter<Boolean, Boolean> ID_Boolean = Util.id();
	public static final Converter<Integer, Integer> ID_Integer = Util.id();

}