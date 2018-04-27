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

import com.directmodelling.impl.SimpleContext;
import com.directmodelling.impl.SingleAssignContext;
import com.google.common.base.Supplier;

/**
 * <p>
 * Context's allow use of services without depending on injection.
 * </p>
 * <p>
 * Injection only works when the correct service can be determined during
 * creation of the client and doesn't change during it's lifetime. This is quite
 * an assumption to build into the client. Otherwise a proxy service has to be
 * used.
 * </p>
 * <p>
 * Often there is exactly one service of a given kind and there is no good
 * reason to ever have more than one. In that case it makes the code a lot
 * clearer when you simply refer to 'the global one'. This is what Spring does
 * with @Autowired or Guice with bind().toInstance().
 * </p>
 * <p>
 * On the other hand, code can run in different environments (Swing, GWT,
 * Webserver etc.) which sometimes means that services are bound to some kind of
 * session. In GWT or Swing there would be exactly one locale during the full
 * runtime of the program, but in a webserver the locale would depend on the
 * settings in the browser. Now what do you do with code shared between a
 * webserver and GWT?
 * </p>
 * <p>
 * Business logic doesn't want to know about such details and simply wants
 * whatever is appropriate for the environment.
 * </p>
 * 
 * @author guus
 * 
 * @param <T>
 */
// TODO simply use Value?? rename it() to get()
public interface Context<T> {
	/** Get the appropriate service */
	T it();

	public interface Factory {
		public static final class Default implements Factory {
			public static final Default INSTANCE = new Default();

			@Override
			public <T> Context<T> constant(final T initialValue) {
				return new SimpleContext<T>(initialValue);
			}

			@Override
			public <T> Context<T> create(final Supplier<T> supplier) {
				return new Context<T>() {
					T value = null;

					@Override
					public T it() {
						if (value == null)
							value = supplier.get();
						return value;
					}
				};
			}

			@Override
			public <T> SingleAssignContext<T> create() {
				return new SingleAssignContext<T>();
			}
		}

		<T> Context<T> constant(T initialValue);

		<T> Context<T> create(Supplier<T> supplier);

		<T> SingleAssignContext<T> create();
	}

	SingleAssignContext<Factory> SESSION = new SingleAssignContext<Context.Factory>();
	SingleAssignContext<Factory> GLOBAL = new SingleAssignContext<Context.Factory>();

	/**
	 * Implementations should create the appropriate, initialized Context
	 * implementation for a given service. Make sure to implement all cases that
	 * can possibly occur.
	 */
	// public interface Initializer {
	// <T> Context<T> create(Class<T> t);
	// }

	// public abstract class It {
	// /**
	// * init ASAP after program start.
	// */
	// // public static void initialize(final Context<? extends Initializer>
	// // ini) {
	// // It.it = ini;
	// // }
	//
	// public static <T> Context<T> create(final Class<T> t) {
	// return It.it.it().create(t);
	// };
	//
	// private static Context<? extends Initializer> it = null;
	// }
}
