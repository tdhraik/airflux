package com.airflux.saviour.domain.enums;

public enum AirportCode {
    TXL, MUC, LHR, HAM;

    public static boolean contains(String airportCode) {
        for (AirportCode ac : AirportCode.values()) {
            if (ac.name().equals(airportCode)) {
                return true;
            }
        }
        return false;
    }
}
