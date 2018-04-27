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
package test.com.modelling.stm.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.directmodelling.api.IntValue.Mutable;
import com.directmodelling.api.Updates;
import com.directmodelling.impl.ExplicitUpdatesTracker;
import com.directmodelling.impl.IntVar;
import com.directmodelling.test.DirectTestBase;

public class EntityTest extends DirectTestBase {
	interface Simple {
		Mutable inty();
	}

	static class SimpleEntity implements Simple {

		private static final Mutable myInty = new IntVar();

		@Override
		public Mutable inty() {
			return myInty;
		}

	}

	@Test
	public void testSimpleEntity() {
		init.init();
		Updates.tracker = new ExplicitUpdatesTracker();
		final SimpleEntity se = new SimpleEntity();
		se.inty().set(15);
		assertEquals(15, se.inty().getAsInt());
	}
}
