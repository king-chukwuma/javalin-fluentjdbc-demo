package com.francis;

import com.francis.module.JavalinModule;
import com.francis.module.PersonModule;
import com.google.inject.AbstractModule;

import javax.inject.Singleton;

public class MainApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        binder().requireExplicitBindings();
        install(new JavalinModule());
        install(new PersonModule());
        bind(MainApplicationStarter.class).in(Singleton.class);

    }
}
