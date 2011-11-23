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
package com.directmodelling.gwt;

import java.util.Iterator;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.impl.util.Function;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * Binds the list of subcomponents of a panel to a list of arbitrary data, using
 * a function to transform the data into components.
 */
public class Iterator2PanelBinding<T> implements Receiver {
	ForIsWidget container;
	Value<Iterable<T>> values;
	Function<T, IsWidget> factory;

	public Iterator2PanelBinding(final ForIsWidget container, final Value<Iterable<T>> values,
					final Function<T, IsWidget> factory) {
		super();
		this.container = container;
		this.values = values;
		this.factory = new FunctionCache<T, IsWidget>(factory);
		Updates.registerForChanges(this);
	}

	public void valuesChanged() {
		Iterator<Widget> cont = container.iterator();
		Iterator<T> vals = values.getValue().iterator();

		while (vals.hasNext() && cont.hasNext()) {
			IsWidget newWidget = factory.apply(vals.next());
			while (cont.hasNext() && cont.next() != newWidget)
				cont.remove();
			if (!cont.hasNext()) {
				container.add(newWidget);
				while (vals.hasNext())
					container.add(factory.apply(vals.next())); // end of panel
				return;
			}
		}

		// remove superfluous children
		while (cont.hasNext()) {
			cont.next();
			cont.remove();
		}

		while (vals.hasNext())
			container.add(factory.apply(vals.next())); // end of panel

	}
}