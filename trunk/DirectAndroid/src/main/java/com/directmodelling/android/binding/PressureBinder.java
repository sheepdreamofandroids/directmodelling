package com.directmodelling.android.binding;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Value.Mutable;

/**
 * Binds the pressure on any view to a mutable.
 * 
 * @author guus
 * 
 */
public final class PressureBinder<TMutableValue> extends Binder<View, TMutableValue, Float> {
	private final Mutable<TMutableValue> mutable;
	{
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mutable.setValue(fromView.convert(event.getPressure()));
				return false;
			}
		});
	}

	PressureBinder(Mutable<TMutableValue> mutable, View view, Converter<TMutableValue, Float> toView,
					Converter<Float, TMutableValue> toMutable) {
		super(mutable, view, toView, toMutable);
		this.mutable = mutable;
	}

	@Override
	protected void setViewEnabled(boolean b) {
		view.setEnabled(b);
	}

	@Override
	protected void setViewValue(Float v, boolean force) {
		// nada
	}

	@Override
	protected Float getViewValue() {
		// TODO Auto-generated method stub
		return null;
	}
}