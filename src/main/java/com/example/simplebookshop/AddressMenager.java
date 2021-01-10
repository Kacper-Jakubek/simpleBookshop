package com.example.simplebookshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AddressMenager {

    private AddressRepository addressRepository;

    @Autowired
    public AddressMenager(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

    }



}
