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
package com.directmodelling.android;

import java.util.List;

import roboguice.application.RoboApplication;

import com.directmodelling.impl.SimpleContext;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.impl.VersionImpl;
import com.google.inject.Module;

/**
 * Subclass this for your own application, or use this directly. If neither is
 * acceptable, add {@link AndroidModule} yourself.
 */
public class DirectAndroidApplication extends RoboApplication {
	@Override
	protected void addApplicationModules(final List<Module> modules) {
		Storage.Util.current = new SimpleContext<Storage>(new VersionImpl());// FIXME
		modules.add(new AndroidModule());
		super.addApplicationModules(modules);
	}

	@Override
	protected com.google.inject.Injector createInjector() {
		return super.createInjector();
	};

}
