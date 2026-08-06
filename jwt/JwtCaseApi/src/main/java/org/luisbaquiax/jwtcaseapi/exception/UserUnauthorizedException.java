package org.luisbaquiax.jwtcaseapi.exception;

public class UserUnauthorizedException extends UserAutException {
    public UserUnauthorizedException(String message) {
        super(message);
    }
}
