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
import java.util.Map;

import com.directmodelling.api.Context;
import com.directmodelling.api.Value;
import com.directmodelling.impl.EntityUtil;
import com.directmodelling.impl.SimpleContext;

public interface Storage extends Serializable {
	public static abstract class Util {

		public static Context<Storage> current = new SimpleContext<Storage>(
				null);
	}

	<T> T get(Value<T> v);

	<T> void set(Value.Mutable<T> v, T val);

	public abstract void bind(Object bean);

	public abstract void bindProperty(Value<?> value);

	public interface HasStorage {
		Storage getStorage();

		void setStorage(Storage s);
	}

	public void addValues(Map<Value.Mutable<?>, Object> values);

	public abstract class AbstractStorage implements Storage {
		EntityUtil entityInfo;

		@Override
		public void bind(final Object bean) {
			entityInfo.store(bean, this);
		}
	}

	// boolean get(BooleanValue.Mutable v);
	//
	// void set(BooleanValue.Mutable v, boolean val);
	//
	// int get(IntValue.Mutable v);
	//
	// void set(IntValue.Mutable v, int val);
	//
	// double get(DoubleValue.Mutable v);
	//
	// void set(DoubleValue.Mutable v, double val);

}
