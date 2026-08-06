package org.luisbaquiax.jwtcaseapi.exception.exceptionresponse;

import java.util.Map;

public record ResponseValidation(
            String message,
            int status,
            Map<String, String> errors
) {
}
