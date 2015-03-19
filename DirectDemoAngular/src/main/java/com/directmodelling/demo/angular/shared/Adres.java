package com.directmodelling.demo.angular.shared;

import com.directmodelling.api.Status;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.impl.Function;
import com.directmodelling.impl.Range;
import com.directmodelling.impl.StringVar;
import com.google.gwt.core.client.js.JsType;

@JsType
public class Adres {
	public Adres(Value<String> postcode) {
		this.postcode = postcode;
		result = new PostcodeLookup(postcode);
		houseNumber = new Range<Integer>(0, result.minHuisnummer,
				result.maxHuisnummer) {
		};
	}

	public final Value<String> postcode;
	public final PostcodeLookup result;
	public final Mutable<Integer> houseNumber;

	public final Function<String> stad = new Function<String>() {
		@Override
		public String getValue() {
			return result.status().isValid() ? result.get().stad : "";
		}

		@Override
		public Status status() {
			return result.status();
		}
	};
	public final StringVar straat = new StringVar("") {
		@Override
		public Status status() {
			return (result.status().isValid()) ? Status.readonly
					: Status.writeable;
		}

		@Override
		public String getValue() {
			return (status() == status().readonly ? result.get().straat : super
					.getValue());
		};
	};

	public final Mutable<Integer> houseNumber() {
		return houseNumber;
	}

	public final Value<String> postcode() {
		return postcode;
	}

	public final PostcodeLookup result() {
		return result;
	}

}
