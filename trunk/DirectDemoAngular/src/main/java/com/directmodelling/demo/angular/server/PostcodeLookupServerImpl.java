package com.directmodelling.demo.angular.server;

import com.directmodelling.api.ID;
import com.directmodelling.demo.angular.shared.PostcodeLookup.PostcodeLookupResult;

public class PostcodeLookupServerImpl extends
		RemoteServerImpl<String, PostcodeLookupResult> {

	public PostcodeLookupServerImpl(ID id) {
		super(id);
	}

	@Override
	protected void calculate(final String zip) {
		new Thread() {
			@Override
			public void run() {
				int minHuisNr = 666;
				try {
					Thread.sleep(500);
					minHuisNr = Integer.parseInt(zip.substring(0, 3));
				} catch (final InterruptedException e) {
					e.printStackTrace();
				} catch (NumberFormatException nfe) {
					minHuisNr = 0;
				} finally {
					setResult(zip, new PostcodeLookupResult(zip + "straat", zip
							+ "stad", minHuisNr, minHuisNr + 10));
				}
			};
		}.start();
	}

}
