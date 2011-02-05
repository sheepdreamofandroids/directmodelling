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
import java.awt.Container;
import java.util.WeakHashMap;

import javax.swing.JComponent;

import com.directmodelling.api.ListValue;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;


/**
 * Binds the list of subcomponents of a panel to a list of arbitrary data, using
 * a function to transform the data into components.
 */
public class List2ComponentsBinding<T> implements Receiver {
	public static class ComponentFactoryCache<In, Out> implements Function<In, Out> {

		private final WeakHashMap<In, Out> cache = new WeakHashMap<In, Out>();
		Function<In, Out> fun;

		public ComponentFactoryCache(final Function<In, Out> fun) {
			super();
			this.fun = fun;
		}

		@Override
		public Out apply(final In in) {
			Out out = cache.get(in);
			if (null == out) {
				out = fun.apply(in);
				cache.put(in, out);
			}
			return out;
		}

	}

	Container container;
	ListValue<T> values;
	Function<T, Component> factory;

	public List2ComponentsBinding(
		final Container container,
		final ListValue<T> values,
		final Function<T, Component> factory) {
		super();
		this.container = container;
		this.values = values;
		this.factory = factory;
		Updates.registerForChanges(this);
	}

	public interface Function<TData, TWidget> {
		/** update the existing oldWidget or create a new one from data */
		TWidget apply(TData in);

		// D newItem();
	}

	@Override
	public void valuesChanged() {
		// modify container to match
		final int componentCount = container.getComponentCount();
		final int size = values.size();
		for (int i = 0; i < componentCount; i++) {
			final Component component = container.getComponent(i);

			final T data = values.get(i);
			// if (null != data) {
			final Component converted = factory.apply(data);
			if (component != converted) {
				container.remove(i);
				container.add(converted, i);
			}
			// } else {
			// container.remove(container.getComponentCount() - 1);
			// }
		}
		for (int i = componentCount; i < size; i++) {
			container.add(factory.apply(values.get(i)));
		}

		if (container instanceof JComponent) {
			((JComponent) container).revalidate();
		} else {
			container.invalidate();
		}
	}
}