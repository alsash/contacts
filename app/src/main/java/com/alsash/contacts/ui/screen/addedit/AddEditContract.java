package com.alsash.contacts.ui.screen.addedit;

import android.support.annotation.Nullable;

import com.alsash.contacts.ui.screen.BasePresenter;
import com.alsash.contacts.ui.screen.BaseView;

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

        void showContactSaveErrorMessageName(boolean focusName);

        void showContactSaveErrorMessageExist(String contactName);

        void finishView();
    }

    interface Presenter extends BasePresenter<View> {

        void init(boolean isNewContact, boolean isSavedState, @Nullable UUID contactUuid);

        void requestEditImage(int imageRes);

        void requestEditName(String name);

        void requestEditSurname(String surname);

        void requestSave();
    }
}
