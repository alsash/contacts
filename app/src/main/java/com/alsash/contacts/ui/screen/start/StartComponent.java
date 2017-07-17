package com.alsash.contacts.ui.screen.start;

import dagger.Subcomponent;

/**
 * A Dagger component that build bridge between start screen dependencies
 */
@StartScope
@Subcomponent(modules = {StartModule.class})
public interface StartComponent {

    void inject(StartActivity startActivity);

    @Subcomponent.Builder
    interface Builder {
        StartComponent build();
    }
}
