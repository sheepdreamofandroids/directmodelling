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
package com.directmodelling.android.binding;

import android.widget.SeekBar;
import android.widget.TextView;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Value.Mutable;

/**
 * <p>
 * This is probably the most simplistic form of binding, because it completely
 * relies on source code to statically specify property / widget pairs.
 * </p>
 * <p>
 * Instead reflection could be used to determine the type of property and other
 * metadata. A widget could be created dynamically based on that information.
 * That would be a very flexible way to define the look and feel of your app
 * without coding each property separately.
 * </p>
 */
public class Binding {
	public static SeekBarBinder bindDouble(final SeekBar s, final Mutable<Double> m) {
		return new SeekBarBinder(m, s, Converter.Double2Integer, Converter.Integer2Double);
	}

	public static <T> void bindInteger(final TextView s, final Mutable<Integer> m) {
		new TextViewBinder(m, s, Converter.toString, Converter.String2Integer);
	}

	public static <T> void bindDouble(final TextView s, final Mutable<Double> m) {
		new TextViewBinder(m, s, Converter.toString, Converter.String2Double);
	}

}
