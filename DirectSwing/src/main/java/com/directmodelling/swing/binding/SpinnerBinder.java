package com.directmodelling.swing.binding;

import javax.swing.AbstractSpinnerModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

import com.directmodelling.api.Value;
import com.directmodelling.properties.HasMaximum;
import com.directmodelling.properties.HasMinimum;

public abstract class SpinnerBinder<TVal> extends AbstractBinder<TVal> {
	protected abstract TVal increment(final TVal v);

	protected abstract TVal decrement(final TVal v);

	private class DefaultBoundSpinnerModel extends AbstractSpinnerModel {

		@Override
		public Object getValue() {
			return val.get();
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setValue(final Object value) {
			if (var != null) {
				var.setValue((TVal) value);
				fireStateChanged();
			}
		}

		// @Override
		// public void valuesChanged() {
		// fireStateChanged();
		// }

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Object getPreviousValue() {
			final TVal v = val.get();
			return v instanceof Comparable && val instanceof HasMinimum<?>
					&& ((Comparable) v).compareTo(((HasMinimum<?>) val).getMinimum()) <= 0 ? null : decrement(v);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Object getNextValue() {
			final TVal v = val.get();
			return val instanceof HasMaximum<?> && ((Comparable) v).compareTo(((HasMaximum<?>) val).getMaximum()) >= 0 ? null
					: increment(v);
		}
	}

	private final SpinnerModel sm = new DefaultBoundSpinnerModel();

	// public final JSpinner spinnerx;
	// public final JTextField spinner = new JTextField();

	private SpinnerBinder(final Value<TVal> val, final JSpinner spinner) {
		super(val);
		spinner.setModel(sm);
		// spinnerx = new JSpinner() {
		// @Override
		// public Dimension getPreferredSize() {
		// // TODO Auto-generated method stub
		// return super.getPreferredSize();
		// }
		// };
		// spinnerx.setValue(getWidgetValue());
		// spinnerx.setMinimumSize(new Dimension(300, 30));
	}

	// @SuppressWarnings("unchecked")
	public static SpinnerBinder<Integer> bind(final Value<Integer> val, final JSpinner spinner) {
		@SuppressWarnings("unchecked")
		final SpinnerBinder<Integer> a = new SpinnerBinder<Integer>(val, spinner) {

			/** @param v
			 * @return v-1 */
			@Override
			protected Integer decrement(final Integer v) {
				return v.intValue() - 1;
			}

			/** @param v
			 * @return v+1 */
			@Override
			protected Integer increment(final Integer v) {
				return v.intValue() + 1;
			}
		};
		return a;
	}

	public static SpinnerBinder<Double> bindDouble(final Value<Double> val, final JSpinner spinner, final double step) {
		return new SpinnerBinder<Double>(val, spinner) {

			/** @param v
			 * @return v-step */
			@Override
			protected Double decrement(final Double v) {
				return v - step;
			}

			/** @param v
			 * @return v+step */
			@Override
			protected Double increment(final Double v) {
				return v + step;
			}
		};
	}

	public static <T extends Enum<T>> SpinnerBinder<T> bindEnum(final Value<T> val, final JSpinner spinner,
			final T[] values) {
		return new SpinnerBinder<T>(val, spinner) {

			/** @param v
			 * @return v-step */
			@Override
			protected T decrement(final T v) {
				final int ordinal = v.ordinal();
				return ordinal <= 0 ? null : values[ordinal - 1];
			}

			/** @param v
			 * @return v+step */
			@Override
			protected T increment(final T v) {
				final int ordinal = v.ordinal() + 1;
				return ordinal >= values.length ? null : values[ordinal];
			}
		};
	}

	@Override
	protected void setWidgetValue(final TVal v) {
		sm.setValue(v);
	}

	@Override
	protected TVal getWidgetValue() {
		return (TVal) sm.getValue();
	}
}
