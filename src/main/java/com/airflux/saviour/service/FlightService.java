package com.airflux.saviour.service;

import com.airflux.saviour.controller.views.response.FlightResList;
import com.airflux.saviour.controller.views.response.OperatingInstructionResList;

public interface FlightService {

    FlightResList getFlightPlan(String airport);
    OperatingInstructionResList getOperationsPlan(String registration);
}
