package com.airflux.saviour.controller.views.response;

import com.airflux.saviour.domain.enums.AirportCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRes {

    private AirportCode origin;
    private AirportCode destination;
    private ZonedDateTime departure;
    private ZonedDateTime arrival;
    private String equipment;
}
