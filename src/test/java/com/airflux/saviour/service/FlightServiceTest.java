package com.airflux.saviour.service;

import com.airflux.saviour.controller.views.response.FlightResList;
import com.airflux.saviour.domain.AirportZone;
import com.airflux.saviour.domain.Flight;
import com.airflux.saviour.domain.FlightSchedule;
import com.airflux.saviour.domain.enums.AirportCode;
import com.airflux.saviour.domain.enums.ZoneName;
import com.airflux.saviour.repository.AirportZoneRepository;
import com.airflux.saviour.repository.FlightRepository;
import com.airflux.saviour.repository.FlightScheduleRepository;
import com.airflux.saviour.service.impl.FlightServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightScheduleRepository flightScheduleRepository;

    @Mock
    private AirportZoneRepository airportZoneRepository;

    private FlightServiceImpl flightService;

    @Before
    public void setUp() {
        flightService = new FlightServiceImpl(flightRepository, flightScheduleRepository, airportZoneRepository);
        Mockito.when(flightScheduleRepository.findAll()).thenReturn(getIterableFlightSchedules());
        Mockito.lenient().when(flightRepository.findByBase(any())).thenReturn(getFlights());
        Mockito.lenient().when(flightScheduleRepository.findByFlightIn(any())).thenReturn(getFlightSchedulesForCaseTwo());
        Mockito.lenient().when(airportZoneRepository.findByAirportCodeIn(Arrays.asList(AirportCode.TXL, AirportCode.HAM))).thenReturn(getAirportZonesForCaseOne());
        Mockito.lenient().when(airportZoneRepository.findByAirportCodeIn(Arrays.asList(AirportCode.TXL, AirportCode.LHR))).thenReturn(getAirportZonesForCaseTwo());
    }

    @Test
    public void whenGetFlightPlanIsCalled_thenReturnAllFlightPlans() {
        // arrange
        String airport = "";

        // act
        FlightResList flightResList = flightService.getFlightPlan(airport);

        //assert
        assertThat(flightResList.getFlights().size()).isEqualTo(3);
        assertThat(flightResList.getFlights().get(0).getEquipment()).isEqualTo("123");
        assertThat(flightResList.getFlights().get(0).getDeparture()).isEqualTo(
                ZonedDateTime.of(LocalDate.of(2018, Month.APRIL, 13) , LocalTime.parse("06:30"), ZoneId.of(ZoneName.EUROPE_AMSTERDAM.toString())));
        assertThat(flightResList.getFlights().get(0).getArrival()).isEqualTo(
                ZonedDateTime.of(LocalDate.of(2018, Month.APRIL, 13) , LocalTime.parse("07:30"), ZoneId.of(ZoneName.EUROPE_AMSTERDAM.toString())));
    }

    @Test
    public void whenGetFlightPlanIsCalledWithValidAirport_thenReturnAllFlightPlansForThatAirport() {
        // arrange
        String airport = "TXL";

        // act
        FlightResList flightResList = flightService.getFlightPlan(airport);

        //assert
        assertThat(flightResList.getFlights().size()).isEqualTo(2);
        assertThat(flightResList.getFlights().get(0).getOrigin()).isEqualTo(AirportCode.TXL);
        assertThat(flightResList.getFlights().get(1).getOrigin()).isEqualTo(AirportCode.TXL);
        assertThat(flightResList.getFlights().get(1).getDeparture()).isEqualTo(
                ZonedDateTime.of(LocalDate.of(2018, Month.APRIL, 13) , LocalTime.parse("19:30"), ZoneId.of(ZoneName.EUROPE_AMSTERDAM.toString())));
        assertThat(flightResList.getFlights().get(1).getArrival()).isEqualTo(
                ZonedDateTime.of(LocalDate.of(2018, Month.APRIL, 13) , LocalTime.parse("20:30"), ZoneId.of(ZoneName.EUROPE_LONDON.toString())));
    }


    // Test data
    private Iterable<FlightSchedule> getIterableFlightSchedules() {
        return () -> getFlightSchedulesForCaseOne().stream().iterator();
    }

    private List<FlightSchedule> getFlightSchedulesForCaseOne() {
        return Arrays.asList(new FlightSchedule(1L, AirportCode.TXL, AirportCode.HAM, 60, "06:30", getFlights().get(0)),
                new FlightSchedule(2L, AirportCode.TXL, AirportCode.LHR, 120, "19:30", getFlights().get(0)),
                new FlightSchedule(3L, AirportCode.MUC, AirportCode.LHR, 180, "12:30", getFlights().get(0)));
    }

    private List<FlightSchedule> getFlightSchedulesForCaseTwo() {
        return Arrays.asList(new FlightSchedule(1L, AirportCode.TXL, AirportCode.HAM, 60, "06:30", getFlights().get(0)),
                new FlightSchedule(2L, AirportCode.TXL, AirportCode.LHR, 120, "19:30", getFlights().get(0)));
    }

    private List<Flight> getFlights() {
        return Collections.singletonList(new Flight(1L, AirportCode.TXL, "Airbus", "123", "FL-0001", new ArrayList<>()));
    }

    private List<AirportZone> getAirportZonesForCaseOne() {
        return Arrays.asList(new AirportZone(1L, AirportCode.TXL, ZoneName.EUROPE_AMSTERDAM),
                new AirportZone(2L, AirportCode.HAM, ZoneName.EUROPE_AMSTERDAM));
    }

    private List<AirportZone> getAirportZonesForCaseTwo() {
        return Arrays.asList(new AirportZone(1L, AirportCode.TXL, ZoneName.EUROPE_AMSTERDAM),
                new AirportZone(2L, AirportCode.LHR, ZoneName.EUROPE_LONDON));
    }
}
