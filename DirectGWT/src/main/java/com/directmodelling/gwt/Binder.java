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
package com.directmodelling.gwt;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

/** Binds variables to widgets. */
public class Binder<WidgetDomain, VarDomain> implements Receiver, ValueChangeHandler<WidgetDomain> {

	private final HasValue<WidgetDomain> gwtValue;
	private Mutable<VarDomain> var;
	private Value<VarDomain> val;
	private final HandlerRegistration addValueChangeHandler;
	private Converter<? super WidgetDomain, ? extends VarDomain> toVar;
	private Converter<? super VarDomain, ? extends WidgetDomain> toWidget;

	public Binder(final HasValue<WidgetDomain> gwtValue) {
		assert null != gwtValue;
		this.gwtValue = gwtValue;
		Updates.registerForChanges(this);
		addValueChangeHandler = gwtValue.addValueChangeHandler(this);
	}

	// A mutable changed
	public void valuesChanged() {
		if (null != val) {
			gwtValue.setValue(toWidget.convert(val.getValue()), false);
		}
	}

	// The widget changed
	public void onValueChange(final ValueChangeEvent<WidgetDomain> event) {
		if (null != var) {
			var.setValue(toVar.convert(event.getValue()));
		}
	}

	public void unbind() {
		Updates.unregister(this);
		addValueChangeHandler.removeHandler();
	}

	@SuppressWarnings("unchecked")
	public <T> void setVar(final Value<T> var, final Converter<? super WidgetDomain, T> toVar,
					final Converter<? super T, WidgetDomain> toWidget) {
		this.val = (Value<VarDomain>) var;
		if (val instanceof Value.Mutable)
			this.var = (Mutable<VarDomain>) var;
		this.toVar = this.var == null ? null : (Converter<WidgetDomain, VarDomain>) toVar;
		this.toWidget = (Converter<VarDomain, WidgetDomain>) toWidget;
	}

	// @SuppressWarnings("unchecked")
	// public void setVar(final Value.Mutable<VarDomain> var,
	// final Converter<? super WidgetDomain, ? extends VarDomain> toVar,
	// final Converter<? super VarDomain, ? extends WidgetDomain> toWidget) {
	// this.val = var;
	// this.var = var;
	// this.toVar = this.var == null ? null : (Converter<WidgetDomain,
	// VarDomain>) toVar;
	// this.toWidget = toWidget;
	// }

	@SuppressWarnings("unchecked")
	public void setVal(final Value<? extends VarDomain> var, final Converter<? extends VarDomain, WidgetDomain> toWidget) {
		this.val = (Value<VarDomain>) var;
		this.var = null;
		this.toVar = null;
		this.toWidget = (Converter<VarDomain, WidgetDomain>) toWidget;
	}

}
