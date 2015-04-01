package com.directmodelling.demo.angular.shared;

import org.junit.Assert;
import org.junit.Test;

import com.directmodelling.test.DirectTestBase;
import com.google.gwt.regexp.shared.RegExp;

public class Adres1Test extends DirectTestBase {
	@Test
	public void checkPattern() {
		// Adres1 adres1 = new Adres1();
		// adres1.postcode.set("1234AB");
		// Assert.assertEquals(Status.writeable, adres1.postcode.status());
		final RegExp postcodePattern = RegExp
				.compile("^\\d{4}\\s*[a-zA-Z]{2}$");
		Assert.assertTrue(postcodePattern.test("1234AB"));
		Assert.assertFalse(postcodePattern.test("1234ABc"));
		Assert.assertTrue(postcodePattern.test("1234 	AB"));
		Assert.assertFalse(postcodePattern.test("123 AB"));
		Assert.assertFalse(postcodePattern.test("1234B"));
		Assert.assertFalse(postcodePattern.test("1234@AB"));
	}
}
