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
package com.directmodelling.demo.angular.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.directmodelling.api.Updates;
import com.directmodelling.demo.angular.client.GreetingService;
import com.directmodelling.demo.angular.client.IInit;
import com.directmodelling.demo.angular.client.Init;
import com.directmodelling.demo.shared.DemoModel;
import com.directmodelling.impl.ExplicitUpdatesTracker;
import com.directmodelling.impl.SimpleContext;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.stm.impl.VersionImpl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	public VersionImpl serverWorld = new VersionImpl(null);
	{
		Storage.Util.current = new SimpleContext<Storage>(serverWorld);
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
	public IInit getInitial() {
		// TODO Auto-generated method stub
		final Init init = new Init();
		init.storage = serverWorld;
		init.model = model;
		// init.calculator = new Calculator();

		perThreadResponse.get().addHeader("Access-Control-Allow-Origin", "*");
		return init;
	}

	@Override
	protected void doOptions(final HttpServletRequest req,
			final HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doOptions(req, res);
	}
}
