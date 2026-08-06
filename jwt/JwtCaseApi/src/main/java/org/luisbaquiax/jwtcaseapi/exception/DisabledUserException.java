package org.luisbaquiax.jwtcaseapi.exception;

public class DisabledUserException extends UserAutException {
    public DisabledUserException(String message) {
        super(message);
    }
}
