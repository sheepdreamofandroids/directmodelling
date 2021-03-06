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
/**
 * 
 */
package com.directmodelling.stm.impl;

import com.directmodelling.api.HasKey;

public class UninitializedException extends RuntimeException {
	private static final long serialVersionUID = 7526415871454438039L;
	private final Object var;

	public UninitializedException(final Object v) {
		this.var = v;
	}

	public Object getVar() {
		return var;
	}

	@Override
	public String getMessage() {
		final StringBuilder sb = new StringBuilder(
				"Reading uninitialized value in ");
		sb.append(var.getClass().getName());
		if (var instanceof HasKey) {
			sb.append(" with key ");
			sb.append(((HasKey) var).getKey());
		}
		sb.append(var);
		return sb.toString();
	}
}