package com.alsash.contacts.ui.screen.start;

import com.alsash.contacts.data.Contact;
import com.alsash.contacts.ui.screen.BasePresenter;
import com.alsash.contacts.ui.screen.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface StartContract {

    interface View extends BaseView {

        void focusContact(int position);

        void showContactAdd();

        void showContactEdit(Contact contact);

        void showDeleteMessage(Contact contact);
    }

    interface Presenter extends BasePresenter<View> {

        void start();

        void stop();

        void requestContactAdd();

        void requestContactDelete(int position);

        void rejectContactDelete();

        void requestContactEdit(Contact contact);
    }
}
