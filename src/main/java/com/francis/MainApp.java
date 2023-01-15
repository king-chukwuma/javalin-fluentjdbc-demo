package com.francis;

import com.google.inject.Guice;
import com.google.inject.Injector;

import static com.google.inject.Guice.createInjector;


public class MainApp {
    public static void main(String[] args) {
        createInjector(new MainApplicationModule()).getInstance(MainApplicationStarter.class).run(args);
    }
}
