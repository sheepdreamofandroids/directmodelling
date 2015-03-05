package com.directmodelling.demo.angular.shared;

import com.directmodelling.api.Status;
import com.directmodelling.impl.Function;
import com.directmodelling.impl.Range;
import com.directmodelling.impl.StringVar;
import com.google.gwt.regexp.shared.RegExp;

public class Address {
	private static final RegExp postcodePattern = RegExp.compile("####nn");

	public final StringVar postcode = new StringVar("1234AB") {

		@Override
		public com.directmodelling.api.Status status() {
			return postcodePattern.test(get()) ? super.status()
					: new Status.Invalid.Format();
		};
	};

	public final PostcodeLookup lookup = new PostcodeLookup(postcode);

	public final Range<Integer> huisnummer = new Range<Integer>(0,
			lookup.minHuisnummer, lookup.maxHuisnummer);

	public final Function<String> straat = new Function<String>() {
		@Override
		public String getValue() {
			return lookup.status().isValid() ? lookup.get().straat : "";
		}

		@Override
		public Status status() {
			return lookup.status();
		}
	};
}
