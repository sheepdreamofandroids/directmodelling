package com.directmodelling.collections.operators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.directmodelling.collections.List.ListReplace;
import com.directmodelling.collections.ListPlayer;
import com.directmodelling.collections.ListRecorder;
import com.directmodelling.impl.DirectTestInit;

public class AppendTest extends DirectTestInit {
	{
		init();
	}
	ListRecorder<String> a = new ListRecorder<>();
	ListRecorder<String> b = new ListRecorder<>();
	ListRecorder<String> c = new ListRecorder<>();
	Append<String> conc = new Append<>(a, b, c);
	ListPlayer<String> play = new ListPlayer<>(conc);

	@Test
	public void goModify() {
		ListReplace<String> lastDelta = conc.getLastDelta();
		assertNotNull(lastDelta);
		assertNull(lastDelta.getNext());
		check();
		b.add("reactive");
		check("reactive");
		c.add("world");
		check("reactive", "world");
		a.add("hello");
		check("hello", "reactive", "world");
	}

	private void check(String... elms) {
		assertEquals(elms.length, conc.size());
		int i = 0;
		for (String string : conc) {
			assertEquals(elms[i++], string);
		}
		for (String string : play) {
			assertEquals(elms[i++], string);
		}
	}

}
