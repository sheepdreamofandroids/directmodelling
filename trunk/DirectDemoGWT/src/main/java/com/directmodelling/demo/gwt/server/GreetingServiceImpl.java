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
package com.directmodelling.demo.gwt.server;

import com.directmodelling.api.Updates;
import com.directmodelling.demo.gwt.client.GreetingService;
import com.directmodelling.demo.shared.DemoModel;
import com.directmodelling.impl.ExplicitUpdatesTracker;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Version;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.stm.impl.VersionImpl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	public Version serverWorld = new VersionImpl(null);
	{
		Storage.Util.current.init(serverWorld);
		Updates.tracker = new ExplicitUpdatesTracker();
	}
	DemoModel model = new DemoModel();

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(final String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public void update(final TransactionImpl t) {
		t.commitTo(serverWorld);
	}

	@Override
	public MakeSerializable dummy(final MakeSerializable dummy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Init getInitial() {
		// TODO Auto-generated method stub
		final Init init = new Init();
		init.storage = serverWorld;
		init.model = model;
		return init;
	}

}
