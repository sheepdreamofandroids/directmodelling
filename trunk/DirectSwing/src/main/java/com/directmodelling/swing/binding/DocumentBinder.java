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
package com.directmodelling.swing.binding;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.directmodelling.api.Converter;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.stm.impl.UninitializedException;

public class DocumentBinder<T> implements Receiver, DocumentListener {
    private final Value<T> val;
    private final Mutable<T> var;
    private final Converter<String, T> toVar;
    private final Converter<T, String> toComponent;
    private final JTextComponent textComponent;
    private boolean suppressTextChanges;

    public DocumentBinder(final JTextComponent tc, final Value<T> val, final Converter<String, T> toVar,
                          final Converter<T, String> toComponent) {
        this.textComponent = tc;
        this.val = val;
        this.var = val instanceof Mutable ? (Mutable<T>) val : null;
        this.toVar = toVar;
        this.toComponent = toComponent;
        Updates.tracker.registerForChanges(this);

        if (var != null && toVar != null) {
            tc.getDocument().addDocumentListener(this);
            tc.addFocusListener(new FocusListener() {

                @Override
                public void focusLost(final FocusEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void focusGained(final FocusEvent e) {
                    // TODO Auto-generated method stub

                }
            });
        }

        // valuesChanged();
        // instead, make tracker call later
    }

    public static <T> DocumentBinder bind(final JTextComponent tc, final Value<T> var,
                                          final Converter<? super String, ? extends T> toVar, final Converter<? super T, ? extends String> toComponent) {
        return new DocumentBinder(tc, var, toVar, toComponent);
    }

    @Override
    public void valuesChanged() {
        if (!textComponent.hasFocus()) {
            suppressTextChanges = true;
            try {
                final T value = val.getValue();
                final String converted = toComponent.convert(value);
                textComponent.setText(converted);
            } catch (final UninitializedException uie) {
                // ignore, textfield not set
            } finally {
                suppressTextChanges = false;
            }
        }
    }

    @Override
    public void changedUpdate(final DocumentEvent e) {
        // TODO only plain text for now
    }

    @Override
    public void insertUpdate(final DocumentEvent e) {
        edited();
    }

    private void edited() {
        try {
            if (!suppressTextChanges) {
                if (var != null)
                    var.setValue(toVar.convert(textComponent.getText()));
            }
        } catch (final Exception e) {
            e.printStackTrace();
            // ignore
        }
    }

    @Override
    public void removeUpdate(final DocumentEvent e) {
        edited();
    }
}
