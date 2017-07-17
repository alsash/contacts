package com.alsash.contacts.app;

import com.alsash.contacts.screen.addedit.AddEditComponent;
import com.alsash.contacts.screen.start.StartComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A Dagger component that build bridge between application and its dependencies
 */
@Singleton // Scope
@Component(modules = {ContactsAppModule.class})
public interface ContactsAppComponent {

    StartComponent.Builder getStartComponentBuilder();

    AddEditComponent.Builder getAddEditComponentBuilder();
}
