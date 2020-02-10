package com.airflux.saviour.exception;

import com.airflux.saviour.controller.views.response.SaviourErrorRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class SaviourExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<SaviourErrorRes> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(constructSaviourErrorResponse(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SaviourValidationException.class)
    public final ResponseEntity<SaviourErrorRes> handleSaviourValidationException(SaviourValidationException ex) {
        return new ResponseEntity<>(constructSaviourErrorResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAirportNameException.class)
    public final ResponseEntity<SaviourErrorRes> handleInvalidAirportNameException(InvalidAirportNameException ex) {
        return new ResponseEntity<>(constructSaviourErrorResponse(ex), HttpStatus.BAD_REQUEST);
    }

    private SaviourErrorRes constructSaviourErrorResponse(RuntimeException ex) {
        return new SaviourErrorRes(ex.getMessage(), ex.getLocalizedMessage(), Instant.now());
    }

}
