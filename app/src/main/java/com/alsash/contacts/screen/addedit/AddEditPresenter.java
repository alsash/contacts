package com.alsash.contacts.screen.addedit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alsash.contacts.data.Contact;
import com.alsash.contacts.data.Repository;
import com.alsash.contacts.screen.start.StartContract;
import com.alsash.contacts.screen.start.StartModule;
import com.alsash.contacts.screen.start.StartScope;

import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * An Implementation of the {@link StartContract.Presenter}
 * It is injected to the view by Dagger at the {@link StartModule}
 */
@StartScope
public class AddEditPresenter implements AddEditContract.Presenter {

    private final Repository repository;
    private UUID contactUuid;
    private String name;
    private String surname;
    private int imageRes;
    private WeakReference<AddEditContract.View> viewRef;

    AddEditPresenter(Repository repository) {
        this.repository = repository;
        fetchDefault();
    }

    public void setContactId(@Nullable UUID contactUuid) {
        if (this.contactUuid == null && contactUuid == null) return;
        if (this.contactUuid != null && contactUuid == null) {
            this.contactUuid = null;
            fetchDefault();
            return;
        }
        if (this.contactUuid == null || !this.contactUuid.equals(contactUuid)) {
            this.contactUuid = contactUuid;
            fetchContact(contactUuid);
        }
    }

    @Override
    public void attach(AddEditContract.View view) {
        viewRef = new WeakReference<>(view);
        view.showImages(repository.getAllImages());
        view.showImage(imageRes);
        view.showName(name);
        view.showSurname(surname);
        checkAndShowTitle();
    }

    @Override
    public void detach() {
        viewRef = null;
    }

    @Override
    public void requestEditImage(int imageRes) {
        if (this.imageRes == imageRes) return;
        this.imageRes = imageRes;
        getView().showImage(imageRes);
    }

    @Override
    public void requestEditName(String name) {
        this.name = name;
        checkAndShowTitle();
    }

    @Override
    public void requestEditSurname(String surname) {
        this.surname = surname;
        checkAndShowTitle();
    }

    @Override
    public void requestSave() {
        Contact contact = repository.createContact(name, surname, imageRes);
        if (contact == null) {
            getView().showContactSaveErrorMessage((name + " " + surname).trim());
        } else {
            // getView().finishView();
        }
    }

    private void checkAndShowTitle() {
        try {
            getView().showTitle(name.charAt(0) + "." + surname.charAt(0) + ".");
        } catch (Throwable e) {
            getView().showTitle("");
        }
    }

    /**
     * Load contact by UUID
     */
    private void fetchContact(UUID editContactId) {
        Contact editContact = repository.getContact(editContactId);
        if (editContact == null) {
            fetchDefault();
            return;
        }
        name = editContact.person().name();
        surname = editContact.person().surname();
        imageRes = editContact.gifRes();
    }

    private void fetchDefault() {
        name = "";
        surname = "";
        imageRes = repository.getDefaultImage();
    }

    @NonNull
    private AddEditContract.View getView() {
        if (viewRef != null) return viewRef.get();
        return EmptyView.INSTANCE;
    }

    private static class EmptyView implements AddEditContract.View {

        static final EmptyView INSTANCE = new EmptyView();

        @Override
        public void showImages(int[] imageRes) {
            // Do nothing
        }

        @Override
        public void showImage(int imageRes) {
            // Do nothing
        }

        @Override
        public void showName(String name) {
            // Do nothing
        }

        @Override
        public void showSurname(String surname) {
            // Do nothing
        }

        @Override
        public void showTitle(String title) {
            // Do nothing
        }

        @Override
        public void showContactSaveErrorMessage(String contactName) {
            // Do nothing
        }

        @Override
        public void finishView() {
            // Do nothing
        }
    }
}
