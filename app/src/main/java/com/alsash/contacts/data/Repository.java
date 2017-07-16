package com.alsash.contacts.data;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A repository class that allows to manage model values.
 * It is injected by constructor annotation {@link javax.inject.Inject)
 * into a {@link com.alsash.contacts.app.ContactsAppComponent}
 */
@Singleton
public class Repository {

    private static final int[] ICONS = new int[]{
            0, 1, 2, 3, 4, 5
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
                    .person(Person.builder()
                            .name(nameAndSurname[0])
                            .surname(nameAndSurname[1])
                            .build())
                    .imageRes(ICONS[(CONTACTS.size() % ICONS.length)])
                    .build());
        }
    }

    public List<Contact> getContacts() {
        return CONTACTS;
    }

    public Contact create(String name, String surname, int imageRes) {
        Contact contact = Contact.builder()
                .person(Person.builder()
                        .name(name == null ? "" : name)
                        .surname(surname == null ? "" : surname)
                        .build())
                .imageRes(imageRes)
                .build();
        CONTACTS.add(contact);
        return contact;
    }
}
