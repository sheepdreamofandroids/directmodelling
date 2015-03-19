package com.directmodelling.demo.angular.shared;

import java.io.Serializable;

import com.directmodelling.api.Status;
import com.directmodelling.impl.IntVar;
import com.directmodelling.impl.ObjectFun;
import com.directmodelling.impl.StringVar;
import com.google.gwt.core.client.js.JsType;

@JsType
public class PostcodeDemo implements Serializable {
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

	public final Adres adres = new Adres(postcode);
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

	// Javascript accessors
	public final StringVar letters() {
		return letters;
	}

	public final IntVar digits() {
		return digits;
	}

}
