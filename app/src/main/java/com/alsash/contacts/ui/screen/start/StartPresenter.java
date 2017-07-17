package com.alsash.contacts.ui.screen.start;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.util.Log;

import com.alsash.contacts.BuildConfig;
import com.alsash.contacts.data.Contact;
import com.alsash.contacts.data.Repository;
import com.alsash.contacts.ui.event.ContactEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * An Implementation of the {@link StartContract.Presenter}
 * It is injected to the view by Dagger at the {@link StartModule}
 */
@StartScope
public class StartPresenter implements StartContract.Presenter {

    private final Repository repository;
    private final SortedList<Contact> contacts;
    private final Deque<Contact> deletedContacts = new LinkedBlockingDeque<>();

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
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
        repository.deleteContacts(deletedContacts);
        deletedContacts.clear();
    }

    @Override
    public void detach() {
        if (EventBus.getDefault().isRegistered(this)) // OnStop may not be called when memory is low
            EventBus.getDefault().unregister(this);
        viewRef = null;
    }

    @Override
    public void requestContactAdd() {
        getView().showContactAdd();
    }

    public void requestContactEdit(Contact contact) {
        if (deletedContacts.size() > 0 && deletedContacts.contains(contact)) return;
        getView().showContactEdit(contact);
    }

    @Override
    public void requestContactDelete(int position) {
        if (position >= contacts.size()) return;
        Contact contact = contacts.removeItemAt(position);
        deletedContacts.push(contact);
        getView().showDeleteMessage(contact);
    }

    @Override
    public void rejectContactDelete() {
        if (deletedContacts.size() == 0) return;
        Contact contact = deletedContacts.pop();
        getView().focusContact(contacts.add(contact));
    }

    /**
     * An EventBus subscription
     * @param event ContactEvent from EventBus. Expected sticky (cached) event
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContactEvent(ContactEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        switch (event.type) {
            case CREATE:
                getView().focusContact(contacts.add(event.contact));
                break;
            case EDIT:
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).uuid().equals(event.contact.uuid())) {
                        contacts.removeItemAt(i);
                        getView().focusContact(contacts.add(event.contact));
                        break;
                    }
                }
                break;
        }
        if (BuildConfig.DEBUG) fetchContacts();
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
                if (!existed.contains(contact))
                    contacts.add(contact);
            }
        } catch (Throwable e) {
            Log.d("StartPresenter", e.getMessage(), e);
        } finally {
            contacts.endBatchedUpdates();
        }
    }

    @NonNull
    private StartContract.View getView() {
        if (viewRef != null) return viewRef.get();
        return EmptyView.INSTANCE;
    }

    private static class EmptyView implements StartContract.View {

        static final EmptyView INSTANCE = new EmptyView();

        @Override
        public void focusContact(int position) {
            // Do nothing
        }

        @Override
        public void showContactAdd() {
            // Do nothing
        }

        @Override
        public void showContactEdit(Contact contact) {
            // Do nothing
        }

        @Override
        public void showDeleteMessage(Contact contact) {
            // Do nothing
        }
    }
}
