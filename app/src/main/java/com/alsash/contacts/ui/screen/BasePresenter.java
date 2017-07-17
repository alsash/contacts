package com.alsash.contacts.ui.screen;

/**
 * A base presenter interface, that represents MVP pattern
 */
public interface BasePresenter<V extends BaseView> {

    void attach(V view);

    void detach();
}
