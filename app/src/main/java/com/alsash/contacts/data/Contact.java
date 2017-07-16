package com.alsash.contacts.data;

import android.support.annotation.IdRes;

import com.google.auto.value.AutoValue;

/**
 * A Contact model
 */
@AutoValue
public abstract class Contact {

    static Contact.Builder builder() {
        return new AutoValue_Contact.Builder();
    }

    public abstract Person person();

    @IdRes
    public abstract int imageRes();

    @AutoValue.Builder
    abstract static class Builder {

        abstract Contact.Builder person(Person person);

        abstract Contact.Builder imageRes(int imageRes);

        abstract Contact build();
    }
}
