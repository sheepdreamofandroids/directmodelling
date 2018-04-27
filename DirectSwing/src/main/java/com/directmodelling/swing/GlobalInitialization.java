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
package com.directmodelling.swing;


//public class GlobalInitialization implements Context.Initializer {
//	// public static void initialize() {
//	// Context.It.initialize(new SimpleContext<Initializer>(
//	// new GlobalInitialization()));
//	// }
//
//	private final static Tracker valuesChangedUpdateTracker = new SwingUpdateTracker();
//
//	public static Tracker getValuesChangedUpdateTracker() {
//		return GlobalInitialization.getImplementationFor(Tracker.class);
//	}
//
//	@SuppressWarnings("unchecked")
//	public static <T> T getImplementationFor(final Class<T> clazz) {
//		if (clazz == Tracker.class) {
//			return (T) GlobalInitialization.valuesChangedUpdateTracker;
//		}
//		return null;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T> Context<T> create(final Class<T> t) {
//		if (t == Tracker.class) {
//			return new SwingContext<T>((T) new SwingUpdateTracker());
//		}
//		return null;
//	}
//
// }
