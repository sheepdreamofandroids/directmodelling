package com.directmodelling.demo.angular.server;

import com.directmodelling.demo.angular.shared.PostcodeLookup.PostcodeLookupResult;
import com.directmodelling.synchronization.RemoteFunction;
import com.directmodelling.synchronization.RemoteFunction.Impl;
import com.directmodelling.synchronization.RemoteFunction.Impl.ResultCallback;

public class PostcodeLookupServerImpl implements
		Impl.AsyncFunction<String, PostcodeLookupResult> {

	@Override
	public void apply(RemoteFunction<String, PostcodeLookupResult> requester,
			final String zip, final ResultCallback<String, PostcodeLookupResult> callback) {
		new Thread() {
			@Override
			public void run() {
				int minHuisNr = 666;
				try {
					Thread.sleep(500);
					minHuisNr = Integer.parseInt(zip.substring(0, 3));
				} catch (final InterruptedException e) {
					e.printStackTrace();
				} catch (NumberFormatException
						| StringIndexOutOfBoundsException e) {
					minHuisNr = 0;
				} finally {
					callback.result(zip,
							new PostcodeLookupResult(zip + "straat", zip
									+ "stad", minHuisNr, minHuisNr + 10));
				}
			};
		}.start();
	}

}
