package com.alsash.contacts.app;

import android.app.Application;

import com.alsash.contacts.BuildConfig;
import com.alsash.contacts.ui.screen.addedit.AddEditComponent;
import com.alsash.contacts.ui.screen.start.StartComponent;

import org.greenrobot.eventbus.EventBus;

/**
 * An application instance
 */
public class ContactsApp extends Application {
    @SuppressWarnings("FieldCanBeLocal")
    private ContactsAppComponent appComponent;
    private StartComponent startComponent;
    private AddEditComponent addEditComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger 2 initialization
        appComponent = DaggerContactsAppComponent.builder()
                .contactsAppModule(new ContactsAppModule(this))
                .build();

        startComponent = appComponent
                .getStartComponentBuilder()
                .build();

        addEditComponent = appComponent
                .getAddEditComponentBuilder()
                .build();

        // EventBus initialization
        EventBus.builder()
                .throwSubscriberException(BuildConfig.DEBUG)
                .installDefaultEventBus();

    }

    public StartComponent getStartComponent() {
        return startComponent;
    }

    public AddEditComponent getAddEditComponent() {
        return addEditComponent;
    }
}
