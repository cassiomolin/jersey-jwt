package com.cassiomolin.example.greeting.service;


import javax.enterprise.context.ApplicationScoped;

/**
 * Service class that provides operations for greetings.
 *
 * @author cassiomolin
 */
@ApplicationScoped
public class GreetingService {

    /**
     * Get a public greeting.
     *
     * @return
     */
    public String getPublicGreeting() {
        return "Hello from the other side!";
    }

    /**
     * Get a greeting for a user.
     *
     * @return
     */
    public String getGreetingForUser(String username) {
        return String.format("Hello %s!", username);
    }
}