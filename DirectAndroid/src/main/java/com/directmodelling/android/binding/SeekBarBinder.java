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
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value.Mutable;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public final class SeekBarBinder<T> extends Binder<SeekBar, T, Integer> implements OnSeekBarChangeListener, Receiver {

	SeekBarBinder(
		final Mutable<T> m,
		final SeekBar s,
		final Converter<T, Integer> toInt,
		final Converter<Integer, T> fromInt) {
		super(m, s, toInt, fromInt);
		s.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
		if (fromUser) {
			takeValueFromView();
		}
	}

	@Override
	public void onStartTrackingTouch(final SeekBar seekBar) {
		// ignore
	}

	@Override
	public void onStopTrackingTouch(final SeekBar seekBar) {
		// ignore
	}

	@Override
	protected void setViewValue(final Integer v, boolean force) {
		view.setProgress(v);
	}

	@Override
	protected Integer getViewValue() {
		return view.getProgress();
	}
}