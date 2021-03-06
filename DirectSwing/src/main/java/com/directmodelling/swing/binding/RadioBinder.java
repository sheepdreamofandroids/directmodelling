package com.directmodelling.swing.binding;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.directmodelling.api.Value;
import com.directmodelling.impl.EnumerableDomain;
import com.directmodelling.impl.util.FunctionCache;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;

/** Creates and binds a list of radiobuttons to a {@link Value}a */
public class RadioBinder<TVal, Val extends Value<TVal> & EnumerableDomain<TVal>>
		extends AbstractBinder<TVal> implements ChangeListener {

	private FunctionCache<TVal, WrappedButton> radiosCache;
	private Function<TVal, WrappedButton> componentFactory;
	private Val val2;
	private Container cont;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public static <T, Val extends Value<T> & EnumerableDomain<T>> RadioBinder<T, Val> bind(
			final Val val, final Function<T, WrappedButton> factory,
			final Container cont) {
		return new RadioBinder(val, factory, cont);
	}

	public RadioBinder(final Val val,
			final Function<TVal, WrappedButton> factory, final Container cont) {
		super(val);
		val2 = val;
		this.cont = cont;
		radiosCache = new FunctionCache<TVal, WrappedButton>(
				new Function<TVal, WrappedButton>() {
					@Override
					public WrappedButton apply(final TVal in) {
						final WrappedButton wb = factory.apply(in);
						wb.getRadio().getModel().setGroup(buttonGroup);
						wb.getRadio().addChangeListener(RadioBinder.this);
						return wb;
					}
				});
	}

	@Override
	public void valuesChanged() {
		super.valuesChanged();
		Iterator2PanelBinding.update(cont, Iterables.transform(
				val2.allPotentialValues(),
				new com.google.common.base.Function<TVal, Component>() {
					@Override
					public Component apply(final TVal input) {
						return radiosCache.apply(input).getContainer();
					}
				}));
	}

	@Override
	protected void setWidgetValue(final TVal wv) {
		final List<TVal> allPotentialValues = val2.allPotentialValues();
		for (final TVal v : allPotentialValues) {
			final WrappedButton wb = radiosCache.apply(v);
			final boolean b = Objects.equal(v, wv);
			wb.getRadio().setSelected(b);
		}
	}

	@Override
	protected TVal getWidgetValue() {
		final WrappedButton<TVal> selection = (WrappedButton<TVal>) buttonGroup
				.getSelection();
		return selection == null ? null : selection.getValue();
		// final List<TVal> allPotentialValues = val2.allPotentialValues();
		// for (final TVal v : allPotentialValues) {
		// final WrappedButton wb = radiosCache.apply(v);
		// if (wb.getRadio().isSelected())
		// return toWidget.convert(v);
		// }
		// return null;
	}

	@Override
	public void stateChanged(final ChangeEvent ce) {
		widgetChanged();
	}

}
