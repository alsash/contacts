package com.alsash.contacts.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that provide the application context
 */
@Module
public final class ContactsAppModule {

    private final Context appContext;

    public ContactsAppModule(@NonNull Application application) {
        this.appContext = application.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return appContext;
    }
}
