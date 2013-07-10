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
package com.directmodelling.swing.binding;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.text.JTextComponent;

import com.directmodelling.api.Converter;
import com.directmodelling.api.EnumValue;
import com.directmodelling.api.Value;

public class Binding {

	public static void bindDouble(final JSlider slider, final Value<Double> doubleVal) {
		BoundedRangeModelBinding.bind(slider, doubleVal, Converter.Double2Integer, Converter.Integer2Double);
	}

	public static void bindEnum(final JComboBox comboBox, final EnumValue val) {
		ListModelBinding.bind(comboBox, val);
	}

	@SuppressWarnings("unchecked")
	public static void bind(final JSlider slider, final Value<?> val) {
		switch (val.type()) {
		case tDouble:
			bindDouble(slider, (Value<Double>) val);
			return;
		}
	}

	@SuppressWarnings("unchecked")
	public static void bind(final JTextComponent text, final Value<?> val) {
		switch (val.type()) {
		case tDouble:
			bindDouble(text, (Value<Double>) val);
			return;
		case tInteger:
			bindInteger(text, (Value<Integer>) val);
			return;
		default:
			bindString(text, (Value<String>) val);

		}
	}

	public static void bind(final JLabel label, final Value<String> v) {
		ReadOnlyBinding.bindString(label, v);
	}

	@SuppressWarnings("unchecked")
	public static void bind(final Component c, final Value<?> val) {
		if (c instanceof JSlider)
			bind((JSlider) c, val);
		else if (c instanceof JTextComponent)
			bind((JTextComponent) c, val);
		else if (c instanceof JComboBox && val instanceof EnumValue)
			bindEnum((JComboBox) c, (EnumValue) val);
		else if (c instanceof JLabel)
			ReadOnlyBinding.bindString((JLabel) c, (Value<String>) val);
		else if (c instanceof AbstractButton) {
			bindToggle((AbstractButton) c, (Value<Boolean>) val);
		}
	}

	/**
	 * @param toggle
	 * @param value
	 * @return
	 */
	public static AbstractButton bindToggle(final AbstractButton toggle, final Value<Boolean> value) {
		new ToggleBinder<Boolean>(value, Converter.ID_Boolean, Converter.ID_Boolean, toggle);
		return toggle;
	}

	/**
	 * @param m
	 * @param text
	 *            TODO
	 * @param doubleVar
	 *            TODO
	 * @return
	 */
	public static DocumentBinder bindInteger(final JTextComponent text, final Value<Integer> doubleVar) {
		return DocumentBinder.bind(text, doubleVar, Converter.String2Integer, Converter.Integer2String);
	}

	public static DocumentBinder bindDouble(final JTextComponent text, final Value<Double> doubleVar) {
		return DocumentBinder.bind(text, doubleVar, Converter.String2Double, Converter.Double2String);
	}

	public static DocumentBinder bindString(final JTextComponent text, final Value<String> stringVar) {
		return DocumentBinder.bind(text, stringVar, Converter.ID_String, Converter.ID_String);
	}

	// /**
	// * This method only works when called on subclasses of ListVar, not on
	// * instances of ListVar itself. Thank java generics erasure for that. This
	// * is the reason that ListVar is abstract.
	// *
	// * @return the type parameter given to ListVar
	// */
	// @SuppressWarnings({"rawtypes", "unchecked"})
	// public static Class<?> componentType(Class<? extends Value> c) {
	// // find the direct subclass of Value
	// while (c.getSuperclass() != Value.class)
	// c = (Class<? extends Value>) c.getSuperclass();
	// // get the base (=Value) as a ParameterizedType and then the first
	// // type argument
	// return ((ParameterizedType) c.getGenericSuperclass())
	// .getActualTypeArguments()[0].getClass();
	// }

}