package com.directmodelling.demo.angular.server;

import javax.servlet.http.HttpSession;

import com.directmodelling.api.Context;
import com.directmodelling.impl.SimpleContext;
import com.directmodelling.impl.SingleAssignContext;
import com.google.common.base.Supplier;

public class SessionContextFactory implements Context.Factory {
	private SessionContextFactory() {
	};

	private static final String CLASS_NAME = SessionContextFactory.class
			.getName();

	private static long counter = 0;

	@Override
	public <T> Context<T> create(Supplier<T> supplier) {
		// TODO Auto-generated method stub
		return new Context<T>() {
			protected final String key = CLASS_NAME + ++counter;

			@Override
			public T it() {
				HttpSession session = GreetingServiceImpl.REQUEST.get()
						.getSession(true);
				@SuppressWarnings("unchecked")
				T attribute = (T) session.getAttribute(key);
				if (attribute == null) {
					attribute = supplier.get();
					session.setAttribute(key, attribute);
				}
				return attribute;
			}
		};
	}

	@Override
	public <T> Context<T> constant(T initialValue) {
		return new SimpleContext<T>(initialValue);
	}

	@Override
	public <T> SingleAssignContext<T> create() {
		return new SingleAssignContext<>();
	}

}