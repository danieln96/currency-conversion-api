package com.github.danieln96.currency.conversion.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(SameCurrencyException.class)
    protected ResponseEntity<Object> handleSameCurrencyException(final Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
