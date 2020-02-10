package com.airflux.saviour.exception;

public class InvalidAirportNameException extends RuntimeException {

    public InvalidAirportNameException(String airportName) {
        super(String.format("Invalid value { %s } for the airport name.", airportName));
    }
}
