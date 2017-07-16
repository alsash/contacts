package com.alsash.contacts.data;

import com.google.auto.value.AutoValue;

/**
 * A Person model
 */
@AutoValue
public abstract class Person {

    static Builder builder() {
        return new AutoValue_Person.Builder();
    }

    public abstract String name();

    public abstract String surname();

    @AutoValue.Builder
    abstract static class Builder {

        abstract Builder name(String name);

        abstract Builder surname(String surname);

        abstract Person build();
    }
}
