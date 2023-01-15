package com.francis.controller;

import com.francis.FluentJdbcConfig;
import com.francis.exception.BadRequestException;
import com.francis.service.PersonService;
import com.francis.module.PersonModule;
import io.javalin.Javalin;
import io.javalin.validation.Validator;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.simple.SimpleLoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersonController implements Router {

    private final Javalin javalin;
    private final PersonService personService;

    private final Logger logger = new SimpleLoggerFactory().getLogger(PersonModule.class.getName());

    @Inject
    public PersonController(@Singleton PersonService personService, Javalin javalin) {
        this.personService = personService;
        this.javalin = javalin;
    }

    @Override
    public void bind() {

        javalin.get("/person", ctx -> {
            ctx.json(personService.getPerson());
            String header = ctx.header("instance-key");
            logger.info("Test Logging");
            logger.info(header);
            FluentJdbcConfig fluentJdbcConfig = new FluentJdbcConfig();
            fluentJdbcConfig.testDB();
        });
    }

    public void bind2() {

        javalin.get("/person2", ctx -> {
            throw new BadRequestException("MEss up!!!");
        });
    }
}
