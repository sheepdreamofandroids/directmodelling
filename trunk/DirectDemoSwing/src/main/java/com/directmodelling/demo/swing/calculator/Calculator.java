package com.directmodelling.demo.swing.calculator;

import java.awt.Component;

import javax.swing.JFrame;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.demo.shared.FunctionApplication;
import com.directmodelling.demo.swing.Main;
import com.directmodelling.impl.util.Function;
import com.directmodelling.swing.binding.Button2CommandBinding;
import com.directmodelling.swing.binding.Iterator2PanelBinding;

public class Calculator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main.startup();
		start();
	}

	public static void start() {
		JFrame frame = new JFrame("Calculator");
		final CalculatorPanel calc = new CalculatorPanel();
		bind(calc, new com.directmodelling.demo.shared.Calculator());
		frame.setContentPane(calc);
		frame.pack();
		frame.setVisible(true);
	}

	private static void bind(CalculatorPanel calc, com.directmodelling.demo.shared.Calculator calculator) {
		new Button2CommandBinding(calc.plus, calculator.plus());
		new Button2CommandBinding(calc.minus, calculator.minus());
		new Button2CommandBinding(calc.multiply, calculator.multiply());
		new Button2CommandBinding(calc.divide, calculator.divide());
		new Button2CommandBinding(calc.getClear(), calculator.clear());
		new Iterator2PanelBinding<DoubleValue>(calc.getCalculationList(), calculator.flattenedOperatorList,
						new Function<DoubleValue, Component>() {
							@Override
							public Component apply(DoubleValue in) {
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
