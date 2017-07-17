package com.alsash.contacts.ui.screen.addedit;

import dagger.Subcomponent;

/**
 * A Dagger component that build bridge between addEdit screen dependencies
 */
@AddEditScope
@Subcomponent(modules = {AddEditModule.class})
public interface AddEditComponent {

    void inject(AddEditActivity addEditActivity);

    @Subcomponent.Builder
    interface Builder {
        AddEditComponent build();
    }
}
