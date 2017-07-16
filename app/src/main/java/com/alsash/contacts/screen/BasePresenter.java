package com.alsash.contacts.screen;

/**
 * A base presenter interface, that represents MVP pattern
 */
public interface BasePresenter<V extends BaseView> {

    void attach(V view);

    void detach();
}
