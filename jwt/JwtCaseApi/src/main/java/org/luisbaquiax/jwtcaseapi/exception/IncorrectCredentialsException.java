package org.luisbaquiax.jwtcaseapi.exception;

public class IncorrectCredentialsException extends UserAutException {
    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
