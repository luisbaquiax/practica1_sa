package org.luisbaquiax.jwtcaseapi.exception.emailexception;


import org.luisbaquiax.jwtcaseapi.exception.UserAutException;

public class EmailException extends UserAutException {
    public EmailException(String message) {
        super(message);
    }
}
