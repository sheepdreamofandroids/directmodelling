package com.directmodelling.demo.angular.shared;

import java.io.Serializable;

import com.directmodelling.api.Status;
import com.directmodelling.api.Value.Mutable;
import com.directmodelling.impl.IntVar;
import com.directmodelling.impl.ObjectFun;
import com.directmodelling.impl.Range;
import com.directmodelling.impl.StringVar;
import com.google.gwt.core.client.js.JsType;

@JsType
public class PostcodeDemo implements Serializable {
	public final StringVar letters = new StringVar("AB") {
		@Override
		public Status status() {
			return get() == null || get().length() != 2 ? new Status.Invalid.Format()
					: super.status();
		}
	};

	public final IntVar digits = new IntVar(1234) {
		@Override
		public Status status() {
			if (get() < 1000)
				return Status.Invalid.tooLow(1000);
			else if (get() > 9999)
				return Status.Invalid.tooHigh(9999);
			else
				return super.status();
		}
	};
	public final ObjectFun<String> postcode = new ObjectFun<String>() {
		@Override
		public String get() {
			return String.valueOf(digits.get()) + letters.get();
		};

		@Override
		public Status status() {
			return !digits.status().isValid() ? digits.status() //
					: !letters.status().isValid() ? letters.status() //
							: Status.readonly;
		};
	};

	public final PostcodeLookup result = new PostcodeLookup(postcode);

	public final Mutable<Integer> houseNumber = new Range<Integer>(0,
			result.minHuisnummer, result.maxHuisnummer) {
	};

	// Javascript accessors
	public final StringVar letters() {
		return letters;
	}

	public final ObjectFun<String> postcode() {
		return postcode;
	}

	public final PostcodeLookup result() {
		return result;
	}

	public final IntVar digits() {
		return digits;
	}

	public final Mutable<Integer> houseNumber() {
		return houseNumber;
	}

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
}
