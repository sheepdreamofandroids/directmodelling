package com.directmodelling.demo.shared;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.api.Updates;
import com.directmodelling.impl.DoubleVar;
import com.directmodelling.impl.ExplicitUpdatesTracker;
import com.directmodelling.impl.SimpleContext;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Storage.Util;
import com.directmodelling.stm.impl.VersionImpl;

public class CalculatorTest {
	/** epsilon */
	private static final double E = .000001;

	@Before
	public void setup() {
		final VersionImpl baseData = new VersionImpl();
		Util.current = new SimpleContext<Storage>(new VersionImpl(baseData));
		Updates.tracker = new ExplicitUpdatesTracker();
	}

	@Test
	public void testCalculator() {
		final Calculator calculator = new Calculator();

		Assert.assertTrue(calculator.calculation.getValue() instanceof DoubleVar);
		final DoubleValue.Mutable firstNumber = (DoubleValue.Mutable) calculator.calculation.getValue();
		Assert.assertEquals(0, firstNumber.get(), 0);
		Assert.assertEquals(0, calculator.calculation.getValue().get(), 0);

		firstNumber.set(12.3);
		Assert.assertEquals(12.3, firstNumber.get(), 0);
		Assert.assertEquals(12.3, calculator.calculation.getValue().get(), 0);

		calculator.plus().run();
		Assert.assertTrue(calculator.calculation.getValue() instanceof FunctionApplication);

		final FunctionApplication lastFunction = (FunctionApplication) calculator.calculation.getValue();
		Assert.assertSame(lastFunction.left(), firstNumber);
		// Assert.assertTrue(lastFunction.right instanceof DoubleVar);

		final DoubleValue.Mutable secondNumber = (DoubleValue.Mutable) lastFunction.right();
		Assert.assertEquals(12.3, calculator.calculation.getValue().get(), E);

		secondNumber.set(-5.5);
		Assert.assertEquals(6.8, calculator.calculation.getValue().get(), E);
	}
}
