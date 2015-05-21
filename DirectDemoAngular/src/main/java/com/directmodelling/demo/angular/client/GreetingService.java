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
package com.directmodelling.demo.angular.client;

import java.util.Set;

import com.directmodelling.api.ID;
import com.directmodelling.demo.angular.shared.PostcodeLookup.PostcodeLookupResult;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.synchronization.IsCalculating;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

	TransactionImpl update(TransactionImpl t, Set<ID> toCalc);

	TransactionImpl longPoll();

	Init getInitial();

	/**
	 * GWT serialization only generates serialization code for those types that
	 * are statically known to be serialized. This is only fully known for data
	 * sent from client to server since the compiler sees all code and there is
	 * no reflection. Sending other types (server to client) will result in
	 * runtime errors. To avoid such runtime errors, add such types as fields
	 * here to let the GWT compiler know.
	 * 
	 * @author BloemsmaGC
	 *
	 */
	public static class MakeSerializable implements IsSerializable {
		public ID id;
		public PostcodeLookupResult result;
		IsCalculating isCalculating;
	}

	MakeSerializable dummy(MakeSerializable dummy);

}
