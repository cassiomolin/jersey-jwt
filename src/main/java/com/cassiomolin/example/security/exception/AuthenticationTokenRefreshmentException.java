package com.cassiomolin.example.security.exception;

/**
 * Thrown if an authentication token cannot be refreshed.
 *
 * @author cassiomolin
 */
public class AuthenticationTokenRefreshmentException extends RuntimeException {

    public AuthenticationTokenRefreshmentException(String message) {
        super(message);
    }

    public AuthenticationTokenRefreshmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
