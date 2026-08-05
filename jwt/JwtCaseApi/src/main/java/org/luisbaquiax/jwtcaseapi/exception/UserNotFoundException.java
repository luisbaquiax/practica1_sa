package org.luisbaquiax.jwtcaseapi.exception;

public class UserNotFoundException extends UserAutException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
