package com.directmodelling.demo.angular.server;

import java.util.concurrent.Callable;

import com.directmodelling.demo.angular.shared.PostcodeLookup.PostcodeLookupResult;
import com.google.common.base.Function;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

public class PostcodeLookupServerImpl implements
		Function<String, ListenableFuture<PostcodeLookupResult>> {
	// {PostcodeLookup.impl.

	@Override
	public ListenableFuture<PostcodeLookupResult> apply(final String zip) {
		return ListenableFutureTask
				.create(new Callable<PostcodeLookupResult>() {

					@Override
					public PostcodeLookupResult call() throws Exception {
						try {
							Thread.sleep(5000);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
						final int minHuisNr = Integer.parseInt(zip.substring(0,
								3));
						return new PostcodeLookupResult(zip + "straat", zip
								+ "stad", minHuisNr, minHuisNr + 10);
					}
				});
	}

}
