package com.alsash.contacts.screen.start;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.util.Log;

import com.alsash.contacts.data.Contact;
import com.alsash.contacts.data.Repository;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

/**
 * An Implementation of the {@link com.alsash.contacts.screen.start.StartContract.Presenter}
 * It is injected to the view by Dagger at the {@link StartModule}
 */
@StartScope
public class StartPresenter implements StartContract.Presenter {

    private final Repository repository;
    private final SortedList<Contact> contacts;

    private WeakReference<StartContract.View> viewRef;

    StartPresenter(Repository repository, SortedList<Contact> contacts) {
        this.repository = repository;
        this.contacts = contacts;
    }

    @Override
    public void attach(StartContract.View view) {
        viewRef = new WeakReference<>(view);
        fetchContacts();
    }

    @Override
    public void detach() {
        viewRef = null;
    }

    /**
     * Check if contacts were created or deleted and update contacts list
     */
    private void fetchContacts() {
        contacts.beginBatchedUpdates();
        try {
            Set<Contact> actual = new HashSet<>();
            actual.addAll(repository.getContacts());
            Set<Contact> existed = new HashSet<>();
            for (int i = 0; i < contacts.size(); i++) {
                Contact contact = contacts.get(i);
                if (actual.contains(contact)) {
                    existed.add(contact);
                } else {
                    contacts.removeItemAt(i);
                }
            }
            for (Contact contact : actual) {
                if (!existed.contains(contact)) contacts.add(contact);
            }
        } catch (Throwable e) {
            Log.d("StartPresenter", e.getMessage(), e);
        } finally {
            contacts.endBatchedUpdates();
        }
    }

    void add() {
        Contact contact = repository.create(String.valueOf(contacts.size()), null, 0);
        getView().showContact(contacts.add(contact));
    }

    @NonNull
    private StartContract.View getView() {
        if (viewRef != null) return viewRef.get();
        return EmptyView.INSTANCE;
    }

    private static final class EmptyView implements StartContract.View {

        static final EmptyView INSTANCE = new EmptyView();

        @Override
        public void showContact(int position) {
            // Do nothing
        }
    }
}
