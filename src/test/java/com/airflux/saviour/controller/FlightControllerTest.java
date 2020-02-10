package com.airflux.saviour.controller;


import com.airflux.saviour.controller.views.response.FlightRes;
import com.airflux.saviour.controller.views.response.FlightResList;
import com.airflux.saviour.controller.views.response.OperatingInstructionRes;
import com.airflux.saviour.controller.views.response.OperatingInstructionResList;
import com.airflux.saviour.domain.enums.AirportCode;
import com.airflux.saviour.exception.SaviourValidationException;
import com.airflux.saviour.service.FlightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FlightControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FlightService flightService;

    private List<FlightRes> getFlightResObjects() {
        return Arrays.asList(new FlightRes(AirportCode.TXL, AirportCode.HAM, ZonedDateTime.now(),
                ZonedDateTime.now(), "737"), new FlightRes(AirportCode.MUC, AirportCode.HAM, ZonedDateTime.now(),
                ZonedDateTime.now(), "737"));
    }

    @Test
    public void whenGetFlightPlanIsCalledWithInvalidAirport_thenReturnErrorMessage() throws Exception {

        // arrange
        String airport = "ABC";

        // act and assert
        mvc.perform(get("/flights/flightplan").param("airport", airport))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid value { ABC } for the airport name."));
    }

    @Test
    public void whenGetFlightPlanIsCalledWithValidAirport_thenReturnListOfAllFlightsForThatAirport() throws Exception {

        // arrange
        String airport = "MUC";
        given(flightService.getFlightPlan(any())).willReturn(new FlightResList(Arrays.asList(getFlightResObjects().get(1))));

        // act and assert
        mvc.perform(get("/flights/flightplan").param("airport", airport))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flights.length()").value(1))
                .andExpect(jsonPath("$.flights[0].origin").value(AirportCode.MUC.toString()));
    }

    @Test
    public void whenGetFlightPlanIsCalled_thenReturnListOfAllFlights() throws Exception {

        // arrange
        given(flightService.getFlightPlan(any())).willReturn(new FlightResList(getFlightResObjects()));

        // act and assert
        mvc.perform(get("/flights/flightplan"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flights.length()").value(2))
                .andExpect(jsonPath("$.flights[0].origin").value(AirportCode.TXL.toString()));
    }

    @Test
    public void whenGetOperationsPlanIsCalledWithEmptyRegistration_thenThrowValidationException() throws Exception {

        // arrange
        String registration = "";
        given(flightService.getOperationsPlan(any())).willThrow(new SaviourValidationException("registration", registration));

        // act and assert
        mvc.perform(get("/flights/operationsplan").param("registration", registration))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid value  for field registration"));
    }

    @Test
    public void whenGetOperationsPlanIsCalledWithValidRegistration_thenReturnAllOperationsForThatFlight() throws Exception {

        // arrange
        String registration = "FL-0001";
        given(flightService.getOperationsPlan(any())).willReturn(new OperatingInstructionResList(
                Collections.singletonList(new OperatingInstructionRes(AirportCode.TXL, AirportCode.HAM, ZonedDateTime.now()))));

        // act and assert
        mvc.perform(get("/flights/operationsplan").param("registration", registration))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operatingInstructions.length()").value(1))
                .andExpect(jsonPath("$.operatingInstructions[0].origin").value(AirportCode.TXL.toString()));
    }

}
