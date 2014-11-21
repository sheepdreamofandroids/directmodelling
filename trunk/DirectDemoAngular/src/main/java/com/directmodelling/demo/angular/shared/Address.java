package com.directmodelling.demo.angular.shared;

import java.util.regex.Pattern;

import com.directmodelling.api.Status;
import com.directmodelling.impl.Range;
import com.directmodelling.impl.StringVar;

public class Address {
	private static final Pattern postcodePattern = Pattern.compile("####nn");

	public final StringVar postcode = new StringVar("1234AB") {

		@Override
		public com.directmodelling.api.Status status() {
			return postcodePattern.matcher(get()).matches() ? super.status()
					: new Status.Invalid.Format();
		};
	};

	private final PostcodeLookup lookup = new PostcodeLookup(postcode);

	public final Range<Integer> huisnummer = new Range<Integer>(0,
			lookup.minHuisnummer, lookup.maxHuisnummer);
}
