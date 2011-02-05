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

/**
 * <p>
 * Context's allow use of services without depending on injection.
 * </p>
 * <p>
 * Injection only works when the correct service can be determined during
 * creation of the client. Otherwise a proxy service has to be used.
 * </p>
 * <p>
 * Often there is exactly one service of a given kind and there is no good
 * reason to ever have more than one. In that case it makes the code a lot
 * clearer when you simply refer to 'the global one'.
 * </p>
 * <p>
 * On the other hand, code can run in different environments (Swing, GWT,
 * Webserver etc.) which sometimes means that services are bound to some kind of
 * session. In GWT or Swing there would be exactly one locale, but in a
 * webserver the locale would depend on the settings in the browser.
 * </p>
 * <p>
 * Business logic doesn't want to know about such details and simply wants the
 * 'appropriate' locale or resourcebundle.
 * </p>
 * 
 * @author guus
 * 
 * @param <T>
 */
public interface Context<T> {
	/** Get the appropriate service */
	T it();

	public interface Factory {
		<T> Context<T> create(T initialValue);
	}

	/**
	 * Implementations should create the appropriate, initialized Context
	 * implementation for a given service. Make sure to implement all cases that
	 * can possibly occur.
	 */
	public interface Initializer {
		<T> Context<T> create(Class<T> t);
	}

	public abstract class It {
		/**
		 * init ASAP after program start.
		 */
		// public static void initialize(final Context<? extends Initializer> ini) {
		// It.it = ini;
		// }

		public static <T> Context<T> create(final Class<T> t) {
			return It.it.it().create(t);
		};

		private static Context<? extends Initializer> it = null;
	}
}
