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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;

public final class TextViewBinder<T> extends Binder<TextView, T, String>
	implements
	Receiver,
	TextWatcher,
	OnFocusChangeListener {

	public TextViewBinder(
		final Mutable<T> m,
		final TextView s,
		final Converter<T, String> toInt,
		final Converter<String, T> fromInt) {
		super(m, s, toInt, fromInt);
		s.addTextChangedListener(this);
		s.setOnFocusChangeListener(this);
	}

	@Override
	public void afterTextChanged(final Editable e) {
		takeValueFromView();
	}

	@Override
	public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
		// ignore
	}

	@Override
	public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
		// ignore
	}

	@Override
	protected void setViewValue(final String v, boolean force) {
		if (force || !view.hasFocus())
			view.setText(v);
	}

	@Override
	public void onFocusChange(final View v, final boolean hasFocus) {
		if (!hasFocus && v == view) {
			takeValueFromView();
		}
	}

	@Override
	protected String getViewValue() {
		return view.getText().toString();
	}
}