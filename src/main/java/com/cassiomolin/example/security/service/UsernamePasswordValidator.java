package com.cassiomolin.example.security.service;


import com.cassiomolin.example.security.exception.AuthenticationException;
import com.cassiomolin.example.user.domain.User;
import com.cassiomolin.example.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Component for validating user credentials.
 *
 * @author cassiomolin
 */
@ApplicationScoped
public class UsernamePasswordValidator {

    @Inject
    private UserService userService;

    @Inject
    private PasswordEncoder passwordEncoder;

    /**
     * Validate username and password.
     *
     * @param username
     * @param password
     * @return
     */
    public User validateCredentials(String username, String password) {

        User user = userService.findByUsernameOrEmail(username);

        if (user == null) {
            // User cannot be found with the given username/email
            throw new AuthenticationException("Bad credentials.");
        }

        if (!user.isActive()) {
            // User is not active
            throw new AuthenticationException("The user is inactive.");
        }

        if (!passwordEncoder.checkPassword(password, user.getPassword())) {
            // Invalid password
            throw new AuthenticationException("Bad credentials.");
        }

        return user;
    }
}