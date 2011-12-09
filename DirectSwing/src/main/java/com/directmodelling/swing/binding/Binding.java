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

import javax.swing.JSlider;
import javax.swing.text.JTextComponent;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Value;

public class Binding {

	/**
	 * @param slider
	 *            TODO
	 * @param doubleVal
	 *            TODO
	 */
	public static void bindDouble(JSlider slider, Value<Double> doubleVal) {
		BoundedRangeModelBinding.bind(slider, doubleVal, Converter.Double2Integer,
				Converter.Integer2Double);
	}

	@SuppressWarnings("unchecked")
	public static void bind(JSlider slider, Value<?> val) {
		switch (val.type()) {
		case tDouble:
			bindDouble(slider, (Value<Double>) val);
			return;
		}
	}

	@SuppressWarnings("unchecked")
	public static void bind(JTextComponent text, Value<?> val) {
		switch (val.type()) {
		case tDouble:
			bindDouble(text, (Value<Double>) val);
			return;
		}
	}

	@SuppressWarnings("unchecked")
	public static void bind(Component c, Value<?> val) {
		if (c instanceof JSlider)
			bind((JSlider) c, val);
		else if (c instanceof JTextComponent)
			bind((JTextComponent) c, val);
	}

	/**
	 * @param m
	 * @param text
	 *            TODO
	 * @param doubleVar
	 *            TODO
	 * @return
	 */
	public static DocumentBinder bindDouble(JTextComponent text, Value<Double> doubleVar) {
		return DocumentBinder.bind(text, doubleVar, Converter.String2Double,
				Converter.Double2String);
	}
}