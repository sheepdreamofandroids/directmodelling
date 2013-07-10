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

import org.junit.Assert;
import org.junit.Test;

import com.directmodelling.api.IntValue.Mutable;
import com.directmodelling.impl.IntVar;
import com.directmodelling.stm.impl.TransactionImpl;

public class TransactionImplTest extends DirectTestBase {
	@Test
	public void testStorage() {
		final TransactionImpl parent = new TransactionImpl(null);
		final Mutable v = new IntVar();
		parent.set(v, 5);
		assertEquals(5, (int) parent.get(v));
		final TransactionImpl child1 = parent.createChild();
		final TransactionImpl child2 = parent.createChild();
		final TransactionImpl child3 = parent.createChild();

		// child 1
		assertEquals(5, (int) child1.get(v));
		child1.set(v, 10);

		// child 2
		child2.set(v, 20);

		// child 3
		assertEquals(5, (int) child3.get(v));
		child3.set(v, 30);

		assertEquals(5, (int) parent.get(v));
		child1.commitTo(parent);
		child2.commitTo(parent);
		try {
			child3.commitTo(parent);
		} catch (final Exception e) {
			assertEquals(10, (int) child1.get(v));
			assertEquals(20, (int) child2.get(v));
			assertEquals(20, (int) parent.get(v));
			return;
		}
		Assert.fail("child 3 should not have been accepted");
	}
}
