package org.luisbaquiax.jwtcaseapi.exception.exceptionresponse;


public record ResponseExceptionApi(
        String message,
        int status
) {
}
