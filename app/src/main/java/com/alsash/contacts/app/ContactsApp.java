package com.alsash.contacts.app;

import android.app.Application;

import com.alsash.contacts.screen.addedit.AddEditComponent;
import com.alsash.contacts.screen.start.StartComponent;

/**
 * An application instance
 */
public class ContactsApp extends Application {

    private ContactsAppComponent appComponent;
    private StartComponent startComponent;
    private AddEditComponent addEditComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerContactsAppComponent.builder()
                .contactsAppModule(new ContactsAppModule(this))
                .build();

        startComponent = appComponent
                .getStartComponentBuilder()
                .build();

        addEditComponent = appComponent
                .getAddEditComponentBuilder()
                .build();
    }

    public StartComponent getStartComponent() {
        return startComponent;
    }

    public AddEditComponent getAddEditComponent() {
        return addEditComponent;
    }
}
