package com.cassiomolin.example.security.exception;

/**
 * Thrown if errors occur during the authorization process.
 *
 * @author cassiomolin
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
