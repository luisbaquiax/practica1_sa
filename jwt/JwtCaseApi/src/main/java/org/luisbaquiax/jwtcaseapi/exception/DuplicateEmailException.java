package org.luisbaquiax.jwtcaseapi.exception;

public class DuplicateEmailException extends UserAutException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
