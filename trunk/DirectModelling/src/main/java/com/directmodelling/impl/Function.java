package com.directmodelling.impl;

import com.directmodelling.api.Status;
import com.directmodelling.api.Status.HasStatus;
import com.directmodelling.api.Value;

public abstract class Function<T> implements Value<T>, HasStatus {
	/**
	 * @return <dl>
	 *         <dt>readonly</dt>
	 *         <dd>per default</dd>
	 *         <dt>pending</dt>
	 *         <dd>while fetching asynchronously</dd>
	 *         <dt>invalid</dt>
	 *         <dd>when based on invalid data</dd>
	 */
	@Override
	public Status status() {
		return Status.readonly;
	}

	// TODO write implementations for all the primitives
}
