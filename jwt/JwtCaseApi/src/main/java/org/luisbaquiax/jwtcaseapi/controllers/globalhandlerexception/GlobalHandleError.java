package org.luisbaquiax.jwtcaseapi.controllers.globalhandlerexception;

import lombok.extern.slf4j.Slf4j;
import org.luisbaquiax.jwtcaseapi.exception.*;
import org.luisbaquiax.jwtcaseapi.exception.emailexception.EmailException;
import org.luisbaquiax.jwtcaseapi.exception.exceptionresponse.ResponseExceptionApi;
import org.luisbaquiax.jwtcaseapi.exception.exceptionresponse.ResponseValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalHandleError {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseValidation> handleValidatorException(MethodArgumentNotValidException ex){
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(
                        Collectors.toMap(
                                FieldError::getField,
                                fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid value"
                        )
                );
        ResponseValidation response = new ResponseValidation(
                "Validation failed",
                HttpStatus.BAD_REQUEST.value(), errors);
        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            NotFoundException.class
    })
    public ResponseEntity<ResponseExceptionApi> handleNotFound(UserAutException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            DuplicateUsernameException.class,
            DuplicateEmailException.class,
            BussnessException.class
    })
    public ResponseEntity<ResponseExceptionApi> handleConlict(UserAutException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            IncorrectCredentialsException.class,
            DisabledUserException.class,
            UserUnauthorizedException.class,
    })
    public ResponseEntity<ResponseExceptionApi> handleNonAuthoritative(UserAutException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Exception.class, EmailException.class})
    public ResponseEntity<?> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ResponseExceptionApi> buildResponseEntity(String message, HttpStatus status) {
        return ResponseEntity.status(status.value()).body(
                new ResponseExceptionApi(message, status.value())
        );
    }
}
