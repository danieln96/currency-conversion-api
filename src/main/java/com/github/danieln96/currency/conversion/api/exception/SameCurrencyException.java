package com.github.danieln96.currency.conversion.api.exception;

public class SameCurrencyException extends IllegalArgumentException {

    public SameCurrencyException(final String message) {
        super(message);
    }
}
