package com.directmodelling.demo.angular.shared;

import com.directmodelling.api.Context;
import com.directmodelling.api.Value;
import com.directmodelling.impl.Function;
import com.directmodelling.impl.IntFun;
import com.directmodelling.impl.ObjectFun;
import com.google.common.base.Optional;

public final class PostcodeLookup extends
		RemoteFunction<String, PostcodeLookup.PostcodeLookupResult> {
	// public static final Context<Impl> impl =
	// Context.perUser.it().create(null);

	// public static interface Impl {
	// void take(PostcodeLookup lookup);
	// }

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

	public final Function<String> straat = new ObjectFun<String>() {
		@Override
		public String get() {
			return PostcodeLookup.this.get().straat;
		}
	};

	public final Function<String> stad = new ObjectFun<String>() {
		@Override
		public String get() {
			return PostcodeLookup.this.get().stad;
		}
	};

	public final Value<String> postcode;

	public PostcodeLookup(final Value<String> postcode) {
		super(impl.it(), new PostcodeLookupResult("", "", 0, Integer.MAX_VALUE));
		this.postcode = postcode;
	}

	public static class PostcodeLookupResult {
		public final String straat;
		public final String stad;
		public final int minHuisnummer;
		public final int maxHuisnummer;

		public PostcodeLookupResult(final String straat, final String stad,
				final int minHuisnummer, final int maxHuisnummer) {
			this.straat = straat;
			this.stad = stad;
			this.minHuisnummer = minHuisnummer;
			this.maxHuisnummer = maxHuisnummer;
		}
	}

	public static Context<com.google.common.base.Function<String, Optional<PostcodeLookupResult>>> impl = Context.perUser
			.it().create(null);

	@Override
	protected String argument() {
		return postcode.getValue();
	}

}