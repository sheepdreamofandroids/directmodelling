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

import javax.swing.JSlider;
import javax.swing.text.JTextComponent;

import com.directmodelling.api.Converter;
import com.directmodelling.impl.DoubleVar;


public class Binding {

	/**
	 * @param slider
	 *          TODO
	 * @param doubleVar
	 *          TODO
	 */
	public static void bind(JSlider slider, DoubleVar doubleVar) {
		BoundedRangeModelBinding.bind(slider, doubleVar, Converter.Double2Integer, Converter.Integer2Double);
	}

	/**
	 * @param m
	 * @param text
	 *          TODO
	 * @param doubleVar
	 *          TODO
	 * @return
	 */
	public static DocumentBinder bind(JTextComponent text, DoubleVar doubleVar) {
		return DocumentBinder.bind(text, doubleVar, Converter.String2Double, Converter.Double2String);
	}
}