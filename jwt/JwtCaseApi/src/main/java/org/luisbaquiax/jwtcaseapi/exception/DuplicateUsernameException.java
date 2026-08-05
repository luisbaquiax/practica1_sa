package org.luisbaquiax.jwtcaseapi.exception;

public class DuplicateUsernameException extends UserAutException {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
