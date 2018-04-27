package com.directmodelling.demo.shared;

public enum Operator {
	plus("+") {
		@Override
		double eval(double left, double right) {
			return left + right;
		}
	},
	minus("-") {
		@Override
		double eval(double left, double right) {
			return left - right;
		}
	},
	multiply("*") {
		@Override
		double eval(double left, double right) {
			return left * right;
		}
	},
	divide("/") {
		@Override
		double eval(double left, double right) {
			return left / right;
		}
	};
	public final String name;

	private Operator(String name) {
		this.name = name;
	}

	abstract double eval(double left, double right);
}
