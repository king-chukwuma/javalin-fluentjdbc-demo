package com.francis.service;

import com.francis.exception.BadRequestException;
import com.francis.model.Person;

import javax.inject.Singleton;


public class PersonService {

//    private PersonRepository personRepository;
//
//    @Inject
//    public PersonService(PersonRepository personRepository) {
//        this.personRepository = personRepository;
//    }

    public Person getPerson(){
//        throw new BadRequestException("MEss up!!!");
        return new Person("Chuks");
    }

}
