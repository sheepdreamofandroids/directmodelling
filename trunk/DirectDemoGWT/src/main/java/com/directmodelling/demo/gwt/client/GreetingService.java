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
package com.directmodelling.demo.gwt.client;

import com.directmodelling.demo.shared.DemoModel;
import com.directmodelling.demo.shared.MyDoubleVar;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.stm.impl.VersionImpl;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

	void update(TransactionImpl t);

	public static class Init implements IsSerializable {
		public VersionImpl storage = null;
		public DemoModel model;
		public MyDoubleVar wtf;
	}

	Init getInitial();

	public static class MakeSerializable implements IsSerializable {
		// Value<?> value;
		// Number dummy1;

		// Storage dummy2;
		// ListVar<String> gwtDummy = new ListVar<String>() {
		// };

		// TransactionImpl dummy3;
		//
		// VersionImpl dummy4;
		//
		// DemoModel.MyDoubleVar dummy5;
	}

	MakeSerializable dummy(MakeSerializable dummy);

}
