package com.directmodelling.swing.binding;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.impl.EnumerableDomain;

import java.awt.Component;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

/**
 * A binder that should grow to support all derivations of {@link ListModel}.
 * Unfortunately at the time it has some dependency on {@link JComboBox}. This
 * should be easy to refactor into a subclass though.
 * <p>
 * It is different by BEING the model instead of synchronizing it. In a way this
 * is closer to the conceptual model where the Widget knows the Var but not the
 * other way around. If it works out, this might be the way to go for all Swing
 * binders.
 */
public class ListModelBinding<T> extends AbstractListModel implements ComboBoxModel, Receiver, ListCellRenderer {
	private final Value<T> val;
	private final Mutable<T> var;
	private final EnumerableDomain<T> domain;
	private List<T> currentDomain;
	private final JComboBox comboBox;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T, P extends Value<T> & EnumerableDomain<T>> void bind(final JComboBox comboBox, final P val) {
		comboBox.setModel(new ListModelBinding(comboBox, val));
	}

	public <P extends Value<T> & EnumerableDomain<T>> ListModelBinding(final JComboBox comboBox, final P val) {
		this.val = val;
		var = val instanceof Mutable ? (Mutable<T>) val : null;
		domain = val;
		currentDomain = domain.allPotentialValues();
		this.comboBox = comboBox;
		Updates.registerForChanges(this);
		comboBox.setRenderer(this);
	}

	@Override
	public void valuesChanged() {
		// This is strange: combobox listens to changes in the listmodel but not
		// in it's own ComboBoxModel...
		comboBox.setSelectedItem(val.getValue());
		// weird, this should not be necessary
		comboBox.getEditor().setItem(val.getValue());

		// Quick check to see if anything changed. Unfortunately we don't know
		// (yet) exactly WHAT changed.
		final List<T> oldDomain = currentDomain;
		currentDomain = domain.allPotentialValues();
		if (!currentDomain.equals(oldDomain))
			fireContentsChanged(null, 0, currentDomain.size());
	}

	// Model methods
	@Override
	public int getSize() {
		return domain.allPotentialValues().size();
	}

	@Override
	public Object getElementAt(final int index) {
		return domain.allPotentialValues().get(index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSelectedItem(final Object anItem) {
		if (var != null)
			var.setValue((T) anItem);
	}

	@Override
	public Object getSelectedItem() {
		try {
			return val.getValue();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// TODO the following renderer implementation is very primitive
	private JLabel label = new JLabel();

	@Override
	public Component getListCellRendererComponent(final JList list, final Object value, final int index,
									final boolean isSelected, final boolean cellHasFocus) {
		label.setText(String.valueOf(value));
		return label;
	}
}
