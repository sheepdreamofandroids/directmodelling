package com.directmodelling.impl;

import javax.inject.Provider;

import com.directmodelling.api.ID;
import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Tracker;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.stm.impl.VersionImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class DirectModule {public static interface StorageProvider extends Provider<Storage>{};
	@Provides
	VersionImpl baseValues() {
		return new VersionImpl();
	}

	@Provides
	TransactionImpl changes(VersionImpl baseValues) {
		return new TransactionImpl(baseValues);
	}

	@Provides
	ID.IDGenerator idGenerator() {
		return SimpleIDGenerator.INSTANCE;
	}

	@Provides
	StorageProvider currentStorage(final TransactionImpl changes) {
		return new StorageProvider() {
			@Override
			public Storage get() {
				return changes;
			}
		};
	}

	@Provides
	Tracker tracker() {
		return new ExplicitUpdatesTracker();
	}
}
