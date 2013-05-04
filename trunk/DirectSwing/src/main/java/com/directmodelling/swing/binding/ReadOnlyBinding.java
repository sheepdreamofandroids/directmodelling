package com.directmodelling.swing.binding;

import javax.swing.JLabel;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;

/**
 * Binds one property of an object to a Value one-way (read value, write
 * object).
 */
public abstract class ReadOnlyBinding<TValue, TWidget> implements Receiver {
	private final Value<TValue> value;
	private final Converter<TValue, TWidget> value2widget;

	public ReadOnlyBinding(Value<TValue> v,
			Converter<TValue, TWidget> value2widget) {
		this.value = v;
		this.value2widget = value2widget;
		// this.setter = setter;
		if (null != value)
			Updates.registerForChanges(this);
	}

	abstract public void set(TWidget v);

	public static void bind(final JLabel label, Value<Double> d) {
		new ReadOnlyBinding<Double, String>(d, Converter.Double2String) {
			@Override
			public void set(String v) {
				label.setText(v);
			}
		};
	}

	public static void bindString(final JLabel label, Value<String> d) {
		new ReadOnlyBinding<String, String>(d, Converter.ID_String) {
			@Override
			public void set(String v) {
				label.setText(v);
			}
		};
	}

	@Override
	public void valuesChanged() {
		set(value2widget.convert(value.getValue()));
	}
}
