package com.directmodelling.demo.angular.server;

import java.util.Set;

import com.directmodelling.api.ID;
import com.directmodelling.demo.angular.client.FixSerialization;
import com.directmodelling.demo.angular.client.MySyncService;
import com.directmodelling.server.SyncServiceImpl;
import com.directmodelling.stm.Version;
import com.directmodelling.stm.impl.TransactionImpl;

public class MySyncServiceImpl extends SyncServiceImpl implements MySyncService {
	public MySyncServiceImpl() {
		super(new ServerInitializer());
	}

	@Override
	public FixSerialization dummy(FixSerialization f) {
		return f;
	}

	@Override
	public MakeSerializable dummy(MakeSerializable dummy) {
		// TODO Auto-generated method stub
		return super.dummy(dummy);
	}

	@Override
	public Version initial() {
		// TODO Auto-generated method stub
		return super.initial();
	}

	@Override
	public TransactionImpl longPoll() {
		// TODO Auto-generated method stub
		return super.longPoll();
	}

	@Override
	public TransactionImpl update(TransactionImpl t, Set<ID> toCalc) {
		// TODO Auto-generated method stub
		return super.update(t, toCalc);
	}
}
