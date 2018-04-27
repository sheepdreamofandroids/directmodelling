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
package com.directmodelling.gwt.sync;

import java.util.Set;

import com.directmodelling.api.ID;
import com.directmodelling.gwt.sync.SyncService.MakeSerializable;
import com.directmodelling.stm.Version;
import com.directmodelling.stm.impl.TransactionImpl;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client side stub for the RPC service.
 * @param <MakeSerializable>
 */
public interface SyncServiceAsync {
	
	void initial(AsyncCallback<Version> callback);

	void update(TransactionImpl t, Set<ID> requestedValues,
			AsyncCallback<TransactionImpl> callback);

	void longPoll(AsyncCallback<TransactionImpl> callback);


	void dummy(MakeSerializable dummy, AsyncCallback<MakeSerializable> callback);

}
