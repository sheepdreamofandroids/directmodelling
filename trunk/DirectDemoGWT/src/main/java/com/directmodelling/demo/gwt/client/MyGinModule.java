/*******************************************************************************
 * Copyright (c) 2010 Guus C. Bloemsma.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *******************************************************************************/
/**
 * 
 */
package com.directmodelling.demo.gwt.client;

import com.directmodelling.api.Updates;
import com.directmodelling.gwt.GWTUpdateTracker;
import com.google.gwt.inject.client.AbstractGinModule;

public class MyGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(DemoPanel.class).to(DemoPanel.class);
		bind(Updates.Tracker.class).to(GWTUpdateTracker.class);
		// bind(DemoPanel.class).toProvider(FactoryProvider.newFactory(DemoPanel.Factory.class,
		// DemoPanel.class));
		requestStaticInjection(Updates.class);
	}
}