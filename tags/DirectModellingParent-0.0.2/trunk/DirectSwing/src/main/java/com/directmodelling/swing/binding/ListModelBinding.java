package com.directmodelling.swing.binding;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.ListModel;

import com.directmodelling.api.ObjectValue.Mutable;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.impl.EnumerableDomain;

/**
 * A binder that should grow to support all derivations of {@link ListModel}.
 * Unfortunately at the time it has some dependency on {@link JComboBox}.
 * <p>
 * It is different by BEING the model instead of synchronizing it. In a way this
 * is closer to the conceptual model where the Widget knows the Var but not the
 * other way around. If it works out, this might be the way to go for all Swing
 * binders.
 */
public class ListModelBinding<T extends Enum<T>> extends AbstractListModel implements ComboBoxModel, Receiver {
	private final Value<T> val;
	private final Mutable<T> var;
	private final EnumerableDomain<T> domain;
	private List<T> currentDomain;
	private final JComboBox comboBox;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Enum<T>, P extends Value<T> & EnumerableDomain<T>> void bind(JComboBox comboBox, P val) {
		comboBox.setModel(new ListModelBinding(comboBox, val));
	}

	public <P extends Value<T> & EnumerableDomain<T>> ListModelBinding(JComboBox comboBox, P val) {
		this.val = val;
		var = val instanceof Mutable ? (Mutable<T>) val : null;
		domain = val;
		currentDomain = domain.allPotentialValues();
		this.comboBox = comboBox;
	}

	@Override
	public void valuesChanged() {
		// This is strange: combobox listens to changes in the listmodel but not
		// in it's own ComboBoxModel...
		comboBox.setSelectedItem(val.getValue());

		// Quick check to see if anything changed. Unfortunately we don't know
		// (yet) exactly WHAT changed.
		List<T> oldDomain = currentDomain;
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
	public Object getElementAt(int index) {
		return domain.allPotentialValues().get(index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSelectedItem(Object anItem) {
		if (var != null)
			var.set((T) anItem);
	}

	@Override
	public Object getSelectedItem() {
		return val.getValue();
	}
}
