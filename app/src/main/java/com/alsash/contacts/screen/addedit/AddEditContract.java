package com.alsash.contacts.screen.addedit;

import com.alsash.contacts.screen.BasePresenter;
import com.alsash.contacts.screen.BaseView;

import java.util.UUID;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface AddEditContract {

    interface View extends BaseView {

        void showImages(int[] imageRes);

        void showImage(int imageRes);

        void showName(String name);

        void showSurname(String surname);

        void showTitle(String title);

        void showContactSaveErrorMessage(String contactName);

        void finishView();
    }

    interface Presenter extends BasePresenter<View> {

        void setContactId(UUID contactId);

        void requestEditImage(int imageRes);

        void requestEditName(String name);

        void requestEditSurname(String surname);

        void requestSave();
    }
}
