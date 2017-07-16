package com.alsash.contacts.screen.start;

import android.support.v7.util.SortedList;

import com.alsash.contacts.data.Contact;
import com.alsash.contacts.data.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module, that provide dependencies for the start screen.
 * Also it provides dependency on the same data container between the presenter and the adapter.
 */
@Module
public abstract class StartModule {

    @Provides
    @StartScope
    static StartPresenter provideStartPresenter(Repository repository,
                                                SortedList<Contact> container) {
        return new StartPresenter(repository, container);
    }

    @Provides // No scope
    static StartListAdapter provideStartListAdapter(SortedList<Contact> container,
                                                    StartListSortedCallback callback) {
        StartListAdapter adapter = new StartListAdapter(container);
        callback.setAdapter(adapter);
        return adapter;
    }

    @Provides
    @StartScope
    static SortedList<Contact> provideContactsContainer(StartListSortedCallback callback) {
        return new SortedList<>(Contact.class, callback);
    }

    @Provides
    @StartScope
    static StartListSortedCallback provideContactsListCallback() {
        return new StartListSortedCallback();
    }
}
