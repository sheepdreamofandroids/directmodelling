package com.directmodelling.swing.binding;

import javax.swing.JLabel;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;

public class TextBinding<TValue, TWidget> implements Receiver {
	// private static final Class[] setterParameterTypes = { String.class };

	public static interface Setter<T> {
		void set(T v);
	}

	public static interface Getter<T> {
		T get();
	}

	private final Setter<TWidget> setter;
	private final Getter<TWidget> getter;
	private final Value<TValue> value;
	private final Mutable<TValue> mutable;
	private final Converter<TValue, TWidget> value2widget;
	private final Converter<TWidget, TValue> widget2value;

	public TextBinding(Value<TValue> v, Converter<TValue, TWidget> value2widget,
					Converter<TWidget, TValue> widget2value, Getter<TWidget> getter, Setter<TWidget> setter) {
		this.value = v;
		this.value2widget = value2widget;
		this.widget2value = widget2value;
		this.getter = getter;
		this.setter = setter;
		if (v instanceof Mutable<?>)
			mutable = (Mutable<TValue>) v;
		else
			mutable = null;
		if (null != value && setter != null)
			Updates.registerForChanges(this);
	}

	public static void bind(final JLabel label, Value<Double> d) {
		new TextBinding<Double, String>(d, Converter.Double2String, Converter.String2Double, null,
						new Setter<String>() {
							@Override
							public void set(String v) {
								label.setText(v);
							}
						});
	}

	// public static void bind(final JTextComponent t, Value<Double> d) {
	// new TextBinding<Double, String>(d, Converter.Double2String,
	// Converter.String2Double, new Getter<String>() {
	// @Override
	// public String get() {
	// return t.getText();
	// }
	// }, new Setter<String>() {
	// @Override
	// public void set(String v) {
	// t.setText(v);
	// }
	// });
	// t.getDocument()
	// }

	@Override
	public void valuesChanged() {
		setter.set(value2widget.convert(value.getValue()));
	}
}
