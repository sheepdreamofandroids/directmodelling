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

import com.directmodelling.impl.TreelikeDomain;

/**
 * Convenience interface when you want to express explicitly that your variable
 * or function can enumerate its domain.
 */
public interface TreelikeValue<T> extends ObjectValue<T>, TreelikeDomain<T> {

	public interface Mutable<T> extends TreelikeValue<T>,
			ObjectValue.Mutable<T> {
	}
}
