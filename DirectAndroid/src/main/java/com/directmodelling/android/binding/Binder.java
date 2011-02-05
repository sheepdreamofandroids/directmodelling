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

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value.Mutable;
import android.util.Log;
import android.view.View;

public abstract class Binder<TView extends View, TMutableValue, TViewValue> implements Receiver {
	protected final Mutable<TMutableValue> m;
	protected final TView view;
	protected final Converter<TMutableValue, TViewValue> toView;
	protected final Converter<TViewValue, TMutableValue> fromView;
	protected TViewValue lastViewContents;
	protected TMutableValue lastValue;

	protected Binder(
		final Mutable<TMutableValue> m,
		final TView s,
		final Converter<TMutableValue, TViewValue> toView,
		final Converter<TViewValue, TMutableValue> toMutable) {
		this.m = m;
		this.view = s;
		this.toView = toView;
		this.fromView = toMutable;
		Updates.registerForChanges(this);
		valuesChanged();
	}

	@Override
	public void valuesChanged() {
		final TMutableValue newValue = m.getValue();
		if (!equals(newValue, lastValue)) {
			lastValue = newValue;
			try {
				setViewValue(toView.convert(lastValue), !equals(fromView.convert(getViewValue()), newValue));
			} catch (final Exception e) {
				Log.w("Setting view from value", e);
			}
		}
	}

	public void takeValueFromView() {
		final TViewValue v = getViewValue();
		if (!equals(v, lastViewContents)) {
			lastViewContents = v;
			try {
				final TMutableValue newMutableValue = fromView.convert(v);
				if (!equals(newMutableValue, m.getValue())) {
					m.setValue(newMutableValue);
				}
			} catch (final Exception e) {
				Log.w("Setting mutable from view", e);
			}
		}
	}

	abstract protected void setViewValue(TViewValue v, boolean force);

	abstract protected TViewValue getViewValue();

	private <Q> boolean equals(final Q a, final Q b) {
		return a == b || a != null && a.equals(b);
	}
}