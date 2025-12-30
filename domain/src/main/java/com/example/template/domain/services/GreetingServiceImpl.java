package com.example.template.domain.services;

import com.example.template.domain.Greeting;
import com.example.template.domain.ports.out.GreetingStore;
import java.util.UUID;

public class GreetingServiceImpl implements GreetingService {
    private final GreetingStore greetingStore;

    public GreetingServiceImpl(GreetingStore greetingStore) {
        this.greetingStore = greetingStore;
    }

    @Override
    public Greeting createGreeting(String name) {
        var greeting = new Greeting(UUID.randomUUID(), "Hello, " + name + "!");
        this.greetingStore.save(greeting);
        return greeting;
    }
}
