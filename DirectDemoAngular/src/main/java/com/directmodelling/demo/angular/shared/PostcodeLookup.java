package com.directmodelling.demo.angular.shared;

import java.io.Serializable;

import com.directmodelling.api.Context;
import com.directmodelling.api.Value;
import com.directmodelling.impl.Function;
import com.directmodelling.impl.IntFun;
import com.directmodelling.impl.ObjectFun;
import com.directmodelling.impl.SingleAssignContext;
import com.directmodelling.synchronization.RemoteFunction;
import com.google.gwt.core.client.js.JsType;

/**
 * Operator taking a postcode as a String and asynchronously updating DB data
 * about that postcode.
 * 
 * @author guus
 *
 */
@JsType
public class PostcodeLookup extends
		RemoteFunction<String, PostcodeLookup.PostcodeLookupResult> {
	private PostcodeLookup() {
		postcode = null;
	}

	public IntFun minHuisnummer() {
		return minHuisnummer;
	}

	public IntFun maxHuisnummer() {
		return maxHuisnummer;
	}

	public Function<String> straat() {
		return straat;
	}

	public Function<String> stad() {
		return stad;
	}

	public Value<String> postcode() {
		return postcode;
	}

	public static final SingleAssignContext<Impl<String, PostcodeLookupResult>> getImpl() {
		return impl;
	}

	public final IntFun minHuisnummer = new IntFun() {
		@Override
		public int get() {
			return PostcodeLookup.this.get().minHuisnummer;
		}
	};

	public final IntFun maxHuisnummer = new IntFun() {
		@Override
		public int get() {
			return PostcodeLookup.this.get().maxHuisnummer;
		}
	};

	public final ObjectFun<String> straat = new ObjectFun<String>() {
		@Override
		public String get() {
			return PostcodeLookup.this.get().straat;
		}
	};

	public final ObjectFun<String> stad = new ObjectFun<String>() {
		@Override
		public String get() {
			return PostcodeLookup.this.get().stad;
		}
	};

	public final Value<String> postcode;

	public PostcodeLookup(final Value<String> postcode) {
		this.postcode = postcode;
	}

	public static class PostcodeLookupResult implements Serializable {
		public final String straat;
		public final String stad;
		public final int minHuisnummer;
		public final int maxHuisnummer;

		/** Dummy constructor for GWT serialization */
		private PostcodeLookupResult() {
			straat = stad = null;
			minHuisnummer = maxHuisnummer = 0;
		};

		public PostcodeLookupResult(final String straat, final String stad,
				final int minHuisnummer, final int maxHuisnummer) {
			this.straat = straat;
			this.stad = stad;
			this.minHuisnummer = minHuisnummer;
			this.maxHuisnummer = maxHuisnummer;
		}
	}

	public static final SingleAssignContext<Impl<String, PostcodeLookupResult>> impl = Context.SESSION
			.it().create();

	@Override
	public String argument() {
		return postcode.getValue();
	}

	@Override
	protected Impl<String, PostcodeLookupResult> implementation() {
		return impl.it();
	}

	@Override
	protected PostcodeLookupResult initial() {
		return new PostcodeLookupResult("", "", 0, Integer.MAX_VALUE);
	}

}