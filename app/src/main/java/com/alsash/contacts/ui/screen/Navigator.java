package com.alsash.contacts.ui.screen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alsash.contacts.data.Contact;
import com.alsash.contacts.ui.screen.addedit.AddEditActivity;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A Navigator class for controlling transitions between application screens.
 * It is injected by constructor annotation {@link javax.inject.Inject) into required instances.
 */
@Singleton
public class Navigator {

    private static final String CONTACT_ID = "com.alsash.contacts.contact_id";

    @Inject
    Navigator() {
    }

    @SuppressWarnings("unchecked")
    public void toContactAddScreen(@NonNull AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, AddEditActivity.class),
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @SuppressWarnings("unchecked")
    public void toContactEditScreen(@NonNull AppCompatActivity activity, @NonNull Contact contact) {
        Intent intent = new Intent(activity, AddEditActivity.class);
        intent.putExtra(CONTACT_ID, contact.uuid().toString());
        activity.startActivity(intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Nullable
    public UUID getContactId(Intent intent) {
        if (intent == null) return null;
        String contactUuid = intent.getStringExtra(CONTACT_ID);
        if (contactUuid == null) return null;
        try {
            return UUID.fromString(contactUuid);
        } catch (Throwable e) {
            Log.d("Navigator", e.getMessage(), e);
            return null;
        }
    }

    public boolean hasTransition(AppCompatActivity activity) {
        return activity instanceof AddEditActivity;
    }
}
