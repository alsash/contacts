package com.alsash.contacts.data;

import android.support.annotation.DrawableRes;

import com.google.auto.value.AutoValue;

import java.util.UUID;

/**
 * A Contact model
 */
@AutoValue
public abstract class Contact {

    private String contactName;

    static Contact.Builder builder() {
        return new AutoValue_Contact.Builder();
    }

    public abstract UUID uuid();

    public abstract Person person();

    @DrawableRes
    public abstract int gifRes();

    // @Memorized - need to import javax.annotation.Generated
    public String name() {
        if (contactName == null)
            contactName = (person().name() + " " + person().surname()).trim();
        return contactName;
    }

    @AutoValue.Builder
    abstract static class Builder {

        abstract Contact.Builder uuid(UUID uuid);

        abstract Contact.Builder person(Person person);

        abstract Contact.Builder gifRes(int gifRes);

        abstract Contact build();
    }
}
