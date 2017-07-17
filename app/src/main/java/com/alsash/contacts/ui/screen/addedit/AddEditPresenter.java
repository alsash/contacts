package com.alsash.contacts.ui.screen.addedit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alsash.contacts.data.Contact;
import com.alsash.contacts.data.Repository;
import com.alsash.contacts.ui.event.ContactEvent;
import com.alsash.contacts.ui.event.ContactEvent.Type;
import com.alsash.contacts.ui.screen.start.StartContract;
import com.alsash.contacts.ui.screen.start.StartModule;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * An Implementation of the {@link StartContract.Presenter}
 * It is injected to the view by Dagger at the {@link StartModule}
 */
@AddEditScope
public class AddEditPresenter implements AddEditContract.Presenter {

    private final Repository repository;
    private boolean isNew;
    private UUID uuid;
    private String name;
    private String surname;
    private int imageRes;
    private WeakReference<AddEditContract.View> viewRef;

    AddEditPresenter(Repository repository) {
        this.repository = repository;
        fetchDefault();
    }

    @Override
    public void init(boolean isNewContact, boolean isSavedState, @Nullable UUID contactUuid) {
        this.isNew = isNewContact;
        this.uuid = contactUuid;
        if (isSavedState) return;
        if (isNewContact) {
            fetchDefault();
        } else {
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

    /**
     * Providing proper error handling
     */
    @Override
    public void requestSave() {
        // check and trim string values
        boolean focusName = name == null || name.trim().length() == 0;
        boolean focusSurname = surname == null || surname.trim().length() == 0;
        if (focusName) getView().showName(name);
        if (focusSurname) getView().showSurname(surname);
        if (focusName || focusSurname) {
            getView().showContactSaveErrorMessageName(focusName); // or Surname
            return;
        }
        Contact contact = repository.createContact(uuid, name, surname, imageRes);
        boolean saved = repository.saveContact(contact, isNew);
        if (isNew) {
            if (saved) {
                EventBus.getDefault().postSticky(new ContactEvent(Type.CREATE, contact));
            } else {
                getView().showContactSaveErrorMessageExist(contact.name());
                return;
            }
        } else {
            if (saved) {
                EventBus.getDefault().postSticky(new ContactEvent(Type.EDIT, contact));
            }
        }
        getView().finishView();
    }

    private void checkAndShowTitle() {
        try {
            String title = name.charAt(0) + "." + surname.charAt(0) + ".";
            getView().showTitle(title);
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
            Log.d("AddEditPresenter",
                    "something goes wrong: Contact ID = "
                            + (editContactId != null ? editContactId.toString() : "null")
                            + " is not found at the Repository");
            fetchDefault();
            return;
        }
        uuid = editContact.uuid();
        name = editContact.person().name();
        surname = editContact.person().surname();
        imageRes = editContact.gifRes();
    }

    private void fetchDefault() {
        uuid = null;
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
        public void showContactSaveErrorMessageName(boolean focusName) {
            // Do nothing
        }

        @Override
        public void showContactSaveErrorMessageExist(String contactName) {
            // Do nothing
        }

        @Override
        public void finishView() {
            // Do nothing
        }
    }
}
