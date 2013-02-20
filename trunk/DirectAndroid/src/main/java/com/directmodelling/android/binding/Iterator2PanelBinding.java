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

import java.util.Iterator;

import android.view.View;
import android.view.ViewGroup;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.impl.util.Function;
import com.directmodelling.impl.util.FunctionCache;

/**
 * Binds the list of subcomponents of a panel to a list of arbitrary data, using
 * a function to transform the data into components.
 */
public class Iterator2PanelBinding<T> implements Receiver {
	ViewGroup container;
	Value<Iterable<T>> values;
	Function<T, View> factory;

	public Iterator2PanelBinding(final ViewGroup container, final Value<Iterable<T>> values,
					final Function<T, View> factory) {
		super();
		this.container = container;
		this.values = values;
		this.factory = new FunctionCache<T, View>(factory);
		Updates.registerForChanges(this);
	}

	public void valuesChanged() {
		int index = 0;
		Iterator<T> vals = values.getValue().iterator();

		while (vals.hasNext() && index < container.getChildCount()) {
			View newWidget = factory.apply(vals.next());
			while (index < container.getChildCount() && container.getChildAt(index) != newWidget)
				container.removeViewAt(index);
			if (index >= container.getChildCount())
				container.addView(newWidget);
			index++;
		}

		// remove superfluous children
		while (index < container.getChildCount()) {
			container.removeViewAt(index);
		}

		while (vals.hasNext())
			container.addView(factory.apply(vals.next())); // end of panel

	}
}