package com.francis.module;

import com.francis.exception.BadRequestException;
import com.francis.exception.GenericExceptionResponse;
import com.google.inject.AbstractModule;
import io.javalin.Javalin;

public class JavalinModule extends AbstractModule {

    @Override
    protected void configure() {
//        Javalin.create().port();
        bind(Javalin.class).toInstance(Javalin.create()
                .error(404, ctx -> ctx.result("404 Error"))
                .exception(BadRequestException.class, (exception, ctx) -> ctx.json(new GenericExceptionResponse(404, "Request Failed", exception.getMessage())))

        );
    }
}
