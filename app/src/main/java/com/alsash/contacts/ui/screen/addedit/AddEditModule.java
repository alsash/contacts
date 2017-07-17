package com.alsash.contacts.ui.screen.addedit;

import com.alsash.contacts.data.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module, that provide dependencies for the addEdit screen.
 */
@Module
public abstract class AddEditModule {

    @Provides
    @AddEditScope
    static AddEditPresenter provideAddEditPresenter(Repository repository) {
        return new AddEditPresenter(repository);
    }

}
