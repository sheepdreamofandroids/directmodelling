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

import com.directmodelling.api.Value;
import com.google.common.base.Function;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

//import com.google.gwt.user.client.ui.IsWidget;

/**
 * Binds the list of subcomponents of a panel to a list of arbitrary data, using
 * a function to transform the data into components.
 */
public abstract class Iterator2PanelBinding<T> extends
		IteratorBinding<T, Widget> implements Function<T, Widget> {
	private final HasWidgets addToContainer;

	public static <T> Iterator2PanelBinding<T> bind(final HasWidgets container,
			final Value<? extends Iterable<T>> values,
			final Function<T, ? extends Widget> factory) {
		return new Iterator2PanelBinding<T>(container, values) {

			@Override
			public Widget apply(final T input) {
				return factory.apply(input);
			}

		};

	}

	/**
	 * 
	 * @param container
	 *            should have a functional {@link Panel#add(Widget)}
	 * @param values
	 *            mutating list of values
	 */
	public Iterator2PanelBinding(final HasWidgets container,
			final Value<? extends Iterable<T>> values) {
		super(container, values);
		addToContainer = container;

	}

	@Override
	protected void addWidget(final Widget newWidget, final T val) {
		addToContainer.add(newWidget);
	}
}