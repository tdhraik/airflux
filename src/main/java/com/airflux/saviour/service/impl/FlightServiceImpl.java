package com.airflux.saviour.service.impl;

import com.airflux.saviour.controller.views.response.FlightRes;
import com.airflux.saviour.controller.views.response.FlightResList;
import com.airflux.saviour.controller.views.response.OperatingInstructionRes;
import com.airflux.saviour.controller.views.response.OperatingInstructionResList;
import com.airflux.saviour.domain.AirportZone;
import com.airflux.saviour.domain.Flight;
import com.airflux.saviour.domain.FlightSchedule;
import com.airflux.saviour.domain.enums.AirportCode;
import com.airflux.saviour.exception.EntityNotFoundException;
import com.airflux.saviour.repository.AirportZoneRepository;
import com.airflux.saviour.repository.FlightRepository;
import com.airflux.saviour.repository.FlightScheduleRepository;
import com.airflux.saviour.service.FlightService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class FlightServiceImpl implements FlightService {

    private Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

    private static final LocalDate DATE_AT_ORIGIN = LocalDate.of(2018, Month.APRIL, 13);

    private FlightScheduleRepository flightScheduleRepository;

    private FlightRepository flightRepository;

    private AirportZoneRepository airportZoneRepository;

    public FlightServiceImpl(FlightRepository flightRepository, FlightScheduleRepository flightScheduleRepository, AirportZoneRepository airportZoneRepository) {
        this.flightRepository = flightRepository;
        this.flightScheduleRepository = flightScheduleRepository;
        this.airportZoneRepository = airportZoneRepository;
    }

    @Override
    public FlightResList getFlightPlan(String airport) {
        List<FlightRes> flightRes = new ArrayList<>();
        if(StringUtils.isEmpty(airport)) {
            for (FlightSchedule flightSchedule : flightScheduleRepository.findAll()) {
                flightRes.add(convertFlightScheduleToFlightRes(flightSchedule));
            }
        }else {
            List<Flight> flights = flightRepository.findByBase(AirportCode.valueOf(airport));
            if(flights!=null && flights.size() > 0) {
                List<FlightSchedule> flightSchedules = flightScheduleRepository.findByFlightIn(flights);
                for(FlightSchedule flightSchedule : flightSchedules) {
                    flightRes.add(convertFlightScheduleToFlightRes(flightSchedule));
                }
            }
        }
        return new FlightResList(flightRes);
    }

    @Override
    public OperatingInstructionResList getOperationsPlan(String registration) {

        List<OperatingInstructionRes> operatingInstructionRes = new ArrayList<>();
        Optional<Flight> flight = flightRepository.findByRegistrationNumber(registration);
        if(!flight.isPresent()) {
            logger.debug(String.format("Invalid registration number %s requested.", registration));
            throw new EntityNotFoundException("Flight", "registration", registration);
        }
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findByFlightIn(Collections.singletonList(flight.get()));
        flightSchedules.forEach( flightSchedule -> {
            operatingInstructionRes.add(convertFlightScheduleToOperationInstRes(flightSchedule));
        });
        return new OperatingInstructionResList(operatingInstructionRes);
    }

    private FlightRes convertFlightScheduleToFlightRes(FlightSchedule flightSchedule) {

        FlightRes flightRes = new FlightRes();
        flightRes.setOrigin(flightSchedule.getOrigin());
        flightRes.setDestination(flightSchedule.getDestination());
        flightRes.setEquipment(flightSchedule.getFlight().getEquipment());

        LocalDateTime originDateTime = LocalDateTime.of(DATE_AT_ORIGIN, LocalTime.parse(flightSchedule.getDepartureTime()));
        List<AirportZone> airportZones = airportZoneRepository.findByAirportCodeIn(
                Arrays.asList(flightSchedule.getOrigin(), flightSchedule.getDestination()));

        Optional<AirportZone> originZone = airportZones.stream().filter(az -> az.getAirportCode().equals(flightSchedule.getOrigin())).findFirst();

        if(originZone.isPresent()) {
            ZonedDateTime departureDateTime = originDateTime.atZone(ZoneId.of(originZone.get().getZoneName().toString()));
            flightRes.setDeparture(departureDateTime);
            departureDateTime = departureDateTime.plusMinutes(flightSchedule.getFlightTime());
            Optional<AirportZone> destinationZone = airportZones.stream().filter(az -> az.getAirportCode().equals(flightSchedule.getDestination())).findFirst();
            if(destinationZone.isPresent()) {
                flightRes.setArrival(departureDateTime.withZoneSameInstant(ZoneId.of(destinationZone.get().getZoneName().toString())));
            }
        }
        return flightRes;
    }

    private OperatingInstructionRes convertFlightScheduleToOperationInstRes(FlightSchedule flightSchedule) {
        OperatingInstructionRes operatingInstructionRes = new OperatingInstructionRes();
        operatingInstructionRes.setOrigin(flightSchedule.getOrigin());
        operatingInstructionRes.setDestination(flightSchedule.getDestination());
        List<AirportZone> airportZones = airportZoneRepository.findByAirportCodeIn(Collections.singletonList(flightSchedule.getOrigin()));
        if (airportZones != null && airportZones.size() > 0) {
            LocalTime departureTime = LocalTime.parse(flightSchedule.getDepartureTime());
            LocalDateTime originDateTime = LocalDateTime.of(DATE_AT_ORIGIN, departureTime);
            operatingInstructionRes.setDeparture(originDateTime.atZone(ZoneId.of(airportZones.get(0).getZoneName().toString())));
        }
        return operatingInstructionRes;
    }

}
