package com.airflux.saviour.controller;

import com.airflux.saviour.controller.views.response.FlightResList;
import com.airflux.saviour.controller.views.response.OperatingInstructionResList;
import com.airflux.saviour.domain.enums.AirportCode;
import com.airflux.saviour.exception.InvalidAirportNameException;
import com.airflux.saviour.exception.SaviourValidationException;
import com.airflux.saviour.service.FlightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("/flights")
@Api(value = "/flights", description = "Airflux flight details service.", tags = "flight-service")
public class FlightController {

    private Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    @GetMapping(value = "/flightplan", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/flightplan", response = FlightResList.class, nickname = "get-flight-plan", notes = "Get flight plan")
    public FlightResList getFligthPlan(@RequestParam(value = "airport", required = false) String airport) {
        if(StringUtils.isNotEmpty(airport) && !AirportCode.contains(airport)) {
            logger.debug(String.format("Invalid airport code %s in the request.", airport));
            throw new InvalidAirportNameException(airport);
        }
        return flightService.getFlightPlan(airport);
    }

    @GetMapping(value = "/operationsplan", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/operationsplan", response = OperatingInstructionResList.class, nickname = "get-operations-plan", notes = "Get operations plan")
    public OperatingInstructionResList getOperationsPlan(@RequestParam(value = "registration") String registration) {
        if(StringUtils.isEmpty(registration)) {
            logger.debug("Empty registration number in the request");
            throw new SaviourValidationException(registration, "registration");
        }
        return flightService.getOperationsPlan(registration);
    }
}
