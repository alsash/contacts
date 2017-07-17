package com.alsash.contacts.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alsash.contacts.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A repository class that allows to manage model values.
 * It is injected by constructor annotation {@link javax.inject.Inject) into required instances.
 */
@Singleton
public class Repository {

    private static final int[] GIFS = new int[]{
            R.drawable.gif_boby, R.drawable.gif_dan, R.drawable.gif_marechal, R.drawable.gif_mila,
            R.drawable.gif_mimizinha, R.drawable.gif_ryan, R.drawable.gif_shin
    };

    private static final String[] NAMES = new String[]{
            // 20 Men
            "Herbert Biel", "Carl Widmann", "Yannick Speck", "Valentin Blomberg", "Wolf Reichen",
            "Kai Brandis", "Mario Wenzel", "Harald Wentz", "Harald Wassermann", "Stephan Söllner",
            "Roland Sachs", "Ben Stahnke", "Jannik Lingenfelter", "Gregor Pauli",
            "Jonathan Weinreich", "Gerold Kruspe", "Wolf Mittermeier", "Jonathan Hafer",
            "Adalbert Nemetz", "Adalbert Memetz",
            // 20 Women
            "Ricarda Myers", "Judith Suckow", "Monika Spanner", "Lucie Straube", "Katharina Kahn",
            "Bettina Schildkraut", "Lisa Böttger", "Maria Scheurer", "Caroline Schlesinger",
            "Cecilia Schwarzmann", "Kerstin Friedberg", "Josefine Hamburg", "Hermina Lorber",
            "Waltraud Steppuhn", "Anneliese Dahmen", "Christa Kittel", "Louisa Schillinger",
            "Jacqueline Nesselrode", "Mareike Loeb", "Elisabeth Grunebaum"
    };

    private static final List<Contact> CONTACTS = new ArrayList<>();

    @Inject
    Repository(@NonNull Context appContext) {
        init();
    }

    private static void init() {
        if (CONTACTS.size() > 0) return;

        for (String name : NAMES) {
            String[] nameAndSurname = name.split(" ");
            CONTACTS.add(Contact.builder()
                    .uuid(UUID.randomUUID())
                    .person(Person.builder()
                            .name(nameAndSurname[0])
                            .surname(nameAndSurname[1])
                            .build())
                    .gifRes(GIFS[(CONTACTS.size() % GIFS.length)])
                    .build());
        }
    }

    public List<Contact> getContacts() {
        ArrayList<Contact> copy = new ArrayList<>();
        copy.addAll(CONTACTS);
        return copy;
    }

    public Contact createContact(String name, String surname, int imageRes) {
        Contact contact = Contact.builder()
                .uuid(UUID.randomUUID())
                .person(Person.builder()
                        .name(name == null ? "" : name)
                        .surname(surname == null ? "" : surname)
                        .build())
                .gifRes(imageRes)
                .build();
        if (CONTACTS.contains(contact)) return null;
        CONTACTS.add(contact);
        return contact;
    }

    public void deleteContacts(Iterable<Contact> contacts) {
        for (Contact contact : contacts) {
            CONTACTS.remove(contact);
        }
    }

    public Contact getContact(UUID editContactId) {
        if (editContactId == null) return null;
        for (Contact contact : CONTACTS) {
            if (contact.uuid().equals(editContactId)) return contact;
        }
        return null;
    }

    public int getDefaultImage() {
        return GIFS[0];
    }

    public int[] getAllImages() {
        return Arrays.copyOf(GIFS, GIFS.length);
    }
}
