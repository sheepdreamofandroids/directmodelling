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

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.js.JsType;

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
@JsType
// to export toString()
public interface Status {
	public class Default implements Status {

		public final boolean enabled;
		private String name;

		public Default(final boolean enabled, final String name) {
			this.enabled = enabled;
			this.name = name;
		}

		@Override
		public boolean isEnabled() {
			return enabled;
		}

		@Override
		public Status unlessFrom(final Object possiblyHasStatus) {
			return possiblyHasStatus instanceof HasStatus ? ((HasStatus) possiblyHasStatus)
					.status() : this;
		}

		public static Status statusFrom(final Object possiblyHasStatus,
				final Invalid fallback) {
			return possiblyHasStatus instanceof HasStatus ? ((HasStatus) possiblyHasStatus)
					.status() : fallback;
		}

		@Override
		public String toString() {
			return name;
		}

		@Override
		public boolean isValid() {
			return true;
		}
	}

	/** A hint for the GUI whether to show this status as enabled. */
	boolean isEnabled();

	/**
	 * Valid means that the user did NOT make a mistake. @see #suspect for
	 * machine-made errors.
	 */
	boolean isValid();

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
	Status irrelevant = new Default(false, "Irrelevant");
	/**
	 * This data is being calculated right now. The result will be available
	 * soon and an update will be issued to notify you. The result of get() and
	 * getValue() will be outdated. A GUI could show a progress bar or throbber.
	 */
	Status pending = new Default(false, "Pending");
	/**
	 * This data is calculated. The get() or getValue() methods will return
	 * current data. Set() or setValue() might fail.
	 */
	Status readonly = new Default(false, "ReadOnly");
	/**
	 * Like readonly but at least one input is invalid or wrong. This means a
	 * machine mistake, NOT a user mistake.
	 */
	Status suspect = new Default(false, "Suspect");
	/** Readable and writable. The current value is valid. */
	Status writeable = new Default(true, "Writeable");

	public static class Invalid extends RuntimeException implements Status,
			HasArguments {
		public static class Format extends Invalid {

		}

		protected Invalid(final Throwable t) {
			super(t);
		}

		protected Invalid() {
		}

		public static class TooLow extends Invalid {
			public TooLow(final Object minimum) {
				withArgument("minimum", minimum);
			}
		}

		public static class TooHigh extends Invalid {
			public TooHigh(final Object maximum) {
				withArgument("maximum", maximum);
			}
		}

		public static class Failure extends Invalid {
			public Failure(final Throwable t) {
				super(t);
			}
		}

		/** A required value is missing. */
		public static class Missing extends Invalid {
		}

		Map<String, Object> arguments = null;

		/** @return true. How would you correct the problem otherwise? */
		@Override
		public boolean isEnabled() {
			return true;
		}

		public Invalid withArgument(final String key, final Object value) {
			if (arguments == null)
				arguments = new HashMap<String, Object>();
			arguments.put(key, value);
			return this;
		}

		@Override
		public Status unlessFrom(final Object possiblyHasStatus) {
			return Default.statusFrom(possiblyHasStatus, this);
		}

		public static Invalid tooHigh(final Object maximum) {
			return new TooHigh(maximum);
		}

		public static Invalid tooLow(final Object minimum) {
			return new TooLow(minimum);
		}

		@Override
		public Object getArgument(final String key) {
			return arguments == null ? null : arguments.get(key);
		}

		@Override
		public String toString() {
			return getClass().getName()
					+ (getCause() == null ? "" : " because "
							+ getCause().getMessage());
		}

		@Override
		public boolean isValid() {
			return false;
		}
	}

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
