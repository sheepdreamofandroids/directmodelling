package com.directmodelling.swing.binding;

import javax.swing.AbstractSpinnerModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Value;
import com.directmodelling.properties.HasMaximum;
import com.directmodelling.properties.HasMinimum;

public abstract class SpinnerBinder<TVal> extends AbstractBinder<TVal, Object> {
	protected abstract TVal increment(final TVal v);

	protected abstract TVal decrement(final TVal v);

	private class DefaultBoundSpinnerModel extends AbstractSpinnerModel
			implements Updates.Receiver {
		{
			Updates.registerForChanges(this);
		}

		@Override
		public Object getValue() {
			return val.getValue();
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setValue(final Object value) {
			if (var != null)
				var.setValue(toVar.convert(value));
		}

		@Override
		public void valuesChanged() {
			fireStateChanged();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Object getPreviousValue() {
			final TVal v = val.getValue();
			return v instanceof Comparable
					&& val instanceof HasMinimum<?>
					&& ((Comparable) v).compareTo(((HasMinimum<?>) val)
							.getMinimum()) <= 0 ? null : decrement(v);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Object getNextValue() {
			final TVal v = val.getValue();
			return val instanceof HasMaximum<?>
					&& ((Comparable) v).compareTo(((HasMaximum<?>) val)
							.getMaximum()) >= 0 ? null : increment(v);
		}
	}

	private final SpinnerModel sm = new DefaultBoundSpinnerModel();

	private SpinnerBinder(final Value<TVal> val, final JSpinner spinner,
			final Converter<? super TVal, ?> toWidget,
			final Converter<? super Object, ? extends TVal> toVar) {
		super(val, toWidget, toVar);
		spinner.setModel(sm);
	}

	@Override
	public void valuesChanged() {
	}

	// @SuppressWarnings("unchecked")
	public static SpinnerBinder<Integer> bind(final Value<Integer> val,
			final JSpinner spinner) {
		return new SpinnerBinder<Integer>(val, spinner, Converter.ID_Integer,
				(Converter) Converter.ID_Integer) {

			/**
			 * @param v
			 * @return v-1
			 */
			@Override
			protected Integer decrement(final Integer v) {
				return v.intValue() - 1;
			}

			/**
			 * @param v
			 * @return v+1
			 */
			@Override
			protected Integer increment(final Integer v) {
				return v.intValue() + 1;
			}
		};
	}

	public static SpinnerBinder<Double> bindDouble(final Value<Double> val,
			final JSpinner spinner, final double step) {
		return new SpinnerBinder<Double>(val, spinner, Converter.ID_Double,
				(Converter) Converter.ID_Double) {

			/**
			 * @param v
			 * @return v-step
			 */
			@Override
			protected Double decrement(final Double v) {
				return v.doubleValue() - step;
			}

			/**
			 * @param v
			 * @return v+step
			 */
			@Override
			protected Double increment(final Double v) {
				return v.doubleValue() + step;
			}
		};
	}

	public static <T extends Enum<T>> SpinnerBinder<T> bindEnum(
			final Value<T> val, final JSpinner spinner, final T[] values) {
		final Converter<T, T> converter = new Converter<T, T>() {

			@Override
			public T convert(final T value) {
				return value;
			}
		};
		return new SpinnerBinder<T>(val, spinner, converter,
				(Converter) converter) {

			/**
			 * @param v
			 * @return v-step
			 */
			@Override
			protected T decrement(final T v) {
				final int ordinal = v.ordinal();
				return ordinal <= 0 ? null : values[ordinal - 1];
			}

			/**
			 * @param v
			 * @return v+step
			 */
			@Override
			protected T increment(final T v) {
				final int ordinal = v.ordinal() + 1;
				return ordinal >= values.length ? null : values[ordinal];
			}
		};
	}
}
