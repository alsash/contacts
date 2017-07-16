package com.alsash.contacts.screen.start;

import com.alsash.contacts.screen.BasePresenter;
import com.alsash.contacts.screen.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface StartContract {

    interface View extends BaseView {

        void showContact(int position);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
