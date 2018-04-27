package com.directmodelling.demo.angular.client;

import com.directmodelling.demo.angular.shared.PostcodeLookup.PostcodeLookupResult;
import com.directmodelling.gwt.sync.SyncService.MakeSerializable;
import com.google.gwt.user.client.rpc.IsSerializable;

public class FixSerialization extends MakeSerializable implements IsSerializable {
	public PostcodeLookupResult postcodeLookupResult;
}
