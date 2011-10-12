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
public final class PressureBinder<TMutableValue> extends Binder<View, TMutableValue, Number> {
	private final Mutable<TMutableValue> mutable;
	{
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final float pressure = event.getPressure();
				// Ln.d("Pressure: %s, size: %s", pressure, event.getSize());
				mutable.setValue(fromView.convert(pressure));
				return false;
			}
		});
	}

	PressureBinder(Mutable<TMutableValue> mutable, View view, Converter<TMutableValue, Number> toView,
					Converter<Number, TMutableValue> toMutable) {
		super(mutable, view, toView, toMutable);
		this.mutable = mutable;
	}

	@Override
	protected void setViewEnabled(boolean b) {
		view.setEnabled(b);
	}

	@Override
	protected void setViewValue(Number v, boolean force) {
		// nada
	}

	@Override
	protected Number getViewValue() {
		// TODO Auto-generated method stub
		return Integer.valueOf(0);
	}
}