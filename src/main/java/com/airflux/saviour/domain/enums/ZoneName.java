package com.airflux.saviour.domain.enums;

public enum ZoneName {
    EUROPE_LONDON("Europe/London"), EUROPE_AMSTERDAM("Europe/Amsterdam");

    private String zoneName;

    ZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String toString() {
        return this.zoneName;
    }
}
