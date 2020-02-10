package com.airflux.saviour.exception;

public class SaviourValidationException extends RuntimeException {

    public SaviourValidationException(String invalidValue, String fieldName) {
        super(String.format("Invalid value %s for field %s", invalidValue, fieldName));
    }
}
