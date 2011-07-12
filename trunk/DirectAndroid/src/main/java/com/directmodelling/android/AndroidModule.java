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

import com.directmodelling.api.Context;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Tracker;
import com.directmodelling.impl.EntityUtil;
import com.directmodelling.impl.SimpleContext;
import com.directmodelling.reflective.EntityInfo;
import com.google.inject.Binder;
import com.google.inject.Module;

public class AndroidModule implements Module {

	@Override
	public void configure(final Binder b) {
		b.bind(Context.class).to(SimpleContext.class);
		b.bind(Tracker.class).to(AndroidUpdateTracker.class);
		b.bind(EntityUtil.class).to(EntityInfo.class);
		b.requestStaticInjection(Updates.class);
	}

}
