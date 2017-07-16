package com.alsash.contacts.screen;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.alsash.contacts.data.Contact;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A Navigator class for controlling transitions between application screens
 * It is injected by constructor annotation {@link javax.inject.Inject)
 * into a {@link com.alsash.contacts.app.ContactsAppComponent}
 */
@Singleton
public class Navigator {

    @Inject
    Navigator() {
    }

    public void toAddContactScreen(AppCompatActivity activity) {

    }

    public void toEditContactScreen(AppCompatActivity activity, @NonNull Contact contact) {

    }
}
