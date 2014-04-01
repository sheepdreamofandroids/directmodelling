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

import javax.swing.JComponent;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.impl.util.FunctionCache;
import com.google.common.base.Function;

/**
 * Binds the list of subcomponents of a panel to a list of arbitrary data, using
 * a function to transform the data into components.
 */
public class Iterator2PanelBinding<T> implements Receiver {
	protected final Container container;
	protected final Value<? extends Iterable<? extends T>> values;
	protected final Function<T, Component> factory;

	public Iterator2PanelBinding(final Container container,
			final Value<? extends Iterable<? extends T>> values,
			final Function<T, Component> factory) {
		super();
		this.container = container;
		this.values = values;
		this.factory = new FunctionCache<T, Component>(factory);
		Updates.registerForChanges(this);
	}

	@Override
	public void valuesChanged() {
		// TODO should attempt to change as little as possible
		int index = 0;
		for (final T t : values.getValue()) {
			final Component component = factory.apply(t);
			// inserts new, moves or leaves alone
			if (container.getComponentCount() <= index
					|| container.getComponent(index) != component) {
				container.add(component, index);
				component.invalidate();
			}
			index++;
		}

		// remove superfluous children
		while (container.getComponentCount() > index) {
			container.remove(index);
		}

		// if (container instanceof JComponent) {
		// ((JComponent) container).revalidate();
		// } else {
		container.invalidate();
		// }
	}

	/**
	 * Updates the container to contain the given content. Tries to do as few
	 * inserts/removes as possible.
	 */
	public static void update(final Container container,
			final Iterable<Component> content) {
		// TODO should attempt to change as little as possible
		int index = 0;
		for (final Component component : content) {
			// inserts new, moves or leaves alone
			if (container.getComponentCount() <= index
					|| container.getComponent(index) != component)
				container.add(component, index);
			index++;
		}

		// remove superfluous children
		while (container.getComponentCount() > index) {
			container.remove(index);
		}

		if (container instanceof JComponent) {
			((JComponent) container).revalidate();
		} else {
			container.invalidate();
		}
	}
}