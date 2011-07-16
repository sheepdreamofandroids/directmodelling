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
package com.directmodelling.android.binding;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Status;
import com.directmodelling.api.Status.HasStatus;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;

public abstract class Binder<TView, TMutableValue, TViewValue> implements Receiver {
	protected final Mutable<TMutableValue> mutable;
	protected final Value<TMutableValue> value;
	protected final HasStatus hasStatus;
	protected final TView view;
	protected final Converter<TMutableValue, TViewValue> toView;
	protected final Converter<TViewValue, TMutableValue> fromView;
	protected TViewValue lastViewContents;
	protected TMutableValue lastValue;

	private static Map<Object, Binder<?, ?, ?>> bound = new HashMap<Object, Binder<?, ?, ?>>();

	// TODO keep track of bound binders and remove old ones when rebinding?

	// TODO this binder is completely Android agnostic (apart from logging),
	// move to directmodelling?

	// FIXME binders should be held by a weak reference to avoid leaking views

	// TODO separate in a baseclass that binds the status when available and
	// derive this one

	// protected Binder(final Mutable<TMutableValue> m, final TView s, final
	// Converter<TMutableValue, TViewValue> toView,
	// final Converter<TViewValue, TMutableValue> toMutable) {
	// this(m, m, (HasStatus) (m instanceof HasStatus ? m : null), s, toView,
	// toMutable);
	// }

	protected Binder(final Value<TMutableValue> v, final TView s, final Converter<TMutableValue, TViewValue> toView,
					final Converter<TViewValue, TMutableValue> toMutable) {
		this((Mutable<TMutableValue>) (v instanceof Mutable ? v : null), v, (HasStatus) (v instanceof HasStatus ? v
						: null), s, toView, toMutable);
	}

	protected Binder(HasStatus hs, final TView s) {
		this(null, null, hs, s, null, null);
	}

	protected Binder(final Mutable<TMutableValue> m, Value<TMutableValue> value, HasStatus hs, final TView s,
					final Converter<TMutableValue, TViewValue> toView,
					final Converter<TViewValue, TMutableValue> toMutable) {
		this.mutable = m;
		this.value = value;
		hasStatus = hs;
		this.view = s;
		this.toView = toView;
		this.fromView = toMutable;
		Updates.registerForChanges(this);
		Binder<?, ?, ?> previousBinder = bound.get(s);
		if (null != previousBinder)
			previousBinder.unbind();
		bound.put(s, this);
		valuesChanged();
	}

	public void unbind() {
		Updates.unregister(this);
	};

	@Override
	public void valuesChanged() {
		if (null != mutable) {
			final TMutableValue newValue = mutable.getValue();
			if (!equals(newValue, lastValue)) {
				lastValue = newValue;
				boolean viewIsCorrect = false;
				try {
					viewIsCorrect = equals(fromView.convert(getViewValue()), newValue);
				} catch (Exception e1) {
					// cannot convert view contents back to value, must be wrong
				}
				try {
					// when the current data is wrong, correct it, even while
					// the
					// user is editing
					// if it is in a slightly different format (e.g. 10.0
					// instead of
					// 10), leave it
					setViewValue(toView.convert(lastValue), !viewIsCorrect);
				} catch (final Exception e) {
					Log.w("Setting view from value", e);
				}
			}
		}

		if (hasStatus != null) {
			Status status = hasStatus.status();
			setViewEnabled(status.enabled);
		}
	}

	public void takeValueFromView() {
		final TViewValue v = getViewValue();
		if (!equals(v, lastViewContents)) {
			lastViewContents = v;
			try {
				final TMutableValue newMutableValue = fromView.convert(v);
				if (!equals(newMutableValue, mutable.getValue())) {
					mutable.setValue(newMutableValue);
				}
			} catch (final Exception e) {
				Log.w("Setting mutable from view", e);
			}
		}
	}

	abstract protected void setViewEnabled(boolean b);

	abstract protected void setViewValue(TViewValue v, boolean force);

	abstract protected TViewValue getViewValue();

	private <Q> boolean equals(final Q a, final Q b) {
		return a == b || a != null && a.equals(b);
	}
}