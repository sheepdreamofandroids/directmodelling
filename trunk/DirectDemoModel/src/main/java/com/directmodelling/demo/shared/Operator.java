package com.directmodelling.demo.shared;

public enum Operator {
	plus("+"), minus("-"), multiply("*"), divide("/");
	public final String name;

	private Operator(String name) {
		this.name = name;
	}

}
