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
 * typical GUI might render this invisible. Reading and writing could fail.</dd>
 * <dt>pending</dt>
 * <dd>This data is being calculated right now. The result will be available
 * soon and an update will be issued to notify you. The result of get() and
 * getValue() will be outdated. A GUI could show a progress bar or throbber.</dd>
 * <dt>readonly</dt>
 * <dd>This data is calculated. The get() or getValue() methods will return
 * current data. Set() or setValue() might fail.</dd>
 * <dt>suspect</dt>
 * <dd>Like readonly but at least one input is invalid or wrong.</dd>
 * <dt>writeable</dt>
 * <dd>Readable and writable. The current value is valid.</dd>
 * <dt>invalid</dt>
 * <dd>A read/write field with an invalid value.</dd>
 * </dl>
 */
public interface Status {
	public class Default implements Status {

		public final boolean enabled;

		public Default(final boolean enabled) {
			this.enabled = enabled;
		}

		@Override
		public boolean isEnabled() {
			return enabled;
		}

		@Override
		public Status unlessFrom(final Object possiblyHasStatus) {
			return possiblyHasStatus instanceof HasStatus ? ((HasStatus) possiblyHasStatus).status() : this;
		}

	}

	/** A hint for the GUI whether to show this status as enabled. */
	boolean isEnabled();

	/**
	 * If possiblyHasStatus implements {@link HasStatus}, possiblyHasStatus's
	 * {@link #status()} will be returned, otherwise this. Use like
	 * {@link Status#writeable}.{@link #unlessFrom(o)}.
	 */
	Status unlessFrom(Object possiblyHasStatus);

	/**
	 * Currently this data is meaningless. Treat it as if it didn't exist. A
	 * typical GUI might hide this. Reading and writing could fail.
	 */
	Status irrelevant = new Default(false);
	/**
	 * This data is being calculated right now. The result will be available
	 * soon and an update will be issued to notify you. The result of get() and
	 * getValue() will be outdated. A GUI could show a progress bar or throbber.
	 */
	Status pending = new Default(false);
	/**
	 * This data is calculated. The get() or getValue() methods will return
	 * current data. Set() or setValue() might fail.
	 */
	Status readonly = new Default(false);
	/** Like readonly but at least one input is invalid or wrong. */
	Status suspect = new Default(false);
	/** Readable and writable. The current value is valid. */
	Status writeable = new Default(true);
	/**
	 * Readable and writable. The current value is not valid and needs to be
	 * corrected.
	 */
	Status invalid = new Default(true);

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
