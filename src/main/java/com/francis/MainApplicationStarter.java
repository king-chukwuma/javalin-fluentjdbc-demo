package com.francis;

import com.francis.controller.Router;
import io.javalin.Javalin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainApplicationStarter {

    private final Javalin javalin;
    private final Router router;

    @Inject
    public MainApplicationStarter(Javalin javalin, Router router) {
        this.javalin = javalin;
        this.router = router;
    }

    public void run(String... args) {
        router.bind();
        javalin.start(7777);
    }
}
