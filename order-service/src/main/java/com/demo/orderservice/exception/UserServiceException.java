package com.demo.orderservice.exception;

/**
 * Excepción que se lanza cuando el user-service falla o no está disponible.
 */
public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
