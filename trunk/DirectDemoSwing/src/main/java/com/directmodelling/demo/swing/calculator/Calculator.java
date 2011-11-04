package com.directmodelling.demo.swing.calculator;

import javax.swing.JFrame;

import com.directmodelling.demo.swing.Main;

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
		frame.setContentPane(new CalculatorPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
