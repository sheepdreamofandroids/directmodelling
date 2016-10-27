package com.directmodelling.demo.swing.calculator;

import java.awt.Component;

import javax.inject.Inject;
import javax.swing.JFrame;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.demo.shared.FunctionApplication;
import com.directmodelling.swing.DirectSwingInit;
import com.directmodelling.swing.binding.Button2CommandBinding;
import com.directmodelling.swing.binding.Iterator2PanelBinding;
import com.google.common.base.Function;

public class SwingCalculator {
	public final DirectSwingInit init;

	@Inject
	public SwingCalculator(com.directmodelling.demo.shared.Calculator model, DirectSwingInit init) {
		MODEL = model;
		this.init = init;
		init.init();
	}

	public final com.directmodelling.demo.shared.Calculator MODEL;
	// = new com.directmodelling.demo.shared.Calculator();

	@dagger.Component(modules = { DirectSwingInit.class })
	public static interface Context {
		SwingCalculator calc();
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		SwingCalculator calc = DaggerSwingCalculator_Context.builder().build().calc();
		// Main.startup();
		calc.start();
	}

	public void start() {
		final JFrame frame = new JFrame("SwingCalculator");
		final CalculatorPanel calc = new CalculatorPanel();
		bind(calc, MODEL);
		frame.setContentPane(calc);
		frame.pack();
		frame.setVisible(true);
	}

	public void bind(final CalculatorPanel panel, final com.directmodelling.demo.shared.Calculator model) {
		new Button2CommandBinding(panel.plus, model.plus());
		new Button2CommandBinding(panel.minus, model.minus());
		new Button2CommandBinding(panel.multiply, model.multiply());
		new Button2CommandBinding(panel.divide, model.divide());
		new Button2CommandBinding(panel.getClear(), model.clear());
		new Iterator2PanelBinding<DoubleValue>(panel.getCalculationList(),
				model.flattenedOperatorList,
				new Function<DoubleValue, Component>() {
					@Override
					public Component apply(final DoubleValue in) {
						if (in instanceof FunctionApplication)
							return new OperatorCell((FunctionApplication) in);
						else
							return new OperatorCell(in);
						// final JTextField tf = new JTextField();
						// new DocumentBinder<Double>(tf, in,
						// Converter.String2Double,
						// Converter.Double2String);
						// return tf;
					}
				});
	}
}
