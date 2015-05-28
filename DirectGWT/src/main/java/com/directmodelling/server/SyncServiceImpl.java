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
package com.directmodelling.server;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.directmodelling.api.ID;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.gwt.sync.SyncService;
import com.directmodelling.impl.DirectInit;
import com.directmodelling.stm.Version;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.synchronization.RemoteServerImpl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SyncServiceImpl extends RemoteServiceServlet implements
		SyncService {

	public SyncServiceImpl(DirectInit initializer) {
		this.initializer = initializer;
		initializer.init();
	}

	/** attribute name of longpoll thread in session */
	private static final String SESSION_THREAD = SyncServiceImpl.class
			.getName() + ".thread";
	// Map<Object, Object> postcodes = new MapRecorder<Object, Object>();
	final DirectInit initializer;

	// DemoModel model = new DemoModel();
	// public Version serverValues = initializer.baseValues;
	// public TransactionImpl serverChanges = initializer.changes;
	// PostcodeLookupServerImpl postcodeLookupImpl = new
	// PostcodeLookupServerImpl();
	// {
	// final ObjectVar<HashMap<String, PostcodeLookupResult>> postcodes = new
	// ObjectVar<>(
	// new HashMap<String, PostcodeLookupResult>());
	// PostcodeLookup.impl.init(postcodeLookupImpl);
	// }

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
	public TransactionImpl update(final TransactionImpl t, final Set<ID> toCalc) {
		t.commitTo(initializer.changes);
		initializer.changes.commitTo(initializer.baseValues);
		// TODO, try opposite order?
		initializer.changes.reset();
		RemoteServerImpl.calculateAll(toCalc);// might cause new changes
		TransactionImpl result = new TransactionImpl();
		initializer.changes.moveTo(result);
		initializer.changes.commitTo(initializer.baseValues);
		initializer.changes.reset();
		return result;
	}

	@Override
	public MakeSerializable dummy(final MakeSerializable dummy) {
		// TODO Auto-generated method stub
		// dummy.id = ID.generator.it().createID();
		return dummy;
	}

	@Override
	public Version initial() {
		// TODO use update() instead?
		// final Init init = new Init();
		// new PostcodeDemo();
		initializer.changes.commitTo(initializer.baseValues);
		// init.storage = initializer.baseValues;
		// init.model = model;
		// init.postcodeCacheID = postcodeLookupImpl.id();
		// init.postcodeDemo = new PostcodeDemo();
		// init.calculator = new Calculator();

		perThreadResponse.get().addHeader("Access-Control-Allow-Origin", "*");
		return initializer.baseValues;
	}

	@Override
	protected void doOptions(final HttpServletRequest req,
			final HttpServletResponse res) throws ServletException, IOException {
		super.doOptions(req, res);
	}

	@Override
	public TransactionImpl longPoll() {
		// TODO make async
		HttpSession session = perThreadRequest.get().getSession(true);
		session.setAttribute(SESSION_THREAD, Thread.currentThread());
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
		} finally {
			session.removeAttribute(SESSION_THREAD);
		}
		TransactionImpl result = new TransactionImpl();
		initializer.changes.moveTo(result);
		initializer.changes.commitTo(initializer.baseValues);
		initializer.changes.reset();
		return result;
	}

	{
		Updates.registerForChanges(new Receiver() {

			@Override
			public void valuesChanged() {
				HttpSession session = SESSION.get();
				if (session != null) {
					Thread longPollThread = (Thread) session
							.getAttribute(SESSION_THREAD);
					if (longPollThread != null)
						longPollThread.interrupt();
				}
			}
		});
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		REQUEST.set(arg0);
		RESPONSE.set(arg1);
		SESSION.set(arg0.getSession(true));
		try {
			super.service(arg0, arg1);
		} finally {
			REQUEST.set(null);
			RESPONSE.set(null);
			SESSION.set(null);
		}
	}

	public static final ThreadLocal<HttpSession> SESSION = new InheritableThreadLocal<>();
	public static final ThreadLocal<HttpServletRequest> REQUEST = new InheritableThreadLocal<>();
	public static final ThreadLocal<HttpServletResponse> RESPONSE = new InheritableThreadLocal<>();

}
