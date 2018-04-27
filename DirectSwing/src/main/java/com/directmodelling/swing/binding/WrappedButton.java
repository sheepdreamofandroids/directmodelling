package com.directmodelling.swing.binding;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.JToggleButton;

public interface WrappedButton<T> {
    /**
     * RadioButton (or equivalent)
     */
    AbstractButton getRadio();

    /**
     * Container of the radio, or radio itself
     */
    Component getContainer();

    /**
     * Value represented by this button
     */
    T getValue();

    public static class Default<T> extends JToggleButton.ToggleButtonModel implements WrappedButton<T> {
        public Default(final T value, final AbstractButton radio) {
            this(value, radio, radio);
        }

        public Default(final T value, final AbstractButton radio, final Component container) {
            super();
            this.value = value;
            this.radio = radio;
            radio.setModel(this);
            this.container = container;
        }

        @Override
        public AbstractButton getRadio() {
            return radio;
        }

        @Override
        public Component getContainer() {
            return container;
        }

        @Override
        public T getValue() {
            return value;
        }

        /**
         * the radio button for a given value
         */
        private final AbstractButton radio;
        /**
         * optional wrapper for the radio button
         */
        private final Component container;
        /**
         * value when chosen
         */
        private T value;
    }

}