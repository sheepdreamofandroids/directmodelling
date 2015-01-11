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

import com.directmodelling.impl.IntVar;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Version;
import com.directmodelling.stm.impl.VersionImpl;

public class VersionImplTest extends DirectTestBase {
	@Test
	public void testStorage() {
		final VersionImpl parent = new VersionImpl(null);
		Storage.Util.current.init(parent);
		final IntVar v = new IntVar();
		parent.set(v, 5);
		assertEquals(5, (long) parent.get(v));
		final Version child1 = parent.createChild();
		assertEquals(5, (long) child1.get(v));
		child1.set(v, 10);
		final Version child2 = parent.createChild();
		assertEquals(5, (long) child2.get(v));
		child2.set(v, 20);
		assertEquals(10, (int) child1.get(v));
		assertEquals(20, (long) child2.get(v));
		assertEquals(5, (long) parent.get(v));
	}
}
