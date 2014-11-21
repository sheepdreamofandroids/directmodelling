package com.directmodelling.demo.angular.client;

public class Pretty implements Interop {
	@Override
	public int integer() {
		return 1;
	}

	@Override
	public double twoTimesFloat(final double a) {
		return 2 * a;
	}
}