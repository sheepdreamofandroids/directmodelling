package com.directmodelling.demo.angular.client;

import com.directmodelling.gwt.sync.SyncService;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("sync")
public interface MySyncService extends SyncService, RemoteService {
public FixSerialization dummy(FixSerialization f);
}
