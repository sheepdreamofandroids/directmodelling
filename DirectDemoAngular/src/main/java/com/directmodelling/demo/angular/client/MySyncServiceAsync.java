package com.directmodelling.demo.angular.client;

import com.directmodelling.gwt.sync.SyncServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MySyncServiceAsync extends SyncServiceAsync {

	void dummy(FixSerialization f, AsyncCallback<FixSerialization> callback);


}
