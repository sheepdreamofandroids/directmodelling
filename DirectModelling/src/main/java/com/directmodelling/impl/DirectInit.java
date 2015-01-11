package com.directmodelling.impl;

import com.directmodelling.api.Context;
import com.directmodelling.api.Context.Factory;
import com.directmodelling.api.ID;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Tracker;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.stm.impl.VersionImpl;

public abstract class DirectInit {

	public final VersionImpl baseValues = new VersionImpl();
	public final TransactionImpl changes = new TransactionImpl(baseValues);
	{
		Context.SESSION.init(sessionContext());// TODO use session
		Context.GLOBAL.init(Context.Factory.Default.INSTANCE);
		ID.generator.init(SimpleIDGenerator.INSTANCE);
		Storage.Util.current.init(changes);
		Updates.tracker = updatesTracker();
	}

	protected Context.Factory sessionContext() {
		return Factory.Default.INSTANCE;
	}

	protected abstract Tracker updatesTracker();

}
