package com.example.simplebookshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PersonMenager {

    private PersonRepository personRepository;

    @Autowired
    public PersonMenager(PersonRepository personRepository) {
        this.personRepository = personRepository;

    }


    @EventListener(ApplicationReadyEvent.class)
    public void init(){

    }
}
