package com.francis.module;

import com.francis.controller.PersonController;
import com.francis.service.PersonService;
import com.francis.controller.Router;
import com.google.inject.AbstractModule;

import javax.inject.Singleton;

public class PersonModule extends AbstractModule {


    @Override
    protected void configure() {
//        binder().requireExplicitBindings();
        bind(Router.class).to(PersonController.class).in(Singleton.class);
        bind(PersonService.class).in(Singleton.class);
    }
}
