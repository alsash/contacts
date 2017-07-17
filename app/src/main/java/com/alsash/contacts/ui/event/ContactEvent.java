package com.alsash.contacts.ui.event;

import com.alsash.contacts.data.Contact;

/**
 * An EventBus contact event class
 */
public class ContactEvent {

    public final Type type;
    public final Contact contact;

    public ContactEvent(Type type, Contact contact) {
        this.type = type;
        this.contact = contact;
    }

    public enum Type {
        CREATE, EDIT
    }
}
