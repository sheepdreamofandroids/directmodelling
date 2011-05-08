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
package com.directmodelling.stm;

import java.io.Serializable;

import com.directmodelling.api.Context;
import com.directmodelling.api.Value;
import com.directmodelling.impl.EntityInfo;
import com.directmodelling.impl.SimpleContext;
import com.google.inject.Inject;

public interface Storage extends Serializable {
	public static abstract class Util {

		@Inject
		public static Context<Storage> current = new SimpleContext<Storage>(null);
	}

	<T> T get(Value<T> v);

	<T> void set(Value.Mutable<T> v, T val);

	public abstract void bind(Object bean);

	public interface HasStorage {
		Storage getStorage();

		void setStorage(Storage s);
	}

	public abstract class AbstractStorage implements Storage {
		@Override
		public void bind(Object bean) {
			for (Value<?> value : EntityInfo.allProperties(bean)) {
				if (value instanceof HasStorage)
					((HasStorage) value).setStorage(this);
			}
		}
	}

	// boolean get(BooleanValue.Modifiable v);
	//
	// void set(BooleanValue.Modifiable v, boolean val);
	//
	// int get(IntValue.Modifiable v);
	//
	// void set(IntValue.Modifiable v, int val);
	//
	// double get(DoubleValue.Modifiable v);
	//
	// void set(DoubleValue.Modifiable v, double val);

}
