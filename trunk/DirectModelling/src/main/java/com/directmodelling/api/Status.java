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
 * States that an entity or a value of an entity can be in. In order of
 * increasing urgency:
 * <dl>
 * <dt>irrelevant</dt>
 * <dd>Currently this data is meaningless. Treat it as if it didn't exist. A
 * typical GUI would render this invisible. Reading and writing could fail.</dd>
 * <dt>pending</dt>
 * <dd>This data is being calculated right now. The result will be available
 * soon and an update will be issued to notify you. The result of get() and
 * getValue() will be outdated. A GUI could show a progress bar or throbber.</dd>
 * <dt>readonly</dt>
 * <dd>This data is calculated. The get() or getValue() methods will return
 * meaningful results. set() and setValue() could fail.</dd>
 * <dt>writeable</dt>
 * <dd>A normal read/write field. The current value is valid.</dd>
 * <dt>invalid</dt>
 * <dd>A read/write field with an invalid value.</dd>
 * </dl>
 */
public enum Status {
	/**
	 * The order somewhat indicates the urgency for the user to look at this
	 * field.
	 */
	irrelevant(false), pending(false), readonly(false), writeable(true), invalid(true);

	private Status(final boolean enabled) {
		this.enabled = enabled;
	}

	public final boolean enabled;

	/**
	 * In practice states like enabled, hidden, invalid etc. are usually
	 * interdependent. Invalid and disabled doesn't make sense, nor does pending
	 * and hidden. Therefore the calculation of these states are quite similar
	 * and calculating them separately would lead to code duplication. That's
	 * why you can calculate them all at once
	 * 
	 * @author guus
	 * 
	 */
	public interface HasStatus {
		Status status();
	}

	// TODO have some kind of 'reason' that can be presented to the user
	// somehow.

}
