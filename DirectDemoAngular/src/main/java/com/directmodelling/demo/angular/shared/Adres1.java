package com.directmodelling.demo.angular.shared;

import com.directmodelling.api.Status;
import com.directmodelling.impl.Range;
import com.directmodelling.impl.StringVar;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.regexp.shared.RegExp;

@JsType
public class Adres1 {
	private static final RegExp postcodePattern = RegExp
			.compile("^\\d{4}\\s*[a-zA-Z]{2}$");

	public final StringVar postcode = new StringVar("1234AB") {

		@Override
		public com.directmodelling.api.Status status() {
			GWT.log("'" + get() + "' is a postcode: "
					+ postcodePattern.test(get()));
			return postcodePattern.test(get()) ? super.status()
					: new Status.Invalid.Format();
		};
	};
	private final Adres adres = new Adres(postcode);

	public final PostcodeLookup lookup = new PostcodeLookup(postcode);

	public final Range<Integer> huisnummer = new Range<Integer>(0,
			lookup.minHuisnummer, lookup.maxHuisnummer);

	public Adres adres() {
		return adres;
	}

}
